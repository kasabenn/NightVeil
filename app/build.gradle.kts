plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.nightveil"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.nightveil"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    // =====================================================
    // ANDROIDX
    // =====================================================

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)


    // =====================================================
    // MATERIAL DESIGN
    // =====================================================

    implementation(libs.material)


    // =====================================================
    // RECYCLERVIEW
    // =====================================================

    implementation(
        "androidx.recyclerview:recyclerview:1.3.2"
    )


    // =====================================================
    // CARDVIEW
    // =====================================================

    implementation(
        "androidx.cardview:cardview:1.0.0"
    )


    // =====================================================
    // GLIDE
    // =====================================================

    implementation(
        "com.github.bumptech.glide:glide:4.16.0"
    )


    // =====================================================
    // RETROFIT
    // =====================================================

    implementation(
        "com.squareup.retrofit2:retrofit:2.11.0"
    )

    implementation(
        "com.squareup.retrofit2:converter-gson:2.11.0"
    )


    // =====================================================
    // COROUTINES
    // =====================================================

    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1"
    )


    // =====================================================
    // LIFECYCLE / MVVM
    // =====================================================

    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4"
    )

    implementation(
        "androidx.lifecycle:lifecycle-livedata-ktx:2.8.4"
    )


    // =====================================================
    // TESTING
    // =====================================================

    testImplementation(libs.junit)

    androidTestImplementation(
        libs.androidx.espresso.core
    )

    androidTestImplementation(
        libs.androidx.junit
    )
}