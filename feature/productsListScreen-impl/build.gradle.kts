plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id ("androidx.navigation.safeargs.kotlin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.example.productslistscreen_impl"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures{
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.9.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.3")
    implementation ("androidx.navigation:navigation-dynamic-features-fragment:2.9.3")


    implementation(project(":core:network-api"))
    implementation(project(":core:database-api"))
    implementation(project(":core:commonUi"))
    implementation(project(":navigation-api"))
    implementation(project(":di"))

    implementation ("com.google.dagger:dagger:2.57")
    kapt ("com.google.dagger:dagger-compiler:2.57")
}