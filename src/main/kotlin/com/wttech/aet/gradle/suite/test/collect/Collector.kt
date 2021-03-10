package com.wttech.aet.gradle.suite.test.collect

import com.wttech.aet.gradle.common.getSelector
import com.wttech.aet.gradle.common.getTimeout
import com.wttech.aet.gradle.suite.test.modify.Modifier
import com.wttech.aet.gradle.suite.test.modify.Modify

open class Collector(
    private val elements: MutableList<String> = mutableListOf(),
    modifier: Modify = Modifier(elements)
) : Modify by modifier, Collect {

    override fun open() = add("<open />")

    override fun collectAccessibility(standard: String) = add("<accessibility standard=\"$standard\" />")

    override fun collectCookie() = add("<cookie />")

    override fun collectJsErrors() = add("<js-errors />")

    override fun collectScreen(
        name: String,
        css: String,
        xpath: String,
        exclude: String,
        timeout: Int
    ) {
        val selector = getSelector("collectScreen", css, xpath, false)
        val exlEl =
            if (exclude != "") " exclude-elements=\"$exclude\""
            else ""
        add("<screen name=\"$name\" $selector$exlEl${getTimeout(timeout)} />")
    }

    override fun collectSource() = add("<source />")

    override fun collectStatusCodes() = add("<status-codes />")

    override fun buildCollect(): String {
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