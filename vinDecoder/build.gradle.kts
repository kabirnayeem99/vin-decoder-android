plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
//    kotlin("jvm") version "1.9.20"
    id("maven-publish")
    `maven-publish`
}

group = "com.github.kabirnayeem99"
version = "1.0.3"

android {
    namespace = "io.github.kabirnayeem99.vindecoder"
    compileSdk = 34

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }

    }

    defaultConfig {
        minSdk = 27
        aarMetadata {
            minCompileSdk = 27
        }
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

}

//val sourcesJar by tasks.creating(Jar::class) {
//    archiveClassifier.set("sources")
//    from(sourceSets.getByName("main").allSource)
//    from("LICENCE.md") {
//        into("META-INF")
//    }
//}

group = "com.github.kabirnayeem99"
version = "1.0.3"

publishing {

    publications {

        // Creates a Maven publication called "release".
        register("release", MavenPublication::class) {

            // Library Package Name (Example : "com.frogobox.androidfirstlib")
            // NOTE : Different GroupId For Each Library / Module, So That Each Library Is Not Overwritten
            groupId = "com.github.kabirnayeem99.vindecoderandroid"

            // Library Name / Module Name (Example : "androidfirstlib")
            // NOTE : Different ArtifactId For Each Library / Module, So That Each Library Is Not Overwritten
            artifactId = "vindecoderandroid"

            // Version Library Name (Example : "1.0.0")
            version = "1.0.3"

//            from(components["java"])

//            artifact(sourcesJar)

            pom {
                packaging = "jar"
                name.set("1.0.3")
                description.set("1.0.3")
            }

        }

    }

    repositories {
        maven { url = uri("https://jitpack.io") }
    }

}