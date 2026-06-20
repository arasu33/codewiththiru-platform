# :more-apps

A plug-and-play Jetpack Compose module designed to drive cross-promotion effectively across all CodeWithThiru applications. 

## Features
* Configurable Layouts (Grid, List, Carousel, Featured)
* Agnostic Caching (Memory, custom implementation)
* Analytics and Event Hooks
* Deep integration with Google Play Store deep links

## Setup
1. Include the module in your `settings.gradle.kts`
2. Add dependency: `implementation(project(":more-apps"))`
3. Provide an implementation for `MoreAppsImageProvider` (e.g. using Coil).

## Getting Started
See the [API_GUIDE.md](API_GUIDE.md) for full usage examples.
