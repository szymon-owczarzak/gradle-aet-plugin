package com.wttech.aet.gradle.suite.test.url

import com.wttech.aet.gradle.common.sanitizeName

class URL(private val name: String) {

    lateinit var url: String

    fun build(): String = "\n      <url href=\"$url\" name=\"${name.sanitizeName()}\" />"
}