from dataclasses import dataclass
from functools import cache
from itertools import permutations


@dataclass(frozen=True)
class Pos:
    row: int
    col: int

    def __add__(self, other):
        return Pos(self.row + other.row, self.col + other.col)

    def __sub__(self, other):
        return Pos(self.row - other.row, self.col - other.col)

    def __eq__(self, other):
        return isinstance(other, Pos) and (self.row, self.col) == (other.row, other.col)


# pad stuff
NUMPAD = {'7': Pos(0, 0), '8': Pos(0, 1), '9': Pos(0, 2),
          '4': Pos(1, 0), '5': Pos(1, 1), '6': Pos(1, 2),
          '1': Pos(2, 0), '2': Pos(2, 1), '3': Pos(2, 2),
          '0': Pos(3, 1), 'A': Pos(3, 2)}

NUMPAD_INV = {v: k for k,v in NUMPAD.items()}

DIRPAD = {'^': Pos(0, 1), 'A': Pos(0, 2),
          '<': Pos(1, 0), 'v': Pos(1, 1), '>': Pos(1, 2)}

DIRPAD_INV = {v: k for k,v in DIRPAD.items()}

DIRECTIONS = {'^': Pos(-1, 0), 'v': Pos(1, 0), '<': Pos(0, -1), '>': Pos(0, 1)}


def get_number(code):
    number = ""
    for i in range(len(code)):
        if code[i].isdigit():
            number += code[i]
    return int(number)


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data

@cache
def generate_moves(robot_id, current_key, dest_key, total_robots):
    if robot_id == 0:
        pad, pad_inv = (NUMPAD, NUMPAD_INV)
    else:
        pad, pad_inv = (DIRPAD, DIRPAD_INV)

    current_pos = pad[current_key]
    dest_pos = pad[dest_key]

    delta = dest_pos - current_pos

    if robot_id == total_robots-1:
        return abs(delta.row) + abs(delta.col) + 1

    moves = []
    for i in range(abs(delta.row)):
        if delta.row < 0:
            moves.append("^")
        else:
            moves.append("v")

    for i in range(abs(delta.col)):
        if delta.col < 0:
            moves.append("<")
        else:
            moves.append(">")

    candidates = []

    if len(moves) == 0:
        return 1

    for move_combo in set(permutations(moves)):
        pos = current_pos
        steps = 0
        skip = False
        for i, dir_key in enumerate(move_combo):
            if i == 0:
                steps += generate_moves(robot_id + 1, "A", dir_key, total_robots)
            else:
                steps += generate_moves(robot_id + 1, move_combo[i - 1], dir_key, total_robots)
            pos += DIRECTIONS[dir_key]

            if pos not in pad_inv:
                skip = True
                break

        if not skip:
            steps += generate_moves(robot_id + 1, move_combo[len(move_combo)-1], "A", total_robots)
            candidates.append(steps)

    return min(candidates)


codes = get_file_data("input_file")

total_length = 0
length = 0
for num_robots in [3, 26]:
    for code in codes:
        for row in range(len(code)):
            if row == 0:
                length = generate_moves(0, 'A', code[row], num_robots)
            else:
                length += generate_moves(0, code[row - 1], code[row], num_robots)
        total_length += length * get_number(code)
    if num_robots == 3:
        print("Part one answer:", end=" ")
    else:
        print("Part two answer:", end=" ")
    print(total_length)
