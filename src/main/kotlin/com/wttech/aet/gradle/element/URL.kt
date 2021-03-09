package com.wttech.aet.gradle.element

import com.wttech.aet.gradle.common.sanitize

class URL(private val name: String) {

    lateinit var url: String

    fun build(): String = "\n      <url=\"$url\" name=\"${name.sanitize()}\" />"
}