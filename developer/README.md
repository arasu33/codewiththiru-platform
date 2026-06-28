# Developer Platform

A comprehensive suite of infrastructure meant exclusively for testing, benchmarking, and architectural inspection. This nested project keeps QA and performance logic completely decoupled from production application bundles.

## Sub-Modules

### `:developer:test-utils`
Provides low-level Fakes for System APIs (Clock, UUIDs, Coroutine Dispatchers, Logging).

### `:developer:game-testing`
The ultimate fixture factory. Contains complex `Fake*Managers` (like `FakeLeaderboardManager`) and random data generators (`RandomProfileGenerator`) that can be used by all games to fuzzy test UI states without needing mock servers.

### `:developer:benchmark`
Provides helpers like `AllocationHelpers.measureExecutionTime` to verify that algorithmic changes (such as pathfinding in Sudoku) remain performant across iterations.

### `:developer:inspection`
Contains architectural assertions like `ThreadInspector.assertNotMainThread()` to enforce concurrency safety on critical database or network boundaries.

## Usage
Include the necessary modules via `testImplementation` (or standard `implementation` if building a developer-only sandbox app).
