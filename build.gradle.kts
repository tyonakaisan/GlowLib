plugins {
    id("java")
    id("maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    // Paper
    compileOnly("io.papermc.paper", "paper-api", "1.20.2-R0.1-SNAPSHOT")

    // ProtocolLib
    compileOnly("com.comphenix.protocol", "ProtocolLib", "5.1.0")
}

tasks {
    compileJava {
        this.options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String

            from(components["java"])
        }
    }
}