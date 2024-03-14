import jetbrains.sign.GpgSignSignatoryProvider

buildscript {
    repositories {
        maven { url = uri("https://packages.jetbrains.team/maven/p/jcs/maven") }
    }
    dependencies {
        classpath("com.jetbrains:jet-sign:38")
    }
}

plugins {
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    `maven-publish`
    signing
    java
}

var projectVersion = project.findProperty("projectVersion") as String
val publishingUser: String? = System.getenv("PUBLISHING_USER")
val publishingPassword: String? = System.getenv("PUBLISHING_PASSWORD")
if (publishingPassword == null) {
    projectVersion += "-SNAPSHOT"
}
println("##teamcity[setParameter name='java.annotations.version' value='$projectVersion']")

allprojects {
    // https://github.com/gradle/gradle/issues/847
    group = "org.jetbrains.proto"
    version = projectVersion

    repositories {
        mavenCentral()
    }

    apply {
        plugin("java")
        plugin("maven-publish")
        plugin("signing")
    }
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(publishingUser)
            password.set(publishingPassword)
        }
    }
}

configure(listOf(project(":java-annotations"), project(":multiplatform-annotations"))) {
    publishing {
        publications.withType(MavenPublication::class) {
            group = "org.jetbrains"
            version = rootProject.version as String

            pom {
                name.set("JetBrains Java Annotations")
                description.set("A set of annotations used for code inspection support and code documentation.")
                url.set("https://github.com/JetBrains/java-annotations")
                scm {
                    url.set("https://github.com/JetBrains/java-annotations")
                    connection.set("scm:git:git://github.com/JetBrains/java-annotations.git")
                    developerConnection.set("scm:git:ssh://github.com:JetBrains/java-annotations.git")
                }
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("JetBrains")
                        name.set("JetBrains Team")
                        organization.set("JetBrains")
                        organizationUrl.set("https://www.jetbrains.com")
                    }
                }
            }
        }
    }

    signing {
        sign(publishing.publications)
        signatories = GpgSignSignatoryProvider()
    }
}
