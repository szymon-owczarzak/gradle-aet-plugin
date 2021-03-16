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

    fun accessibility(standard: String = "WCAG2AAA") = add("<accessibility standard=\"$standard\" />")

    fun cookie() = add("<cookie />")

    fun jsErrors() = add("<js-errors />")

    /**
     * Collect Screenshot
     *
     * @param  name name for the screenshot
     * @param  css selector, has precedent over xpath
     * @param  xpath
     * @param  exclude default not used
     * @param timeout defaults to 1000
     */
    fun screen(
        name: String, css: String = "", xpath: String = "", exclude: String = "", timeout: Int = 1000
    ) {
        val selector = getSelector("collectScreen", css, xpath, false)
        val exlEl = exclude.asAttr { " exclude-elements=\"$exclude\"" }
        add("<screen name=\"$name\"$selector$exlEl${getTimeout("screen", timeout)} />")
    }

    fun screenWithResolution(
        width: Int, name: String, height: Int = 0, samplingPeriod: Int = 100,
        css: String = "", xpath: String = "", exclude: String = "", timeout: Int = 1000
    ) {
        resolution(width, height, samplingPeriod)
        screen(name, css, xpath, exclude, timeout)
    }

    fun source() = add("<source />")

    fun statusCodes() = add("<status-codes />")

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