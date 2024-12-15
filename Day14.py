import time

TILE_COLUMNS = 101
TILE_ROWS = 103


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    f.close()
    return data


def move_robot_once_x(robot):
    position = robot["position"]
    velocity = robot["velocity"]
    vx = velocity[0]

    if vx > 0:
        robot["position"] = (position[0]+1, position[1])
        if robot["position"][0] == TILE_COLUMNS:
            robot["position"] = (0, position[1])
    else:
        robot["position"] = (position[0]-1, position[1])
        if robot["position"][0] == -1:
            robot["position"] = (TILE_COLUMNS-1, position[1])


def move_robot_once_y(robot):
    position = robot["position"]
    velocity = robot["velocity"]
    vy = velocity[1]

    if vy > 0:
        robot["position"] = (position[0], position[1]+1)
        if robot["position"][1] == TILE_ROWS:
            robot["position"] = (position[0], 0)
    else:
        robot["position"] = (position[0], position[1]-1)
        if robot["position"][1] == -1:
            robot["position"] = (position[0], TILE_ROWS-1)


def move_robot(robot):
    velocity = robot["velocity"]
    vx = velocity[0]
    vy = velocity[1]
    for i in range(abs(vx)):
        move_robot_once_x(robot)
    for i in range(abs(vy)):
        move_robot_once_y(robot)


def print_grid(grid):
    for row in grid:
        for item in row:
            if item == 0:
                print(".", end=" ")
            else:
                print(item, end=" ")
        print()
    print()


def populate_grid(robots):

    grid = []

    for i in range(TILE_ROWS):
        row = []
        for j in range(TILE_COLUMNS):
            row.append(0)
        grid.append(row)

    for k in robots:
        row = robots[k]["position"][1]
        col = robots[k]["position"][0]
        grid[row][col] += 1

    return grid


def calculate_score(grid):
    middle_row = TILE_ROWS // 2
    middle_col = TILE_COLUMNS // 2

    q1 = 0
    q2 = 0
    q3 = 0
    q4 = 0
    for i in range(0, middle_row):
        for j in range(0, middle_col):
            q1 += grid[i][j]

    for i in range(0, middle_row):
        for j in range(middle_col + 1, TILE_COLUMNS):
            q2 += grid[i][j]

    for i in range(middle_row + 1, TILE_ROWS):
        for j in range(0, middle_col):
            q3 += grid[i][j]

    for i in range(middle_row + 1, TILE_ROWS):
        for j in range(middle_col + 1, TILE_COLUMNS):
            q4 += grid[i][j]

    return q1 * q2 * q3 * q4



file_data = get_file_data("input_file")

robots = {}

robot_number = 0
for line in file_data:
    robot_number += 1
    robots[robot_number] = {}

    position_x = int(line.split(" ")[0].split("=")[1].split(",")[0])
    position_y = int(line.split(" ")[0].split("=")[1].split(",")[1])
    position = (position_x, position_y)
    robots[robot_number]["position"] = position

    velocity_x = int(line.split(" ")[1].split("=")[1].split(",")[0])
    velocity_y = int(line.split(" ")[1].split("=")[1].split(",")[1])
    velocity = (velocity_x, velocity_y)
    robots[robot_number]["velocity"] = velocity

grid = populate_grid(robots)

for i in range(100):
    for k in robots:
        move_robot(robots[k])
    grid = populate_grid(robots)

m = calculate_score(grid)

# Part two was terrible
# I just let it run for a long time and recorded a video of the output
# I then looked at the video to look for a shape and found that a tree shows up around the 7000s

print("Part one answer:", calculate_score(grid))

