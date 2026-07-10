import os, re
modules = ['analytics', 'ads', 'billing', 'notifications', 'remote-config']
packages_to_internalize = ['data', 'domain', 'config', 'api', 'presentation', 'engine']
for module in modules:
    for root, dirs, files in os.walk(f"{module}/src/main/java"):
        for file in files:
            if file.endswith('.kt') and any(f"/{pkg}/" in root.replace('\\', '/') for pkg in packages_to_internalize):
                path = os.path.join(root, file)
                content = open(path, encoding='utf-8').read()
                new_content = re.sub(r'\bpublic\s+(class|interface|object|fun|val|var|data class)\b', r'internal \1', content)
                if new_content != content:
                    with open(path, 'w', encoding='utf-8') as f:
                        f.write(new_content)
