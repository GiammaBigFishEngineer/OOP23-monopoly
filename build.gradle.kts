plugins {
    java
    application
    id("org.danilopianini.gradle-java-qa") version "1.28.0"
}

tasks.javadoc {
    isFailOnError = false
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.json:json:20171018")
    
    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")

    // Usato per sopprimere Spotbugs Warnings.
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.7.3") 
}

val mainClass: String by project

application {
    // The following allows to run with: ./gradlew -PmainClass=it.unibo.oop.MyMainClass run
    mainClass.set(project.properties["mainClass"].toString())
}

val test by tasks.getting(Test::class) {
    // Use junit platform for unit tests
    useJUnitPlatform()
    testLogging {
        events(*(org.gradle.api.tasks.testing.logging.TestLogEvent.values())) // events("passed", "skipped", "failed")
    }
    testLogging.showStandardStreams = true    
}
