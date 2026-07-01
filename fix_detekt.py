import os
import re

def process_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    original_content = content
    modified = False

    # Find: catch (VAR: Exception) {\n            if (VAR is kotlinx.coroutines.CancellationException) throw VAR
    # Replace with: catch (VAR: kotlinx.coroutines.CancellationException) {\n            throw VAR\n        } catch (VAR: Exception) {
    
    # We will use regex
    pattern = re.compile(r'catch\s*\(\s*([a-zA-Z0-9_]+)\s*:\s*Exception\s*\)\s*\{\s*if\s*\(\s*\1\s*is\s*kotlinx\.coroutines\.CancellationException\s*\)\s*throw\s*\1')
    
    def replacer(match):
        var_name = match.group(1)
        return f"catch ({var_name}: kotlinx.coroutines.CancellationException) {{\n            throw {var_name}\n        }} catch ({var_name}: Exception) {{"

    if pattern.search(content):
        content = pattern.sub(replacer, content)
        modified = True

    if modified:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Fixed: {file_path}")

def main():
    root_dir = r"c:\Workspace\Dev\Android\codewiththiru-platform"
    for root, dirs, files in os.walk(root_dir):
        if '.git' in root or 'build' in root:
            continue
        for file in files:
            if file.endswith('.kt') or file.endswith('.java'):
                process_file(os.path.join(root, file))

if __name__ == "__main__":
    main()
