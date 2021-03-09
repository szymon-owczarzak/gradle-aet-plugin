package com.wttech.aet.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class AetPlugin: Plugin<Project> {

    override fun apply(project: Project) {

        project.extensions.create(AetExtension.NAME, AetExtension::class.java)

        project.tasks.register("buildSuites") { task ->
            task.doLast {
                val aet = project.extensions.getByName("aet") as AetExtension
                aet.build()
            }
        }
    }

    companion object {
        const val ID = "com.wttech.aet.gradle"
    }
}
