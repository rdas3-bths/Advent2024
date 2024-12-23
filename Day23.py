from collections import defaultdict

import networkx


def get_file_data(file_name):
    f = open(file_name)
    data = []
    for line in f:
        data.append(line.rstrip())
    return data


def triplet_contains_t(triplet):
    for comp in triplet:
        if comp.startswith("t"):
            return True
    return False


networks = defaultdict(lambda : set())
file_data = get_file_data("input_file")


network = networkx.Graph()
for line in file_data:
    c1 = line.split("-")[0]
    c2 = line.split("-")[1]
    network.add_edge(c1, c2)

connections = list(networkx.enumerate_all_cliques(network))
part_one_answer = 0
for c in connections:
    if triplet_contains_t(c) and len(c) == 3:
        part_one_answer += 1

print("Part one answer:", part_one_answer)

largest_connections = connections[-1]
largest_connections.sort()
for i in range(len(largest_connections)):
    if i != len(largest_connections)-1:
        print(largest_connections[i], end=",")
    else:
        print(largest_connections[i])


