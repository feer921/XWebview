plugins{
    "kotlin-dsl"
}

repositories {
    jcenter()
}

task("delete"){
    delete(buildDir)
}
