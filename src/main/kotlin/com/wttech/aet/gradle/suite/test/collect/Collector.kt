package com.wttech.aet.gradle.suite.test.collect

import com.wttech.aet.gradle.common.asAttr
import com.wttech.aet.gradle.common.getSelector
import com.wttech.aet.gradle.common.getTimeout
import com.wttech.aet.gradle.suite.test.modify.Modifier
import com.wttech.aet.gradle.suite.test.modify.Modify

open class Collector(
    private val elements: MutableList<String> = mutableListOf(),
    modifier: Modify = Modifier(elements)
) : Modify by modifier {

    fun open() = add("<open />")

    fun collectAccessibility(standard: String = "WCAG2AAA") = add("<accessibility standard=\"$standard\" />")

    fun collectCookie() = add("<cookie />")

    fun collectJsErrors() = add("<js-errors />")

    fun collectScreen(
        name: String, css: String = "", xpath: String = "", exclude: String = "", timeout: Int = 1000
    ) {
        val selector = getSelector("collectScreen", css, xpath, false)
        val exlEl = exclude.asAttr { " exclude-elements=\"$exclude\"" }
        add("<screen name=\"$name\" $selector$exlEl${getTimeout(timeout)} />")
    }

    fun collectSource() = add("<source />")

    fun collectStatusCodes() = add("<status-codes />")

    fun built(): String {
        val builder = StringBuilder()
        if (elements.isNotEmpty()) {
            builder.append("\n    <collect>")
            elements.forEach { builder.append("\n      $it") }
            builder.append("\n    </collect>")
        }
        return builder.toString()
    }

    private fun add(element: String) {
        elements.add(element)
    }
}