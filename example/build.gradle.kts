plugins {
    `kotlin-dsl`
    id("com.wttech.aet.gradle")
}

defaultTasks("buildSuite")
description = "Gradle AET Plugin Example"
group = "com.cognifide.gradle"

aet {
    suites {
        "test" {
            domain = "test"
            projectName = "proj"
        }
    }
}