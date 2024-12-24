from collections import defaultdict


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data


def binary_to_decimal(binary):
    decimal, i = 0, 0
    while binary != 0:
        dec = binary % 10
        decimal = decimal + dec * pow(2, i)
        binary = binary // 10
        i += 1
    return decimal


def evaluate_gate(gate, wires):
    first = wires[gate[0]]
    second = wires[gate[2]]
    logic = gate[1]
    if logic == "AND":
        return first and second
    if logic == "OR":
        return first or second
    if logic == "XOR":
        return first ^ second


wires = {}
gates = []
gates_original = []

file_data = get_file_data("input_file")

for line in file_data:
    if ":" in line:
        wire = line.split(": ")[0]
        value = line.split(": ")[1]
        if value == "1":
            wires[wire] = True
        else:
            wires[wire] = False
    if "->" in line:
        line = line.replace(" -> ", " ")
        gate_data = line.split(" ")
        gate = (gate_data[0], gate_data[1], gate_data[2], gate_data[3])
        gates.append(gate)
        gates_original.append(gate)

while len(gates) != 0:
    i = 0
    while True:
        gate = gates[i]
        first_wire = gate[0]
        second_wire = gate[2]
        if first_wire in wires and second_wire in wires:
            result = evaluate_gate(gate, wires)
            wires[gate[3]] = result
            gates.remove(gate)
            break
        i += 1

z_wires = []
x_wires = []
y_wires = []
for key in wires:
    if key.startswith("z"):
        z_wires.append(key)
    if key.startswith("x"):
        x_wires.append(key)
    if key.startswith("y"):
        y_wires.append(key)

z_wires.sort()
x_wires.sort()
y_wires.sort()

i = len(z_wires)-1
z_binary_result = ""
while i >= 0:
    if wires[z_wires[i]]:
        z_binary_result += "1"
    else:
        z_binary_result += "0"
    i -= 1

i = len(x_wires)-1
x_binary_result = ""
while i >= 0:
    if wires[x_wires[i]]:
        x_binary_result += "1"
    else:
        x_binary_result += "0"
    i -= 1

i = len(y_wires)-1
y_binary_result = ""
while i >= 0:
    if wires[y_wires[i]]:
        y_binary_result += "1"
    else:
        y_binary_result += "0"
    i -= 1

x = binary_to_decimal(int(x_binary_result))
y = binary_to_decimal(int(y_binary_result))
z = binary_to_decimal(int(z_binary_result))

print("Part one answer:", z)


# I figured these out manually by using these rules found on reddit:

# All XOR gates that input x__ and y__ cannot every output z__ (unless x00,y00 because the first one is a half adder)
#
# All other XOR gates must output z__
#
# All gates that output z__ must be XOR (except for z45, which is the final carry)
#
# All gates checked in (1) must output to gate checked in (2)
#
# If there are any swaps unaccounted for, manually review
#
# Apparently, this is a "adder" circuit that has rules to follow. I don't know this.
#
part_two_answer = [ "z06","z13","z38","jmq","gmh","qrh","cbd","rqf" ]
part_two_answer.sort()
print("Part two answer:",
      str(part_two_answer).replace("[", "").replace("]", "").replace("\'", "").replace(" ", ""))

