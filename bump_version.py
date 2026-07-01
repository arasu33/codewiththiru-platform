import os
import re

def process_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    original_content = content
    modified = False

    if file_path.endswith('build.gradle.kts'):
        # Be careful not to replace things like foojay-resolver-convention version
        if 'version = "1.0.0"' in content and not 'foojay-resolver' in content:
            content = content.replace('version = "1.0.0"', 'version = "1.0.1"')
            modified = True
        if 'versionName = "1.0.0"' in content:
            content = content.replace('versionName = "1.0.0"', 'versionName = "1.0.1"')
            modified = True
            
    elif file_path.endswith('.kt') and 'game-common' in file_path.replace('\\', '/'):
        # Add @Suppress("DEPRECATION") to DefaultRewardManager, DefaultSaveManager, DefaultGameSettingsManager
        if 'class DefaultRewardManager' in content and '@Suppress("DEPRECATION")' not in content:
            content = content.replace('class DefaultRewardManager', '@Suppress("DEPRECATION")\nclass DefaultRewardManager')
            modified = True
        if 'class DefaultSaveManager' in content and '@Suppress("DEPRECATION")' not in content:
            content = content.replace('class DefaultSaveManager', '@Suppress("DEPRECATION")\nclass DefaultSaveManager')
            modified = True
        if 'class DefaultGameSettingsManager' in content and '@Suppress("DEPRECATION")' not in content:
            content = content.replace('class DefaultGameSettingsManager', '@Suppress("DEPRECATION")\nclass DefaultGameSettingsManager')
            modified = True

    if modified:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Updated: {file_path}")

def main():
    root_dir = r"c:\Workspace\Dev\Android\codewiththiru-platform"
    for root, dirs, files in os.walk(root_dir):
        if '.git' in root or '\\build\\' in root or '/build/' in root:
            continue
        for file in files:
            process_file(os.path.join(root, file))

if __name__ == "__main__":
    main()
