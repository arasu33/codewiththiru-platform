#!/usr/bin/env bash
# Usage: ./bump_version.sh [major|minor|patch]

if [ -z "$1" ]; then
  echo "Usage: ./bump_version.sh [major|minor|patch]"
  exit 1
fi

BUMP_TYPE=$1
PROPERTIES_FILE="gradle.properties"
README_FILE="README.md"

if [ ! -f "$PROPERTIES_FILE" ]; then
  echo "Error: $PROPERTIES_FILE not found!"
  exit 1
fi

# Extract current version
CURRENT_VERSION=$(grep -E "^PLATFORM_VERSION_NAME=" "$PROPERTIES_FILE" | cut -d '=' -f 2)
CURRENT_CODE=$(grep -E "^PLATFORM_VERSION_CODE=" "$PROPERTIES_FILE" | cut -d '=' -f 2)

if [ -z "$CURRENT_VERSION" ] || [ -z "$CURRENT_CODE" ]; then
  echo "Error: Could not extract version information from $PROPERTIES_FILE"
  exit 1
fi

IFS='.' read -r -a VERSION_PARTS <<< "$CURRENT_VERSION"
MAJOR="${VERSION_PARTS[0]}"
MINOR="${VERSION_PARTS[1]}"
PATCH="${VERSION_PARTS[2]}"

case "$BUMP_TYPE" in
  major)
    MAJOR=$((MAJOR + 1))
    MINOR=0
    PATCH=0
    ;;
  minor)
    MINOR=$((MINOR + 1))
    PATCH=0
    ;;
  patch)
    PATCH=$((PATCH + 1))
    ;;
  *)
    echo "Error: Invalid bump type '$BUMP_TYPE'. Use major, minor, or patch."
    exit 1
    ;;
esac

NEW_VERSION="$MAJOR.$MINOR.$PATCH"
NEW_CODE=$((CURRENT_CODE + 1))

echo "Bumping version from $CURRENT_VERSION ($CURRENT_CODE) to $NEW_VERSION ($NEW_CODE)..."

# Update gradle.properties
sed -i.bak -E "s/^PLATFORM_VERSION_NAME=.*/PLATFORM_VERSION_NAME=$NEW_VERSION/" "$PROPERTIES_FILE"
sed -i.bak -E "s/^PLATFORM_VERSION_CODE=.*/PLATFORM_VERSION_CODE=$NEW_CODE/" "$PROPERTIES_FILE"
rm -f "$PROPERTIES_FILE.bak"

# Update README.md
if [ -f "$README_FILE" ]; then
  sed -i.bak -E "s/## Version: v[0-9]+\.[0-9]+\.[0-9]+/## Version: v$NEW_VERSION/" "$README_FILE"
  sed -i.bak -E "s/platform-bom:[0-9]+\.[0-9]+\.[0-9]+/platform-bom:$NEW_VERSION/" "$README_FILE"
  rm -f "$README_FILE.bak"
fi

echo "Successfully bumped to $NEW_VERSION"
