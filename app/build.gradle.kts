plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.cash.paparazzi)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    signingConfigs {
//        getByName("debug") {
//            storeFile = file("/home/rhymezxcode/Documents/MyFood/myFood.jks")
//            storePassword = "testtest"
//            keyAlias = "key0"
//            keyPassword = "testtest"
//        }
    }
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.rhymezxcode.food"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    namespace = "com.rhymezxcode.food"
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.android.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.bundles.androidx.xr)
    implementation(libs.coil.compose)
    implementation(libs.coil.okhttp)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.hilt.android)
    implementation(libs.square.moshi.kotlin)
    implementation(libs.square.retrofit)
    implementation(libs.square.retrofit.converter.moshi)
    implementation(libs.navigation.compose)
    implementation(libs.compose.material.icons)
    implementation(libs.hilt.navigation.compose)

    // OkHttp and Logging Interceptor
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // firebase crashlytics
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    debugImplementation(platform(libs.compose.bom))
    debugImplementation(libs.compose.ui.test.manifest)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.square.leakcanary)

    // Use KSP for code generation
    ksp(libs.androidx.room.compiler)
    ksp(libs.hilt.compiler)
    ksp(libs.square.moshi.kotlin.codegen)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.compose.ui.test.junit)
    androidTestImplementation(libs.hilt.android.testing)
}

tasks.formatKotlinMain {
    exclude { it.file.path.contains("build/") }
}

tasks.lintKotlinMain {
    exclude { it.file.path.contains("build/") }
}
