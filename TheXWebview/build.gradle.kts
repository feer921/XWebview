plugins{
    id("com.android.library")
    kotlin("android")
}
apply(from = "maven_publish.gradle")

android{
    compileSdk = 31
    defaultConfig{
        minSdk = 19
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions{
        jvmTarget = "1.8" // 默认为 1.6，不变更这个，会导致 编译报错：Cannot inline bytecode built with JVM target 1.8 into bytecode that
    }

    publishing {
        singleVariant("release"){
            withSourcesJar()
        }
    }
}

//android {
//

//
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//}

dependencies {
    implementation(
        fileTree(
            mapOf(
                "dir" to "libs",
                "include" to listOf("*.jar")
            )
        )
    )
    implementation( "org.jetbrains.kotlin:kotlin-stdlib:1.8.20")
    implementation( "androidx.core:core-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.4.1")

    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    api("com.tencent.tbs:tbssdk:44286") //1. 移除文档打开 API。如需文档打开能力请移步：文档SDK



}