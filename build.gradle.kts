// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlinVersion by extra("1.4.21")
    repositories {
        google()
        jcenter()
    }
    dependencies {

        classpath ("com.android.tools.build:gradle:4.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
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