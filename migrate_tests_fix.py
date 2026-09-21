import os
import re

def migrate_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Remove lingering composeTestRule references
    # It might be followed by whitespace, newlines, and a dot
    content = re.sub(r'composeTestRule\s*\.', '', content)
    content = re.sub(r'composeTestRule\b', '', content) # just to be safe if passed as param, but hopefully not
    
    # Fix double @OptIn
    # If the class now has multiple OptIn, we should merge or remove the extra one
    # If it has @OptIn(ExperimentalTestApi::class) multiple times
    
    # First, let's just find and replace double @OptIn(ExperimentalTestApi::class)
    while content.count('@OptIn(ExperimentalTestApi::class)') > 1:
        content = content.replace('@OptIn(ExperimentalTestApi::class)\n@OptIn(ExperimentalTestApi::class)', '@OptIn(ExperimentalTestApi::class)')
        content = re.sub(r'@OptIn\(ExperimentalTestApi::class\)\s*@OptIn\(ExperimentalTestApi::class\)', '@OptIn(ExperimentalTestApi::class)', content)
        
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)
    
    print(f"Fixed: {filepath}")

for root, _, files in os.walk('.'):
    for file in files:
        if file.endswith('Test.kt'):
            migrate_file(os.path.join(root, file))
