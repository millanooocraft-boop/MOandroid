defaultConfig {
    applicationId = "com.mo.assistant"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0.0"

    buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")
    buildConfigField("String", "ADMOB_APP_ID", "\"$admobAppId\"")
    buildConfigField("String", "ADMOB_BANNER_ID", "\"$admobBannerId\"")
}

buildTypes {
    release {
        isMinifyEnabled = false
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
        signingConfig = signingConfigs.getByName("debug")
    }
}

compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlinOptions {
    jvmTarget = "17"
    freeCompilerArgs += listOf(
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    )
}
buildFeatures {
    compose = true
    buildConfig = true
}
composeOptions {
    kotlinCompilerExtensionVersion = "1.5.10"
}
