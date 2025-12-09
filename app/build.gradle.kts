import org.gradle.internal.declarativedsl.parsing.main
import java.util.regex.Pattern.compile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

//repositories{
//    flatDir {
//        dirs("libs")
//    }
//}

android {
    namespace = "com.xxs.eat"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.xxs.eat"
        minSdk = 35
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
    sourceSets {
        named("main") {
            jniLibs.srcDirs("libs")
        }
    }



    dataBinding {
        enable = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(files("libs\\MobCommons-2017.0607.1736.jar"))
    implementation(files("libs\\MobTools-2017.0607.1736.jar"))
    implementation(files("libs\\jcore-android_v1.1.3.jar"))
    implementation(files("libs\\jpush-android_v3.0.6.jar"))
    implementation(libs.support.v13)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("androidx.viewpager:viewpager:1.0.0")
    implementation("com.squareup.picasso:picasso:2.3.2")
    implementation("com.nineoldandroids:library:2.4.0")
    implementation("com.daimajia.slider:library:1.1.5@aar")
    implementation("com.squareup.retrofit2:retrofit:2.0.2")
    implementation("com.squareup.retrofit2:converter-gson:2.0.2")
    kapt ("com.google.dagger:dagger-compiler:2.56")
    implementation("com.google.dagger:dagger:2.56")
    implementation(files("libs/SMSSDK-3.0.0.aar"))
    implementation("com.j256.ormlite:ormlite-android:5.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("io.reactivex:rxjava:1.3.0")
    implementation("io.reactivex:rxandroid:1.2.1")
    implementation("com.squareup.retrofit2:adapter-rxjava:2.1.0")


    implementation("com.google.android.material:material:1.4.0")
    implementation("se.emilsjolander:stickylistheaders:2.7.0")
}