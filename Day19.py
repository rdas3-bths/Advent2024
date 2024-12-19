from functools import cache


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data

# we can use caching here because looking at the input shows that
# we have MANY repeated patterns that should always result in the
# same return value.
# python makes this simple:
@cache
def is_possible(towel):
    # base case, we have an empty string
    # just added a counter for part 2 ezpz
    if towel == '':
        return True, 1
    possible = False
    count = 0
    # start checking the beginning of each string
    for start in available_designs:
        # if the towel starts with one of the available designs
        if towel.startswith(start):
            # recursive call with the substring after the start design
            check, number = is_possible(towel[len(start):])
            # update possible if we found a match
            if check:
                possible = True
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