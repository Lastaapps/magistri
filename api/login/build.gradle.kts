plugins {
    id(Plugins.LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.SERIALIZATION)
    id(Plugins.KAPT)
    id(Plugins.DAGGER_HILT)
    id(Plugins.SQLDELIGHT)
}

android {
    namespace = "cz.lastaapps.api.login"
    compileSdk = App.COMPILE_SDK

    defaultConfig {
        minSdk = App.MIN_SDK
        targetSdk = App.TARGET_SDK

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
}

kapt {
    correctErrorTypes = true
}

sqldelight {
    database("UserDatabase") {
        packageName = "cz.lastaapps.menza.db"
        sourceFolders = listOf("sqldelight")
        schemaOutputDirectory = File("schema")
    }
}

dependencies {

    coreLibraryDesugaring(Libs.DESUGARING)

    implementation(Libs.SQLDELIGHT_RUNTIME)
    implementation(Libs.SQLDELIGHT_ANDROID)
    implementation(Libs.SQLDELIGHT_COROUTINES)

    implementation(Libs.KOTLIN_COROUTINES)

    implementation(Libs.KTOR_CORE)
    implementation(Libs.KTOR_CIO)
    implementation(Libs.KTOR_CONTENT_NEGOTIATION)
    implementation(Libs.KTOR_SERIALIZATION)
    implementation(Libs.KTOR_AUTH)

    implementation(Libs.KM_LOGGING)

    implementation(Libs.DAGGER_HILT)
    implementation(Libs.HILT_COMMON)
    implementation(Libs.HILT_NAVIGATION_COMPOSE)
    kapt(Libs.DAGGER_HILT_COMPILER)
    kapt(Libs.HILT_COMPILER)

    testImplementation(Tests.JUNIT)
    testImplementation(Tests.COROUTINES)
    testImplementation(Tests.KOTEST_ASSERTION)

    androidTestImplementation(Tests.COROUTINES)
    androidTestImplementation(Tests.KOTEST_ASSERTION)
    androidTestImplementation(Tests.RULES)
    androidTestImplementation(Tests.RUNNER)
}