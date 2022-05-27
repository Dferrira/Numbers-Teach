// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.6.21")
    val kotlinGradlePlugin by extra("1.6.0")
    val material_design_version by extra("1.6.0")
    val core_version by extra("1.7.0")
    val appcompat_version by extra("1.4.1")
    val preference_version by extra("1.2.0")
    val minimum_sdk_version by extra(21)
    val jvm_target by extra("1.8")
    val compile_sdk_version by extra(32)
    val target_sdk_version by extra(32)
    val sqlDelightVersion by extra("1.5.3")



    repositories {
        mavenCentral()
        google()
        maven(url = "https://www.jetbrains.com/intellij-repository/releases")
        maven(url = "https://jetbrains.bintray.com/intellij-third-party-dependencies")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinGradlePlugin")
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven(
            url = "https://maven.google.com/"
        )
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
