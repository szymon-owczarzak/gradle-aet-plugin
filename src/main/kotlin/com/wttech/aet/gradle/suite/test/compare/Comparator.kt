package com.wttech.aet.gradle.suite.test.compare

import com.wttech.aet.gradle.AetException
import com.wttech.aet.gradle.suite.test.filter.Filter
import com.wttech.aet.gradle.suite.test.filter.FilterCreator

class Comparator(filterCreator: Filter = FilterCreator()) : Filter by filterCreator, Compare {

    private val elements = mutableSetOf<String>()

    override fun buildCompare(
        comparators: Set<SourceComparator>,
        statusCodes: Set<StatusCodeComparator>
    ): String {
        val builder = StringBuilder()
        if (elements.isNotEmpty()) {
            builder.append("\n    <compare>")
            comparators.forEach { builder.append(it.build()) }
            statusCodes.forEach { builder.append(it.build()) }
            elements.forEach { builder.append("\n      $it") }
            builder.append(buildFilters())
            builder.append("\n    </compare>")
        }
        return builder.toString()
    }

    override fun compareAccessibility(
        reportLevel: String,
        ignoreNotice: Boolean,
        showExcluded: Boolean
    ) {
        if (!setOf("ERROR", "WARN", "NOTICE").contains(reportLevel)) {
            throw AetException("(compareAccessibility) Given reportLevel allows only \"ERROR\", \"WARN\", \"NOTICE\"")
        }
        elements.add("<accessibility report-level=\"$reportLevel\" ignore-notice=\"$ignoreNotice\" showExcluded=\"$showExcluded\" />")
    }

    override fun compareCookie(
        action: String,
        cookieName: String,
        cookieValue: String,
        showMatched: Boolean
    ) {
        val act =
            if (action == "list") ""
            else if (action == "test" || action == "compare") " action=\"$action\""
            else throw AetException("(compareCookie) Given 'action' is not supported. Use \"list\", \"test\" or \"compare\".")
        val cn =
            if (action == "test" && cookieName == "") throw AetException("(compareCookie) When 'action' is set to \"test\" then 'cookieName' is required.")
            else if (cookieName != "") " cookie-name=\"$cookieName\""
            else ""
        val cv =
            if (action == "test" && cookieValue != "") " cookie-value=\"$cookieValue\""
            else ""
        val sm =
            if (action == "compare") " showMatched=\"$showMatched\""
            else ""
        elements.add("<cookie$act$cn$cv$sm />")
    }

    override fun compareJsErrors() {
        elements.add("<js-errors />")
    }

    override fun compareLayout(pixelThreshold: Int, percentageThreshold: Int, fuzz: Int) {
        val pixTr = if (pixelThreshold > 0) " pixelThreshold=\"$pixelThreshold\"" else ""
        val perTr = if (percentageThreshold > 0) " percentageThreshold=\"$percentageThreshold\"" else ""
        val fu = if (fuzz > 0) " fuzz=\"$fuzz\"" else ""

        elements.add("<screen comparator=\"layout\"$pixTr$perTr$fu />")
    }

    override fun compareW3C(ignoreWarnings: Boolean) {
        elements.add("<source comparator=\"w3c-html5\" ignore-warnings=\"$ignoreWarnings\" />")
    }
}