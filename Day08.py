def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    f.close()
    return data

file_data = get_file_data("input_file")

# build a 2D List based on the file_data
grid = []
for line in file_data:
    row = []
    for letter in line:
        row.append(letter)
    grid.append(row)

rows = len(grid)
cols = len(grid[0])

frequencies = {}

for i in range(len(grid)):
    for j in range(len(grid[0])):
        if grid[i][j] != ".":
            frequencies[grid[i][j]] = []

for i in range(len(grid)):
    for j in range(len(grid[0])):
        if grid[i][j] != ".":
            frequencies[grid[i][j]].append((i,j))

unique_antinodes_part_one = set()
unique_antinodes_part_two = set()

for key in frequencies.keys():

    for point1 in frequencies[key]:
        for point2 in frequencies[key]:
            if point1 != point2:
                antinode_r = point1[0] + (point1[0] - point2[0])
                antinode_c = point1[1] + (point1[1] - point2[1])
                if (0 <= antinode_r < len(grid)) and (0 <= antinode_c < len(grid[0])):
                    unique_antinodes_part_one.add((antinode_r, antinode_c))

for key in frequencies.keys():

    for point1 in frequencies[key]:
        for point2 in frequencies[key]:
            if point1 != point2:
                # 0,0 & 1,3 --> (1, 3) (2, 6) (3, 9) (4, 12)
                row_delta = point2[0] - point1[0]
                col_delta = point2[1] - point1[1]
                antinode_r = point1[0]
                antinode_c = point1[1]
                while 0 <= antinode_r < rows and 0 <= antinode_c < cols:
                    unique_antinodes_part_two.add((antinode_r, antinode_c))
                    antinode_r = antinode_r + row_delta
                    antinode_c = antinode_c + col_delta


print("Part one answer:", len(unique_antinodes_part_one))
print("Part two answer:", len(unique_antinodes_part_two))
