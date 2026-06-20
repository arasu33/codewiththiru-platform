package com.codewiththiru.syncbackup.scheduler

import com.codewiththiru.syncbackup.sync.SyncJob

interface SyncScheduler {
    fun scheduleJob(job: SyncJob)
    fun cancelJob(jobId: String)
    fun getPendingJobs(): List<SyncJob>
}
