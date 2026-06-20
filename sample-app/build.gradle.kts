plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.codewiththiru.platform.showcase"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.codewiththiru.platform.showcase"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":designsystem"))
    implementation(project(":analytics"))
    implementation(project(":ads"))
    implementation(project(":remote-config"))
    implementation(project(":feedback"))
    implementation(project(":rating"))
    implementation(project(":more-apps"))
    implementation(project(":updates"))
    implementation(project(":coupons"))
    implementation(project(":notifications"))
    implementation(project(":billing"))
    implementation(project(":security-platform"))
    implementation(project(":identity"))
    implementation(project(":observability-platform"))
    implementation(project(":growth-platform"))
    implementation(project(":platform-governance"))
    implementation(project(":ai-native-platform"))

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
}
