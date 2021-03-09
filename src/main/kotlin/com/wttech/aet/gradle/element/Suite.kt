package com.wttech.aet.gradle.element

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

open class Suite(private val name: String) {

    var buildPath: String = "./src/"
    var company: String = "Wunderman Thompson Technology"
    lateinit var projectName: String
    lateinit var domain: String

    private var tests = Tests()
    fun tests(action: Action<Tests>) = action.execute(tests)

    fun build(): String {
        val builder = StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        builder.append("\n<suite name=\"${name.replace("_", "-")}\" company=\"${company}\" project=\"${projectName}\" domain=\"${domain}\">")
        tests.build()
        builder.append("\n</suite>")
        return builder.toString()
    }
}