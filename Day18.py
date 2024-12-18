import time

MAX_GRID = 71
SIMULATE_BYTES = 1024
DIRECTIONS = [(0, 1), (0, -1), (1, 0), (-1, 0)]


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data

def make_grid():

    grid = []
    for i in range(MAX_GRID):
        row = []
        for j in range(MAX_GRID):
            row.append(".")
        grid.append(row)

    buffer_row = []
    for i in range(MAX_GRID):
        buffer_row.append("#")

    grid.insert(0, buffer_row)

    buffer_row = []
    for i in range(MAX_GRID):
        buffer_row.append("#")

    grid.append(buffer_row)

    for row in grid:
        row.insert(0, "#")
        row.append("#")

    return grid


def traverse_maze(grid):
    steps_to_goal = []
    start = (1, 1)
    end = (MAX_GRID, MAX_GRID)
    #                  r        d        l        u
    direction_map = [(0, 1), (-1, 0), (0, -1), (1, 0)]
    visited = {}

    unvisited = [(start, 0, 0)]
    while unvisited:
        (current_row, current_col), curr_score, curr_dir = unvisited.pop(0)
        if (current_row, current_col) == end:
            steps_to_goal.append(curr_score)

        if ((current_row, current_col), curr_dir) in visited:
            continue

        visited[((current_row, current_col), curr_dir)] = curr_score

        for dir_index, (row_change, col_change) in enumerate(direction_map):
            if (curr_dir + 2) % 4 == dir_index:
                continue

            new_row, new_col = current_row + row_change, current_col + col_change
            if grid[new_row][new_col] != "#":
                unvisited.append(((new_row, new_col), curr_score + 1, dir_index))

    return steps_to_goal


grid = make_grid()
file_data = get_file_data("InputFile")

all_bytes = []
for i in range(len(file_data)):
    points = file_data[i]
    row = int(points.split(",")[1])
    col = int(points.split(",")[0])
    all_bytes.append((row, col))

corrupted_bytes = []
for i in range(SIMULATE_BYTES):
    row, col = all_bytes[i]
    grid[row+1][col+1] = "#"

steps = traverse_maze(grid)
print("Part one answer:", min(steps))

# brute forcing this
# takes about 20-30 seconds to run
b = SIMULATE_BYTES
while len(steps) != 0:
    b += 1
    row, col = all_bytes[b]
    grid[row + 1][col + 1] = "#"
    steps = traverse_maze(grid)

print("Part two answer:", str(all_bytes[b][1]) + "," + str(all_bytes[b][0]))