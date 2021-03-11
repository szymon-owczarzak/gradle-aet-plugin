plugins {
    id("com.gradle.plugin-publish") version "0.13.0"
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm") version "1.3.71"
    id("maven-publish")
}

defaultTasks(":publishToMavenLocal")

group = "com.szokone"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

repositories {
    jcenter()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

gradlePlugin {
    plugins {
        create("aet") {
            id = "com.szokone.aet.gradle"
            implementationClass = "com.wttech.aet.gradle.AetPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/szymon-owczarzak"
    vcsUrl = "https://github.com/szymon-owczarzak/gradle-aet-plugin"

    description = "Provides DSL for generating AET suites."

    (plugins) {
        "aet" {
            displayName = "Gradle AET Plugin"
            tags = listOf("aet", "kotlin")
            version = "1.0.1"
        }
    }
}

// Add a source set for the functional test suite
val functionalTestSourceSet = sourceSets.create("functionalTest") {
}

gradlePlugin.testSourceSets(functionalTestSourceSet)
configurations.getByName("functionalTestImplementation").extendsFrom(configurations.getByName("testImplementation"))

// Add a task to run the functional tests
val functionalTest by tasks.creating(Test::class) {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
}

val check by tasks.getting(Task::class) {
    // Run the functional tests as part of `check`
    dependsOn(functionalTest)
}
