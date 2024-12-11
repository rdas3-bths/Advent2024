from collections import defaultdict

def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data

def convert_to_dict(numbers):

    # this will give me a default value of 0 for any stone i don't have
    # idk how to do this in java
    stones = defaultdict(int)
    for stone_number in numbers:
        stone_number = int(stone_number)
        stones[stone_number] += 1

    return stones


def blink_times(blinks, stone_map):
    for i in range(blinks):
        blink(stone_map)
    return

# just keep track of how many stones of each number you have
# since the math isn't complicated, you shouldn't too many of each stone type
# for example:
# if you have 4090 three times, one blink will create 40 three times and 90 three times
# the order of the stones don't really matter here, so a list isn't necessary
def blink(stone_map):
    # have to create a temp dict so I can modify the original dict during iteration
    temporary_stones = dict(stone_map)

    for stone_number, number_of_stones in temporary_stones.items():

        # rule 1
        if stone_number == 0:
            stones[1] += number_of_stones
            stones[0] -= number_of_stones

        # rule 2
        elif len(str(stone_number)) % 2 == 0:
            stone_left = int(str(stone_number)[:int(len(str(stone_number)) / 2)])
            stone_right = int(str(stone_number)[int(len(str(stone_number)) / 2):])

            stones[stone_left] += number_of_stones
            stones[stone_right] += number_of_stones
            stones[stone_number] -= number_of_stones

        # rule 3
        else:
            stones[stone_number * 2024] += number_of_stones
            stones[stone_number] -= number_of_stones
    return


#-----------------------------------------------------------------------------------------

filename = 'InputFile'
#filename = 'sample.txt'
file_data = get_file_data(filename)
numbers = file_data[0].split(" ")

stones = convert_to_dict(numbers)
blink_times(25, stones)
print('Part one answer: ', sum(stones.values()))

stones = convert_to_dict(numbers)
blink_times(75, stones)
print('Part two answer: ', sum(stones.values()))