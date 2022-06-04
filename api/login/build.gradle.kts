plugins {
    id(Plugins.LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.SERIALIZABLE)
    id(Plugins.KSP)
}

android {
    namespace = "cz.lastaapps.api.login"
    compileSdk = App.COMPILE_SDK

    defaultConfig {
        minSdk = App.MIN_SDK
        targetSdk = App.TARGET_SDK

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
        sourceCompatibility = Versions.JAVA
        targetCompatibility = Versions.JAVA
    }
    kotlinOptions {
        jvmTarget = Versions.JVM_TARGET
    }
}

dependencies {

    implementation(Libs.ROOM)
    implementation(Libs.ROOM_KTX)
    ksp(Libs.ROOM_COMPILER)

    implementation(Libs.KOTLIN_COROUTINES)

    implementation(Libs.KTOR_CORE)
    implementation(Libs.KTOR_CIO)
    implementation(Libs.KTOR_CONTENT_NEGOTIATION)
    implementation(Libs.KTOR_SERIALIZATION)
    implementation("io.ktor:ktor-client-auth:${Versions.KTOR}")

    implementation(Libs.KM_LOGGING)

    testImplementation(Tests.JUNIT)
    testImplementation(Tests.COROUTINES)
    testImplementation(Tests.KOTEST_ASSERTION)

    androidTestImplementation(Tests.COROUTINES)
    androidTestImplementation(Tests.KOTEST_ASSERTION)
    androidTestImplementation(Tests.RULES)
    androidTestImplementation(Tests.RUNNER)
}