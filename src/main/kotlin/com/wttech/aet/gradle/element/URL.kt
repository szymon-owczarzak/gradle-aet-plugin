package com.wttech.aet.gradle.element

class URL(private val name: String) {

    lateinit var url: String

    fun build(): String = "\n      <url=\"$url\" name=\"$name\" />"
}