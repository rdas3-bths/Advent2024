import time


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data


def get_combo_operand(operand, a, b, c):
    if 0 <= operand <= 3:
        return operand
    elif operand == 4:
        return a
    elif operand == 5:
        return b
    elif operand == 6:
        return c
    return -1


def do_program(program_instructions, register_a, register_b, register_c):
    i = 0
    jump = False
    output = []
    while i < len(program_instructions) - 1:
        opcode = program_instructions[i]
        operand = program_instructions[i + 1]
        if opcode == 0:
            register_a = register_a // (2 ** get_combo_operand(operand, register_a, register_b, register_c))
            i += 2
        if opcode == 1:
            register_b = register_b ^ operand
            i += 2
        if opcode == 2:
            register_b = get_combo_operand(operand, register_a, register_b, register_c) % 8
            i += 2
        if opcode == 3:
            if register_a != 0:
                i = operand
            else:
                i += 2
        if opcode == 4:
            register_b = register_b ^ register_c
            i += 2
        if opcode == 5:
            output.append(get_combo_operand(operand, register_a, register_b, register_c) % 8)
            i += 2
        if opcode == 6:
            register_b = register_a // (2 ** get_combo_operand(operand, register_a, register_b, register_c))
            i += 2
        if opcode == 7:
            register_c = register_a // (2 ** get_combo_operand(operand, register_a, register_b, register_c))
            i += 2
    return output

file_data = get_file_data("InputFile")
register_a = 0
register_b = 0
register_c = 0
program_instructions = []

for line in file_data:
    if line.__contains__("Register A"):
        register_a = int(line.split(": ")[1])
    if line.__contains__("Register B"):
        register_b = int(line.split(": ")[1])
    if line.__contains__("Register C"):
        register_c = int(line.split(": ")[1])
    if line.__contains__("Program: "):
        numbers = line.split(": ")[1].split(",")
        for n in numbers:
            program_instructions.append(int(n))

output = do_program(program_instructions, register_a, register_b, register_c)
current_output = str(output).replace("[", "").replace("]", "").replace(" ", "")
print("Part one answer:", current_output)

valid = []
for length in range(1,len(program_instructions)+1):
    if length == 1:
        previous_valid = [0]
    else:
        previous_valid = valid
    valid = []
    for num in previous_valid:
        for offset in range(8):
            register_a = 8*num+offset
            result = do_program(program_instructions, register_a, register_b, register_c)
            program_subset = program_instructions[len(program_instructions)-length:]
            if result == program_subset:
                valid.append(register_a)

print("Part two answer:", min(valid))