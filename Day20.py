def distance(point1, point2):
    d = 0
    for i in range(len(point1)):
        d += abs(point1[i] - point2[i])
    return d


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data


def traverse_maze(grid, start, end):
    #                  r        d        l        u
    direction_map = [(0, 1), (-1, 0), (0, -1), (1, 0)]
    visited = {}

    unvisited = [(start, 0, 0)]
    while unvisited:
        (current_row, current_col), curr_score, curr_dir = unvisited.pop(0)

        if (current_row, current_col) in visited:
            continue

        visited[(current_row, current_col)] = curr_score

        for dir_index, (row_change, col_change) in enumerate(direction_map):
            if (curr_dir + 2) % 4 == dir_index:
                continue

            new_row, new_col = current_row + row_change, current_col + col_change
            if grid[new_row][new_col] != "#":
                unvisited.append(((new_row, new_col), curr_score + 1, dir_index))

    return visited


def find_cheat_node_part_one(grid):
    east_west_cheats = set()
    north_south_cheats = set()
    for r in range(len(grid)):
        for c in range(len(grid[0])):
            # check east west cheat
            if 0 < c < len(grid[0])-1:
                if grid[r][c] == "#":
                    if grid[r][c-1] == "." and grid[r][c+1] == ".":
                        east_west_cheats.add((r, c))
            # check north south
            if 0 < r < len(grid)-1:
                if grid[r][c] == "#":
                    if grid[r-1][c] == "." and grid[r+1][c] == ".":
                        north_south_cheats.add((r, c))

    return east_west_cheats, north_south_cheats


def find_cheats_part_two(visited):
    count = 0
    computed = set()
    for node1 in visited:
        for node2 in visited:
            if node1 != node2:
                d = distance(node1, node2)
                if d <= 20:
                    if abs(visited[node1] - visited[node2]) - d >= 100:

                        if not (node1, node2) in computed and not (node2, node1) in computed:
                            count += 1
                        computed.add((node1, node2))
                        computed.add((node2, node1))
    return count


def find_saved_time_horizontal(cheat, visited):
    row_cheat, col_cheat = cheat
    left_time = visited[(row_cheat, col_cheat-1)]
    right_time = visited[(row_cheat, col_cheat+1)]
    return abs(left_time - right_time) - 2


def find_saved_time_vertical(cheat, visited):
    row_cheat, col_cheat = cheat
    up_time = visited[(row_cheat-1, col_cheat)]
    down_time = visited[(row_cheat+1, col_cheat)]
    return abs(up_time - down_time) - 2



file_data = get_file_data("InputFile")
grid = []
start = None
end = None
row_number = 0
for line in file_data:
    row = []
    col_number = 0
    for letter in line:

        if letter == "S":
            start = (row_number, col_number)
            row.append(".")
        elif letter == "E":
            end = (row_number, col_number)
            row.append(".")
        else:
            row.append(letter)
        col_number += 1
    row_number += 1
    grid.append(row)


all_nodes = traverse_maze(grid, start, end)

east_west_cheats, north_south_cheats = find_cheat_node_part_one(grid)
part_one_ans = 0
for cheat in east_west_cheats:
    saved_time = find_saved_time_horizontal(cheat, all_nodes)
    if saved_time >= 100:
        part_one_ans += 1

for cheat in north_south_cheats:
    saved_time = find_saved_time_vertical(cheat, all_nodes)
    if saved_time >= 100:
        part_one_ans += 1

print("Part one answer:", part_one_ans)

part_two_answer = find_cheats_part_two(all_nodes)
print("Part two answer:", part_two_answer)
