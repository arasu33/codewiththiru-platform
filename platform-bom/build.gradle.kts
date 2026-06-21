plugins {
    `java-platform`
    id("codewiththiru.publishing")
}

group = "com.codewiththiru.platform"
version = "1.0.0-SNAPSHOT"

dependencies {
    constraints {
        api(project(":core"))
        api(project(":core-android"))
        api(project(":designsystem"))
        api(project(":about"))
        api(project(":feedback"))
        api(project(":rating"))
        api(project(":more-apps"))
        api(project(":coupons"))
        api(project(":updates"))
        
        api(project(":analytics-api"))
        api(project(":analytics"))
        
        api(project(":identity"))
        
        api(project(":ads-api"))
        api(project(":ads"))
        
        api(project(":remote-config-api"))
        api(project(":remote-config"))
        
        api(project(":notifications-api"))
        api(project(":notifications"))
        
        api(project(":billing-api"))
        api(project(":billing"))
        
        api(project(":ai-platform"))
        api(project(":security-platform"))
        api(project(":sync-backup"))
        api(project(":gamification"))
        api(project(":growth-platform"))
        api(project(":observability-platform"))
        api(project(":developer-platform"))
        api(project(":platform-framework"))
    }
}
