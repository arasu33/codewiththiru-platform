# Architecture Rules

## Dependency Flow

core
↑
all modules

## Forbidden

- Circular dependencies
- Feature to feature dependencies

Examples:

ads -> analytics ❌
feedback -> ads ❌
about -> coupons ❌

## Allowed

ads -> core ✅
analytics -> core ✅
feedback -> core ✅

## Requirements

- SOLID
- Clean Architecture
- Dependency Inversion
- Composition over inheritance
- Feature isolation
