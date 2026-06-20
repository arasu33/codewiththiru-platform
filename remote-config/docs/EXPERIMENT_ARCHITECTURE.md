# Experiment Architecture

## Overview
A robust A/B testing and experimentation engine decoupled from the configuration provider.

## Bucketing Strategy
* `UserBucketingStrategy`: Interface for assigning users.
* `DeterministicHashingStrategy`: Default implementation. Combines `userId` and `experimentId` into a SHA-256 hash. Modulo 100 ensures a uniform, deterministic rollout based on variant weights.

## Stickiness
* `StickyAssignmentManager`: Uses DataStore to remember which variant a user was assigned to. This prevents users from flapping between variants if the rollout percentages change or they log in across devices (when combined with deterministic hashing).

## Exposure Tracking
* `ExperimentExposureTracker`: Automatically fires analytics events when a user evaluates an experiment, ensuring precise conversion metrics.

## Evaluator
* `ExperimentEvaluator`: Orchestrates the flow: Sticky Check -> Bucket Assign -> Sticky Save -> Exposure Track.
