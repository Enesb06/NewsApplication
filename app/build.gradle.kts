plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.newsapplication"
    compileSdk = 35


        buildFeatures {
            viewBinding =true
        }


    defaultConfig {
        applicationId = "com.example.newsapplication"
        minSdk = 24
        targetSdk = 35
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)



        // Retrofit
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0" )// JSON parse etmek için Gson

        // OkHttp Logging Interceptor (API isteklerini ve yanıtlarını loglamak için opsiyonel ama çok faydalı)
        implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

        // RecyclerView
        implementation ("androidx.recyclerview:recyclerview:1.3.0") // En güncel sürümü kontrol edin

        // Coroutines (Asenkron işlemler için)
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

        // ViewModel and LiveData (MVVM mimarisi için)
        implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
        implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

        // View Binding (XML elemanlarına daha kolay erişim için)
        // buildFeatures içinde viewBinding true yapılmalı (aşağıda gösterilecek)
    }
