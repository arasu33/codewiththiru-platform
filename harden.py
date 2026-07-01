import os
import re
from pathlib import Path

def process_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    original_content = content
    modified = False

    # 1. Fix CancellationException swallowing
    # Find: catch (e: Exception) {
    # Some might have different spacing or variable name
    pattern = re.compile(r'catch\s*\(\s*([a-zA-Z0-9_]+)\s*:\s*Exception\s*\)\s*\{')
    
    def replacer(match):
        var_name = match.group(1)
        return f"catch ({var_name}: Exception) {{\n            if ({var_name} is kotlinx.coroutines.CancellationException || {var_name} is kotlin.coroutines.cancellation.CancellationException) throw {var_name}"
    
    if pattern.search(content):
        # Prevent double adding
        if "is kotlinx.coroutines.CancellationException" not in content and "is kotlin.coroutines.cancellation.CancellationException" not in content:
            content = pattern.sub(replacer, content)
            modified = True
        elif "is kotlin.coroutines.cancellation.CancellationException" not in content:
            # If it only has the kotlinx one, we should add the kotlin one too, but that requires more complex regex replacement.
            # Instead, just replace the existing one with both.
            content = re.sub(r'if\s*\([^)]+is\s+kotlinx\.coroutines\.CancellationException\)\s*throw\s+[a-zA-Z0-9_]+', 
                             lambda m: m.group(0).replace('throw ', 'throw ') + f" else if ({m.group(0).split()[-1]} is kotlin.coroutines.cancellation.CancellationException) throw {m.group(0).split()[-1]}", content)
            modified = True

    # 2. Fix mutable collections in main source sets (not test/testing)
    if 'src/main' in str(file_path).replace('\\', '/') and not '/testing/' in str(file_path).replace('\\', '/'):
        if 'mutableMapOf' in content or 'hashMapOf' in content or 'mutableListOf' in content:
            # Replace basic empty mutableMapOf<...>()
            content = re.sub(r'mutableMapOf\s*<([^>]+)>\s*\(\s*\)', r'java.util.concurrent.ConcurrentHashMap<\1>()', content)
            content = re.sub(r'=\s*mutableMapOf\s*\(\s*\)', r'= java.util.concurrent.ConcurrentHashMap()', content)
            
            # Replace hashMapOf
            content = re.sub(r'hashMapOf\s*<([^>]+)>\s*\(\s*\)', r'java.util.concurrent.ConcurrentHashMap<\1>()', content)
            content = re.sub(r'=\s*hashMapOf\s*\(\s*\)', r'= java.util.concurrent.ConcurrentHashMap()', content)

            # Replace mutableListOf
            content = re.sub(r'mutableListOf\s*<([^>]+)>\s*\(\s*\)', r'java.util.concurrent.CopyOnWriteArrayList<\1>()', content)
            content = re.sub(r'=\s*mutableListOf\s*\(\s*\)', r'= java.util.concurrent.CopyOnWriteArrayList()', content)

            if content != original_content:
                modified = True

    if modified:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Modified: {file_path}")

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
