plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.multimodule"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.multimodule"
        minSdk = 24
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation (project(":navigation-impl"))
    implementation(project(":core:network-impl"))
    implementation(project(":core:database-impl"))
    implementation(project(":core:network-api"))
    implementation(project(":core:database-api"))
    implementation(project(":core:commonUi"))
    implementation(project(":navigation-api"))
    implementation(project(":di"))
    implementation(project(":feature:productsListScreen-impl"))
    implementation(project(":feature:productsListScreen-api"))
    implementation(project(":feature:pdpProductScreen-impl"))
    implementation(project(":feature:pdpProductScreen-api"))
    implementation(project(":feature:cartScreen-impl"))
    implementation(project(":feature:cartScreen-api"))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("com.google.dagger:dagger:2.57")
    kapt ("com.google.dagger:dagger-compiler:2.57")
}