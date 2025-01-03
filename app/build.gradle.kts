plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.quanlykhachsan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.quanlykhachsan"
        minSdk = 28
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation ("com.google.android.gms:play-services-auth:21.1.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.android.volley:volley:1.2.1")

    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    implementation(libs.firebase.auth)
    implementation(fileTree(mapOf(
        "dir" to "D:\\Android\\QuanLyKhachSan\\lib",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf("")
    )))

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("commons-codec:commons-codec:1.15")
    implementation("com.google.code.gson:gson:2.10")
}