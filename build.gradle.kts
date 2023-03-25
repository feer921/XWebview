// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlinVersion by extra("1.8.0")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {

        classpath ("com.android.tools.build:gradle:7.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

//task("clean"){
//    delete(buildDir)
//}


tasks{
    val clean by registering(Delete::class){
        delete(buildDir)
    }
}