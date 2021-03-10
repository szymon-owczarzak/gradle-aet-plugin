package com.wttech.aet.gradle.suite.test.collect

import com.wttech.aet.gradle.suite.test.modify.Modify

interface Collect : Modify {

    fun open()
    fun collectAccessibility(standard: String = "WCAG2AAA")
    fun collectCookie()
    fun collectJsErrors()
    fun collectScreen(name: String, css: String = "", xpath: String = "", exclude: String = "", timeout: Int = 1000)
    fun collectSource()
    fun collectStatusCodes()
    fun buildCollect(): String
}