plugins {
    id(Plugins.APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KAPT)
    id(Plugins.DAGGER_HILT)
}

project.group = App.GROUP

android {
    namespace = App.APP_ID

    if (App.USE_LEGACY) {
        compileSdk = App.COMPILE_SDK
        buildToolsVersion = App.BUILD_TOOLS

        defaultConfig.targetSdk = App.TARGET_SDK
    } else {
        compileSdk = App.LEGACY_COMPILE_SDK
        buildToolsVersion = App.LEGACY_BUILD_TOOLS

        defaultConfig.targetSdk = App.LEGACY_TARGET_SDK
    }

    defaultConfig {
        applicationId = App.APP_ID

        //have to be specified explicitly for FDroid to work
        versionCode = 1000000 // 1x major . 2x minor . 2x path . 2x build diff
        versionName = "1.0.0"
        check(versionCode == App.VERSION_CODE)
        check(versionName == App.VERSION_NAME)

        minSdk = App.MIN_SDK

        resourceConfigurations.addAll(setOf("en", "cs"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        multiDexEnabled = true
    }
    lint {
        abortOnError = false
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"

            extra.set("alwaysUpdateBuildId", false)

            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = Versions.JAVA
        targetCompatibility = Versions.JAVA
    }

    kotlinOptions {
        jvmTarget = Versions.JVM_TARGET
        freeCompilerArgs = listOf(
            "-Xjvm-default=all-compatibility",
            "-opt-in=kotlin.RequiresOptIn",
            "-Xbackend-threads=4",
        )
        languageVersion = Versions.KOTLIN_LANGUAGE_VERSION
        apiVersion = Versions.KOTLIN_LANGUAGE_VERSION
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    coreLibraryDesugaring(Libs.DESUGARING)

    implementation(project(Projects.ui.login))

    implementation(Libs.APPCOMPAT)
    implementation(Libs.CORE)
    implementation(Libs.DATASTORE)
    implementation(Libs.LIFECYCLE)
    implementation(Libs.MATERIAL)
    implementation(Libs.SPLASHSCREEN)
    implementation(Libs.STARTUP)
    implementation(Libs.VECTOR_DRAWABLES)
    implementation(Libs.WINDOW_MANAGER)

    implementation(Libs.DAGGER_HILT)
    implementation(Libs.HILT_COMMON)
    implementation(Libs.HILT_NAVIGATION_COMPOSE)
    kapt(Libs.DAGGER_HILT_COMPILER)
    kapt(Libs.HILT_COMPILER)

    implementation(Libs.COMPOSE_ACTIVITY)
    implementation(Libs.COMPOSE_ANIMATION)
    implementation(Libs.COMPOSE_CONSTRAINTLAYOUT)
    implementation(Libs.COMPOSE_FOUNDATION)
    implementation(Libs.COMPOSE_ICONS_EXTENDED)
    implementation(Libs.COMPOSE_MATERIAL_3)
    implementation(Libs.COMPOSE_NAVIGATION)
    implementation(Libs.COMPOSE_TOOLING)
    implementation(Libs.COMPOSE_UI)
    implementation(Libs.COMPOSE_VIEWMODEL)

    implementation(Libs.ACCOMPANIST_DRAWABLE_PAINTERS)
    implementation(Libs.ACCOMPANIST_FLOW_LAYOUTS)
    implementation(Libs.ACCOMPANIST_NAVIGATION_ANIMATION)
    implementation(Libs.ACCOMPANIST_NAVIGATION_MATERIAL)
    implementation(Libs.ACCOMPANIST_PAGER)
    implementation(Libs.ACCOMPANIST_PERMISSION)
    implementation(Libs.ACCOMPANIST_PLACEHOLDER)
    implementation(Libs.ACCOMPANIST_SWIPE_TO_REFRESH)
    implementation(Libs.ACCOMPANIST_SYSTEM_UI)
}
