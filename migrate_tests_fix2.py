import os
import re

def fix_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Fix multiple @OptIn annotations on class
    # Find classes with multiple OptIns
    
    # Simple regex to find OptIn lines before class
    optin_regex = re.compile(r'@OptIn\((.*?)\)\n(?:@.*?\n)*@OptIn\((.*?)\)\nclass', re.MULTILINE)
    
    match = optin_regex.search(content)
    while match:
        # Merge them
        args1 = match.group(1)
        args2 = match.group(2)
        merged = f"{args1}, {args2}"
        
        # Replace the second OptIn line
        original_chunk = match.group(0)
        new_chunk = original_chunk.replace(f'@OptIn({args2})\n', '')
        new_chunk = new_chunk.replace(f'@OptIn({args1})', f'@OptIn({merged})')
        content = content.replace(original_chunk, new_chunk)
        match = optin_regex.search(content)
        
    # Replace composeTestRule followed by whitespace and a dot
    content = re.sub(r'composeTestRule\s*\.', '', content)
    
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)
    
    print(f"Fixed: {filepath}")

for root, _, files in os.walk('.'):
    for file in files:
        if file.endswith('Test.kt'):
            fix_file(os.path.join(root, file))
