plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  id ("kotlin-parcelize")
  id("com.google.devtools.ksp")
  id ("androidx.navigation.safeargs")
}

android {
  namespace = "com.technovice.appnote"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.technovice.appnote"
    minSdk = 28
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    viewBinding = true
    //noinspection DataBindingWithoutKapt
    dataBinding = true
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

  implementation(libs.room)
  implementation(libs.roomRuntime)
  ksp(libs.roomCompiler)

  implementation(libs.lifecycle)
  ksp(libs.lifecycleCompiler)
  implementation(libs.liveData)
  implementation(libs.viewModel)

  implementation(libs.navigationFragment)
  implementation(libs.navigationUI)

  implementation(libs.coroutines)

}