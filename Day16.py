def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data


def traverse_maze(grid):
    #                  r        d        l        u
    direction_map = [(0, 1), (-1, 0), (0, -1), (1, 0)]
    routes = []
    visited = {}

    visited_nodes_data = [(start, [start], 0, 0)]  # (current_row, current_col), node_path, score, direction
    while visited_nodes_data:
        (current_row, current_col), node_path, curr_score, curr_dir = visited_nodes_data.pop(0)

        # we are at the goal. update the routes dict to show the path and score to the goal
        if (current_row, current_col) == end:
            routes.append((node_path, curr_score))
            continue

        # we are at a node, check the current score. if we have gotten here in a "better" way, skip
        if ((current_row, current_col), curr_dir) in visited and visited[((current_row, current_col), curr_dir)] < curr_score:
            continue

        # update score for this node
        visited[((current_row, current_col), curr_dir)] = curr_score

        # check directions
        for dir_index, (row_change, col_change) in enumerate(direction_map):
            # check opposite direction
            if (curr_dir + 2) % 4 == dir_index:
                continue

            new_row, new_col = current_row + row_change, current_col + col_change
            if grid[new_row][new_col] != "#" and (new_row, new_col) not in node_path:
                # same direction
                if dir_index == curr_dir:
                    visited_nodes_data.append(((new_row, new_col), node_path + [(new_row, new_col)], curr_score + 1, dir_index))  # move forward
                # new direction
                else:
                    visited_nodes_data.append(((current_row, current_col), node_path, curr_score + 1000, dir_index))  # turn

    return routes


file_data = get_file_data("InputFile")
grid = []
start = None
end = None
row_number = 0
for line in file_data:
    row = []
    col_number = 0
    for letter in line:
        row.append(letter)
        if letter == "S":
            start = (row_number, col_number)
        elif letter == "E":
            end = (row_number, col_number)
        col_number += 1
    row_number += 1
    grid.append(row)


path_to_end = traverse_maze(grid)

all_end_scores = []
for path in path_to_end:
    all_end_scores.append(path[1])
print("Part one answer:", min(all_end_scores))

unique_tiles = set()

for path in path_to_end:
    if path[1] == min(all_end_scores):
        for tile in path[0]:
            unique_tiles.add(tile)

print("Part two answer:", len(unique_tiles))