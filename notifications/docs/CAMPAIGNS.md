# Campaigns & Segmentation

We use `CampaignManager` to orchestrate marketing and re-engagement campaigns.

## Scheduling
`CampaignScheduler` evaluates rules periodically via `WorkManager`.

## Segmentation
`AudienceMatcher` filters users based on `SegmentEvaluator` outputs.
