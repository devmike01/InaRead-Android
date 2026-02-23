import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.gbenga.inaread"
    compileSdk {
        version = release(36)
    }

    packaging.resources.pickFirsts.add("META-INF/gradle/incremental.annotation.processors")

    defaultConfig {
        applicationId = "dev.gbenga.inaread"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://b7eb-197-253-4-26.ngrok-free.app\"")
        }

        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"https://b7eb-197-253-4-26.ngrok-free.app\"")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        jvmToolchain(21)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.constraint.layout)
    implementation(libs.compose.icons.extention)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)

    ksp(libs.hilt.compiler)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Gson
    implementation(libs.gson)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.okhttp3.logging)

    // Mockk
    testImplementation(libs.mockk)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Datastore
    // Preferences DataStore (SharedPreferences like APIs)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)
    // Typed DataStore for custom data objects (for example, using Proto or JSON).
    implementation(libs.androidx.datastore)
    // okhttp
    implementation(libs.okhttp)

    // Retrofit
    implementation(libs.converter.gson.v2110)
// Also need the main retrofit library and the Gson library
    implementation(libs.retrofit.v300)
    implementation(libs.gson.v2101)

    // Room
    implementation(libs.androidx.room.runtime)

    // Android crypto
    implementation(libs.androidx.crypto.security)


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation(libs.kotlinx.serialization)
    implementation(libs.androidx.camera2)
    implementation(libs.compose.viewmodel)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.kotlinx.coroutines.test)
}