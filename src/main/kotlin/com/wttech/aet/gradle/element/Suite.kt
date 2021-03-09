package com.wttech.aet.gradle.element

import org.gradle.api.Action
import com.wttech.aet.gradle.common.sanitize

open class Suite(private val name: String) {

    var company: String = "Wunderman Thompson Technology"
    lateinit var projectName: String
    lateinit var domain: String

    private var tests = Tests()
    fun tests(action: Action<Tests>) = action.execute(tests)

    fun build(): String {
        val builder = StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        builder.append("\n<suite name=\"${name.sanitize()}\" company=\"${company}\" project=\"${projectName}\" domain=\"${domain}\">")
        builder.append(tests.build())
        builder.append("\n</suite>")
        return builder.toString()
    }
}