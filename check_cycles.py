import os
import re
from collections import defaultdict

deps = defaultdict(list)
for root, dirs, files in os.walk('.'):
    if 'build.gradle.kts' in files:
        module_path = root.replace('.\\', '').replace('\\', '/')
        if module_path == '.': continue
        module_name = ':' + module_path.replace('/', ':')
        content = open(os.path.join(root, 'build.gradle.kts')).read()
        matches = re.findall(r'(?:api|implementation|testImplementation|androidTestImplementation)\(project\(\"([^\"]+)\"\)\)', content)
        for m in matches:
            deps[module_name].append(m)

def find_cycles(graph):
    visited = set()
    path = []
    cycles = []
    
    def visit(node):
        if node in path:
            cycle_start = path.index(node)
            cycles.append(path[cycle_start:] + [node])
            return
        if node in visited:
            return
        visited.add(node)
        path.append(node)
        for neighbor in graph.get(node, []):
            visit(neighbor)
        path.pop()

    for node in graph:
        visit(node)
    return cycles

cycles = find_cycles(deps)
if cycles:
    print("Cycles found:")
    for c in cycles:
        print(" -> ".join(c))
else:
    print("No cycles found.")

# Also check for correct dependency directions
# e.g., data/domain/api modules
print("Dependency mapping:")
for k, v in deps.items():
    print(f"{k}: {v}")
