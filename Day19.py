import functools


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data


@functools.cache
def is_possible(towel):
    if towel == '':
        return True, 1
    possible = False
    count = 0
    for start in available_designs:
        if towel.startswith(start):
            check, number = is_possible(towel[len(start):])
            possible = possible or check
            count += number
    return possible, count


file_data = get_file_data("InputFile")

available_designs = set()
target_towels = set()
for line in file_data:
    if line.__contains__(","):
        for a in line.split(", "):
            available_designs.add(a)
    elif len(line) != 0:
        target_towels.add(line)


part_one = 0
part_two = 0
for towel in target_towels:
    check, number = is_possible(towel)
    if check:
        part_one += 1
    part_two += number

print("Part one answer:", part_one)
print("Part two answer:", part_two)