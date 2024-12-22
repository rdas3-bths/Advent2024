from collections import defaultdict


def do_one_evolve(secret_number):
    product = secret_number * 64
    secret_number = secret_number ^ product
    secret_number = secret_number % 16777216

    divide = secret_number // 32
    secret_number = secret_number ^ divide
    secret_number = secret_number % 16777216

    product = secret_number * 2048
    secret_number = secret_number ^ product
    secret_number = secret_number % 16777216

    return secret_number


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data


file_data = get_file_data("input_file")
original_secret_numbers = []
for secret_n in file_data:
    original_secret_numbers.append(int(secret_n))


new_secret_numbers = []
price_map = defaultdict(int)
count = 0
for number in original_secret_numbers:
    new_number = number
    ones_places = []
    differences = []
    for i in range(2000):
        ones_places.append(new_number % 10)
        new_number = do_one_evolve(new_number)
    ones_places.append(new_number % 10)
    new_secret_numbers.append(new_number)
    for i in range(len(ones_places)-1):
        differences.append(ones_places[i+1] - ones_places[i])

    index = 3
    found = set()
    while index < len(differences):
        price_change_tuple = (differences[index-3], differences[index-2], differences[index-1], differences[index])
        if price_change_tuple not in found:
            price_map[price_change_tuple] += ones_places[index+1]
            found.add(price_change_tuple)
        index += 1

print("Part one answer:", sum(new_secret_numbers))
print("Part two answer:", price_map[max(price_map, key=price_map.get)])
