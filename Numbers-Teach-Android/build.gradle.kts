// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.5.0")
    val kotlin_gradle_plugin by extra("1.5.0")
    val build_tools_version by extra("30.0.3")
    val material_design_version by extra("1.3.0")
    val core_version by extra("1.5.0")
    val appcompat_version by extra("1.3.0")
    val preference_version by extra("1.1.1")
    val minimum_sdk_version by extra(21)
    val jvm_target by extra("1.8")
    val compile_sdk_version by extra(30)
    val target_sdk_version by extra(30)
    val sql_delight_version by extra("1.5.0")



    repositories {
        mavenCentral()
        google()
        maven(url = "https://www.jetbrains.com/intellij-repository/releases")
        maven(url = "https://jetbrains.bintray.com/intellij-third-party-dependencies")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-alpha01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_gradle_plugin")
        classpath("com.squareup.sqldelight:gradle-plugin:$sql_delight_version")

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
