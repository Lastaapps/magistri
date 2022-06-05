buildscript {
    dependencies {
        classpath(Classpath.DAGGER_HILT)
    }
}

plugins {
    id(Plugins.APPLICATION) version Versions.GRADLE apply false
    id(Plugins.LIBRARY) version Versions.GRADLE apply false
    id(Plugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false
    id(Plugins.SERIALIZABLE) version Versions.KOTLIN apply false
    id(Plugins.KSP) version Versions.KSP apply false
}
