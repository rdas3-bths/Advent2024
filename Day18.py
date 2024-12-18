MAX_GRID = 71
SIMULATE_BYTES = 1024
DIRECTIONS = [(0, 1), (0, -1), (1, 0), (-1, 0)]


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data


def do_part_one(corrupted_bytes):

    start_position = (0, 0)
    end_position = (MAX_GRID-1, MAX_GRID-1)

    visited = set()
    unvisited = [(start_position, (1, 0), 0), (start_position, (0, 1), 0)]

    while unvisited:
        current_position, current_direction, steps = unvisited.pop(0)
        visited.add(current_position)

        if current_position==end_position:
            return steps

        x, y = current_position

        for direction in DIRECTIONS:
            change_x, change_y = direction
            x_new, y_new = (x + change_x), (y + change_y)

            if x_new < 0 or x_new >= MAX_GRID or y_new < 0 or y_new >= MAX_GRID:
                continue

            if (x_new, y_new) in visited:
                continue

            if (x_new, y_new) in corrupted_bytes:
                continue

            item = ((x_new, y_new), direction, steps + 1)
            if item in unvisited:
                continue

            unvisited.append(item)

    return -1


file_data = get_file_data("InputFile")
corrupted_bytes = []
all_bytes = []

for i in range(len(file_data)):
    points = file_data[i]
    y = int(points.split(",")[1])
    x = int(points.split(",")[0])
    all_bytes.append((x, y))

for i in range(SIMULATE_BYTES):
    points = file_data[i]
    y = int(points.split(",")[1])
    x = int(points.split(",")[0])
    corrupted_bytes.append((x, y))

print("Part one answer:", do_part_one(corrupted_bytes))


b = 1024
while b < len(all_bytes):
    corrupted_bytes.append(all_bytes[b])
    steps = do_part_one(corrupted_bytes)
    if steps == -1:
        print("Part two answer:", str(all_bytes[b][0]) + "," + str(all_bytes[b][1]))
        break
    b += 1



