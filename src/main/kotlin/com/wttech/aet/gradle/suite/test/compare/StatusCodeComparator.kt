package com.wttech.aet.gradle.suite.test.compare

import com.wttech.aet.gradle.AetException
import com.wttech.aet.gradle.common.asAttr

class StatusCodeComparator(
    var filterRange: IntRange = 400..600,
    var filterCodes: Set<Int> = setOf(),
    var showExcluded: Boolean = true
) {

    private val filters = mutableSetOf<String>()

    fun build(): String {
        val fc =
            if (filterCodes.isEmpty()) ""
            else " filterCodes=\"${filterCodes.joinToString { i: Int -> i.toString() }}\""
        val builder =
            StringBuilder("\n      <status-codes filterRange=\"${filterRange.first},${filterRange.last}\"$fc showExcluded=\"$showExcluded\"")
        if (filters.isNotEmpty()) {
            builder.append(">")
            filters.forEach { builder.append("\n        $it") }
            builder.append("\n      </status-codes>")
        } else {
            builder.append(" />")
        }
        return builder.toString()
    }

    fun exclude(url: String = "", pattern: String = "") {
        if (url == "" && pattern == "") {
            throw AetException("(exclude) At least one parameter 'url' or 'pattern'.")
        }
        val URL = url.asAttr { " url=\"$url\"" }
        val pat = pattern.asAttr { " pattern=\"$pattern\"" }
        filters.add("<exclude$URL$pat />")
    }

}