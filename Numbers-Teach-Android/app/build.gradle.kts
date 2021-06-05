plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.squareup.sqldelight")
}

android {
    compileSdk = rootProject.extra["compile_sdk_version"] as Int
    buildToolsVersion = rootProject.extra["build_tools_version"] as String

    // Set both the Java and Kotlin compilers to target Java 8.

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = rootProject.extra["jvm_target"] as String
    }

    defaultConfig {
        applicationId = "com.dferreira.numbers_teach"
        minSdk = rootProject.extra["minimum_sdk_version"] as Int
        targetSdk = rootProject.extra["target_sdk_version"] as Int
        versionCode = 1
        versionName = "1.0"
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
    productFlavors {
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:${rootProject.extra["appcompat_version"]}")
    implementation("androidx.preference:preference-ktx:${rootProject.extra["preference_version"]}")
    implementation("com.google.android.material:material:${rootProject.extra["material_design_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.0")
    implementation("com.squareup.sqldelight:android-driver:${rootProject.extra["sqlDelightVersion"]}")
    implementation(project(":languages_dashboard"))
    implementation(project(":exercise_icons"))
    implementation(project(":commons"))
    implementation(project(":numbers"))
    implementation("androidx.core:core-ktx:${rootProject.extra["core_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${rootProject.extra["kotlin_version"]}")
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.squareup.sqldelight:sqlite-driver:${rootProject.extra["sqlDelightVersion"]}")


}
repositories {
    mavenCentral()
}
