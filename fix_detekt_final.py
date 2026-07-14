import os
import re

def fix_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Find and fix the multiline pattern
    pattern = re.compile(
        r'\} catch \(([^:]+):\s*Exception\) \{\s*'
        r'if \(\1 is kotlinx\.coroutines\.CancellationException \|\|[\s\n]*'
        r'\1 is kotlin\.coroutines\.cancellation\.CancellationException[\s\n]*\)\s*\{\s*'
        r'throw \1\s*\}',
        re.MULTILINE
    )
    
    replacement = (
        r'} catch (\1: kotlinx.coroutines.CancellationException) {\n'
        r'            throw \1\n'
        r'        } catch (\1: kotlin.coroutines.cancellation.CancellationException) {\n'
        r'            throw \1\n'
        r'        } catch (\1: Exception) {'
    )
    
    new_content = pattern.sub(replacement, content)
    
    # Find and fix the single line pattern
    pattern2 = re.compile(
        r'\} catch \(([^:]+):\s*Exception\) \{\s*'
        r'if \(\1 is kotlinx\.coroutines\.CancellationException\) \{\s*'
        r'throw \1\s*\}',
        re.MULTILINE
    )
    replacement2 = (
        r'} catch (\1: kotlinx.coroutines.CancellationException) {\n'
        r'            throw \1\n'
        r'        } catch (\1: Exception) {'
    )
    
    new_content = pattern2.sub(replacement2, new_content)

    if new_content != content:
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print(f"Fixed: {filepath}")

for root, dirs, files in os.walk('.'):
    for file in files:
        if file.endswith('.kt'):
            fix_file(os.path.join(root, file))
