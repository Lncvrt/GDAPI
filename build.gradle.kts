plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-rc1"
    `maven-publish`
}

group = "xyz.lncvrt"
version = "1.0.0-alpha.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.17")
}

val sourcesJar = tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

tasks {
    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
    }

    build {
        dependsOn(shadowJar)
    }
}

publishing {
    repositories {
        maven {
            name = "lncvrtRepositoryReleases"
            url = uri("https://repo.lncvrt.xyz/releases")
            credentials {
                username = System.getenv("LNCVRT_REPO_USERNAME")
                password = System.getenv("LNCVRT_REPO_PASSWORD")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
        maven {
            name = "lncvrtRepositorySnapshots"
            url = uri("https://repo.lncvrt.xyz/snapshots")
            credentials {
                username = System.getenv("LNCVRT_REPO_USERNAME")
                password = System.getenv("LNCVRT_REPO_PASSWORD")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = rootProject.group as String?
            artifactId = rootProject.name.lowercase()
            version = rootProject.version as String?
            artifact(tasks.shadowJar)
            artifact(sourcesJar.get())
        }
    }
}