plugins {
    id(Plugins.LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KAPT)
    id(Plugins.DAGGER_HILT)
}

android {
    namespace = "cz.lastaapps.ui.login"
    compileSdk = App.COMPILE_SDK

    defaultConfig {
        minSdk = App.MIN_SDK
        targetSdk = App.TARGET_SDK

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = Versions.JAVA
        targetCompatibility = Versions.JAVA
    }
    kotlinOptions {
        jvmTarget = Versions.JVM_TARGET
        languageVersion = Versions.KOTLIN_LANGUAGE_VERSION
        apiVersion = Versions.KOTLIN_LANGUAGE_VERSION
    }
    buildFeatures {
        buildConfig = false
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

    implementation(project(Projects.api.login))
    implementation(project(Projects.ui.common))

    implementation(Libs.KOTLIN_COROUTINES)
    implementation(Libs.KM_LOGGING)

    implementation(Libs.MATERIAL)
    implementation(Libs.CORE)
    implementation(Libs.LIFECYCLE)

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