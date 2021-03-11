package com.wttech.aet.gradle.suite.test.compare

import com.wttech.aet.gradle.AetException


class SourceComparator(var compareType: String = "all") {

    private val filters = mutableSetOf<String>()

    fun build(): String {
        if (!setOf("all", "content", "markup", "allFormatted").contains(compareType)) {
            throw AetException("(compareSource) Given 'compareType = $compareType' is not supported. Use \"all\", \"content\", \"markup\" or \"allFormatted\".")
        }
        val builder = StringBuilder("\n      <source comparator=\"source\" compareType=\"$compareType\"")
        if (filters.isNotEmpty()) {
            builder.append(">")
            filters.forEach { builder.append("\n        $it") }
            builder.append("\n      </source>")
        } else {
            builder.append(" />")
        }
        return builder.toString()
    }

    fun extractElementFilter(elementId: String = "", `class`: String = "") {
        if ((elementId == "" && `class` == "") || (elementId != "" && `class` != "")) {
            throw AetException("(extractElement) Use exactly one parameter 'elementId' or 'class'.")
        } else if (elementId != "") {
            filters.add("<extract-element elementId=\"$elementId\" />")
        } else {
            filters.add("<extract-element class=\"$`class`\" />")
        }
    }

    fun removeLinesFilter(dataRanges: String = "", patternRanges: String = "") {
        if (dataRanges == "" && patternRanges == "") {
            throw AetException("(removeLinesFilter) At least one parameter 'dataRanges' or 'patternRanges' is required.")
        }
        val dr = if (dataRanges != "") " dataRanges=\"$dataRanges\"" else ""
        val pr = if (patternRanges != "") " patternRanges=\"$patternRanges\"" else ""
        filters.add("<remove-lines$dr$pr />")
    }

    fun removeNodesFilter(xpath: String) {
        filters.add("<remove-nodes xpath=\"$xpath\" />")
    }

    fun removeRegexpFilter(dataRegExp: String = "", patternRegExp: String = "", regExp: String = "") {
        if (dataRegExp == "" && patternRegExp == "" && regExp == "") {
            throw AetException("(removeRegexpFilter) At least one parameter 'dataRegExp', 'patternRegExp' or 'regExp' is required.")
        }
        val re = if (regExp != "") " regExp='$regExp'" else ""
        val dre = if (regExp == "" && dataRegExp != "") " dataRegExp=\"$dataRegExp\"" else ""
        val pre = if (regExp == "" && patternRegExp != "") " patternRegExp=\"$patternRegExp\"" else ""
        filters.add("<remove-regexp$dre$pre$re />")
    }
}