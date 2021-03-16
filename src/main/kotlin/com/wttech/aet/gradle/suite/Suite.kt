package com.wttech.aet.gradle.suite

import org.gradle.api.Action
import com.wttech.aet.gradle.common.sanitize
import com.wttech.aet.gradle.common.sanitizeName
import com.wttech.aet.gradle.suite.test.Tests
import java.io.File

open class Suite(private val name: String) {

    var company: String = "Wunderman Thompson Technology"
    lateinit var projectName: String
    lateinit var domain: String

    /**
     * When set to true generated suites will printed to stdout
     */
    var verbose: Boolean = false

    /**
     * Where should suite be saved
     */
    var path: String = "./src"

    private var tests = Tests()
    fun tests(action: Action<Tests>) = action.execute(tests)

    fun build() {
        val builder = StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        builder.append("\n<suite name=\"${name.sanitizeName()}\" company=\"${company.sanitize()}\" project=\"${projectName.sanitize()}\" domain=\"${domain}\">")
        builder.append(tests.build())
        builder.append("\n</suite>")
        create(builder.toString())
    }

    private fun create(suite: String): String {
        println("\nCreating AET Suite named: ${name.sanitizeName()}")
        if (verbose) {
            println("\n$suite\n")
        }
        File("$path/${name.sanitize()}.xml").writeText(suite)
        return suite
    }
}