# About Module (`:about`)

The `:about` module is a robust, isolated, configuration-driven Jetpack Compose module providing an "About this App" feature across the CodeWithThiru platform ecosystem.

## Overview
This module abstracts app metadata, developer contacts, device diagnostics, and legal compliance links into a single unified `AboutScreen`. It enforces a decoupled architecture allowing zero internal networking dependencies.

## Key Features
- **Dynamic Configuration**: Fully configurable through `AboutConfig.Builder`.
- **UI State Wrapper**: Leverages `AboutUiState` for asynchronous loading architectures.
- **Provider Abstractions**: Uses `CustDeviceInfoProvider` and `CustBuildInfoProvider` for deterministic dependency injection.
- **Diagnostics Preview**: Built-in dialog preview for payload diagnostics before copying to clipboard.
- **Visual Testing**: Covered completely via native Robolectric `Roborazzi` snapshots.

## Usage
Refer to the `API_GUIDE.md` for integration details.
