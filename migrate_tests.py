import os
import re

def migrate_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Skip if already migrated or doesn't have createComposeRule
    if 'createComposeRule' not in content:
        return

    # Replace imports
    content = re.sub(r'import androidx\.compose\.ui\.test\.junit4\.createComposeRule\n?', 
                     'import androidx.compose.ui.test.ExperimentalTestApi\nimport androidx.compose.ui.test.runComposeUiTest\n', 
                     content)
    
    # Remove @Rule and composeTestRule declaration
    content = re.sub(r'@get:Rule\s*val\s+composeTestRule\s*=\s*createComposeRule\(\)\n*', '', content)
    content = re.sub(r'@Rule\s*@JvmField\s*val\s+composeTestRule\s*=\s*createComposeRule\(\)\n*', '', content)
    
    # Add @OptIn to the class
    content = re.sub(r'class ([A-Za-z0-9_]+Test)', r'@OptIn(ExperimentalTestApi::class)\nclass \1', content)
    
    # Rewrite test functions
    # From: fun myTest() { ... composeTestRule.setContent ... }
    # To: fun myTest() = runComposeUiTest { ... setContent ... }
    
    # Find all @Test methods
    test_pattern = re.compile(r'(@Test.*?fun\s+[a-zA-Z0-9_]+\(\)\s*)\{', re.DOTALL)
    
    def replacer(match):
        return match.group(1) + "= runComposeUiTest {"
    
    content = test_pattern.sub(replacer, content)
    
    # Replace composeTestRule usages
    content = content.replace('composeTestRule.', '')
    
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)
    
    print(f"Migrated: {filepath}")

for root, _, files in os.walk('.'):
    for file in files:
        if file.endswith('Test.kt'):
            migrate_file(os.path.join(root, file))
