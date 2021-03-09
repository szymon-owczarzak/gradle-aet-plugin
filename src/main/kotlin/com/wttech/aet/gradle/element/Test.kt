package com.wttech.aet.gradle.element

import groovy.lang.Closure
import org.gradle.api.NamedDomainObjectContainer

open class Test(private val name: String) : Collect() {

    lateinit var urls: NamedDomainObjectContainer<URL>

    fun urls(closure: Closure<URL>): NamedDomainObjectContainer<URL> = urls.configure(closure)

    fun build(): String {
        val builder = StringBuilder("\n  <test name=\"${name}\">")
        builder.append(buildCollect())

        builder.append("\n    <urls>")
        urls.forEach { builder.append(it.build()) }
        builder.append("\n    </urls>")
        builder.append("\n  </test>\n")
        return builder.toString()
    }


}