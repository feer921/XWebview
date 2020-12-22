plugins{
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android{
    compileSdkVersion(29)
    defaultConfig{
        applicationId = "com.fee.xwebview.demo"
        minSdkVersion(19)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        dataBinding{
//            isEnabled = true
//        }
    }
    buildFeatures{
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions{
        jvmTarget = "1.8" // 默认为 1.6，不变更这个，会导致 编译报错：Cannot inline bytecode built with JVM target 1.8 into bytecode that
    }
}

dependencies {
    implementation(
        fileTree(
            mapOf(
                "dir" to "libs",
                "include" to listOf("*.jar")
            )
        )
    )
    implementation(project(mapOf("path" to ":TheXWebview")))
    implementation( "org.jetbrains.kotlin:kotlin-stdlib:1.4.21")
    implementation( "androidx.core:core-ktx:1.3.2")
    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation ("com.google.android.material:material:1.2.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation ("androidx.navigation:navigation-fragment:2.3.2")
    implementation ("androidx.navigation:navigation-ui:2.3.2")
//    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")//已经过时了
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.2")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.2")
    testImplementation ("junit:junit:4.13.1")
    androidTestImplementation ("androidx.test.ext:junit:1.1.2")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.3.0")
    implementation("org.tensorflow:tensorflow-lite:2.4.0")
}