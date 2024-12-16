from collections import deque

DIRECTIONS = {
    "<": (0, -1),
    ">": (0, 1),
    "^": (-1, 0),
    "v": (1, 0),
}


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    f.close()
    return data


def push_block(row, col, grid, move):
    next_row_offset, next_col_offset = DIRECTIONS[move]
    next_row = row + next_row_offset
    next_col = col + next_col_offset
    if grid[row][col] == ".":
        return
    if grid[next_row][next_col] == "O":
        push_block(next_row, next_col, grid, move)
    if grid[next_row][next_col] == "#":
        return
    if grid[next_row][next_col] == ".":
        grid[row][col] = "."
        grid[next_row][next_col] = "O"


def get_move_spot(row, col, move, grid):
    next_row_offset, next_col_offset = DIRECTIONS[move]
    next_row = row + next_row_offset
    next_col = col + next_col_offset
    return grid[next_row][next_col], next_row, next_col


def get_robot_position(grid):
    for i in range(len(grid)):
        for j in range(len(grid[0])):
            if grid[i][j] == "@":
                return i, j


def get_gps_score(grid):
    answer = 0
    for i in range(len(grid)):
        for j in range(len(grid[0])):
            if grid[i][j] == "O" or grid[i][j] == "[":
                answer += (100 * i + j)
    return answer


def do_part_one(grid, moves):
    for move in moves:
        robot_row, robot_col = get_robot_position(grid)
        next_spot_item, next_row, next_col = get_move_spot(robot_row, robot_col, move, grid)

        if next_spot_item == "O":
            push_block(next_row, next_col, grid, move)
            next_spot_item, next_row, next_col = get_move_spot(robot_row, robot_col, move, grid)

        if next_spot_item == ".":
            grid[robot_row][robot_col] = "."
            grid[next_row][next_col] = "@"

    print("Part one answer:", get_gps_score(grid))


def do_part_two(grid, moves):
    robot_row, robot_col = get_robot_position(grid)
    for move in moves:
        next_row, next_col = DIRECTIONS[move]
        can_move, items_to_move = get_items_to_move(grid, move, (robot_row, robot_col))
        if not can_move:
            continue
        for item_row, item_col in items_to_move[::-1]:
            if grid[item_row][item_col] == "@":
                robot_row, robot_col = (robot_row + next_row, robot_col + next_col)
            grid[item_row + next_row][item_col + next_col] = grid[item_row][item_col]
            grid[item_row][item_col] = "."

    print("Part two answer:", get_gps_score(grid))


def get_items_to_move(grid, move, pos):

    q = deque([pos])
    seen = set()
    items_to_move = []
    dr, dc = DIRECTIONS[move]
    while q:
        r, c = q.popleft()
        if (r, c) in seen:
            continue
        seen.add((r, c))
        if grid[r][c] == ".":
            continue
        elif grid[r][c] == "#":
            return False, []
        items_to_move.append((r, c))
        cur_p = grid[r][c]
        if cur_p == "[":
            if (r, c + 1) not in seen and move != ">":
                q.append((r, c + 1))
        elif cur_p == "]":
            if (r, c - 1) not in seen and move != "<":
                q.append((r, c - 1))
        q.append((r + dr, c + dc))
    return True, items_to_move


file_data = get_file_data("input_file")
part_one_grid = []
part_two_grid = []
instructions = ""

for line in file_data:
    if "#" in line:
        part_one_row = []
        part_two_row = []
        for letter in line:
            part_one_row.append(letter)
            if letter == "#" or letter == ".":
                part_two_row.append(letter)
                part_two_row.append(letter)
            if letter == "@":
                part_two_row.append("@")
                part_two_row.append(".")
            if letter == "O":
                part_two_row.append("[")
                part_two_row.append("]")
        part_one_grid.append(part_one_row)
        part_two_grid.append(part_two_row)
    if "^" in line:
        instructions += line

do_part_one(part_one_grid, instructions)
do_part_two(part_two_grid, instructions)
