import itertools


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    f.close()
    return data


def evaluate_operation(operation):
    index = 1
    current_result = 0
    while index < len(operation):
        if index == 1:
            if operation[index] == "+":
                current_result += (operation[index-1] + operation[index+1])
            if operation[index] == "*":
                current_result += (operation[index-1] * operation[index+1])
            if operation[index] == "||":
                current_result += int((str(operation[index-1]) + str(operation[index+1])))
        else:
            if operation[index] == "+":
                current_result += operation[index+1]
            if operation[index] == "*":
                current_result *= operation[index+1]
            if operation[index] == "||":
                current_result = int(str(current_result) + str(operation[index+1]))
        index += 2
    return current_result


file_data = get_file_data("input_file")
results = []
operands = []
operator_list_part_one = []
operator_list_part_two = []

for line in file_data:
    number = line.split(":")[0]
    results.append(int(number))
    one_set_of_operands = line.split(":")[1].split(" ")
    one_set_of_operands.pop(0)
    for i in range(len(one_set_of_operands)):
        one_set_of_operands[i] = int(one_set_of_operands[i])
    operands.append(one_set_of_operands)

for operand in operands:
    combos = []
    for combo in itertools.product(("+", "*"), repeat=len(operand)-1):
        combos.append(combo)
    operator_list_part_one.append(combos)

    combos = []
    for combo in itertools.product(("+", "*", "||"), repeat=len(operand)-1):
        combos.append(combo)
    operator_list_part_two.append(combos)

all_combinations_part_one = []
all_combinations_part_two = []
for i in range(len(operands)):
    result = results[i]
    for operators in operator_list_part_one[i]:
        unique_operation = operands[i].copy()
        index = 1
        for o in operators:
            unique_operation.insert(index, o)
            index += 2
        all_combinations_part_one.append((unique_operation, result))
    for operators in operator_list_part_two[i]:
        unique_operation = operands[i].copy()
        index = 1
        for o in operators:
            unique_operation.insert(index, o)
            index += 2
        all_combinations_part_two.append((unique_operation, result))

good_results = set()

for item in all_combinations_part_one:
    result = evaluate_operation(item[0])
    if result == item[1]:
        good_results.add(result)

print("Part one answer:", sum(good_results))
good_results = set()

for item in all_combinations_part_two:
    result = evaluate_operation(item[0])
    if result == item[1]:
        good_results.add(result)

print("Part two answer:", sum(good_results))





