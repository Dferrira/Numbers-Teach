
plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = rootProject.extra["compile_sdk_version"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minimum_sdk_version"] as Int
        targetSdk = rootProject.extra["target_sdk_version"] as Int
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:${rootProject.extra["appcompat_version"]}")
    implementation("androidx.core:core-ktx:${rootProject.extra["core_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${rootProject.extra["kotlin_version"]}")
    implementation(project(":commons"))
}

repositories {
    mavenCentral()
}
