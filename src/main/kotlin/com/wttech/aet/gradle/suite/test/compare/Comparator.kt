package com.wttech.aet.gradle.suite.test.compare

import com.wttech.aet.gradle.AetException
import com.wttech.aet.gradle.suite.test.filter.Filter
import com.wttech.aet.gradle.suite.test.filter.FilterCreator
import org.gradle.api.Action

class Comparator(filterCreator: Filter = FilterCreator()) : Filter by filterCreator {

    private val elements = mutableSetOf<String>()
    private var sourceComparator = mutableSetOf<SourceComparator>()
    private var statusCodesComparator = mutableSetOf<StatusCodeComparator>()

    fun build(): String {
        val builder = StringBuilder()
        if (elements.isNotEmpty()) {
            builder.append("\n    <compare>")
            sourceComparator.forEach { builder.append(it.build()) }
            statusCodesComparator.forEach { builder.append(it.build()) }
            elements.forEach { builder.append("\n      $it") }
            builder.append(buildFilters())
            builder.append("\n    </compare>")
        }
        return builder.toString()
    }

    fun source(compareType: String) = sourceComparator.add(SourceComparator(compareType))
    fun source(action: Action<SourceComparator>) =
        sourceComparator.add(SourceComparator().apply { action.execute(this) })

    fun statusCodes(
        filterRange: IntRange = 400..600,
        filterCodes: Set<Int> = setOf(),
        showExcluded: Boolean = true
    ) = statusCodesComparator.add(StatusCodeComparator(filterRange, filterCodes, showExcluded))

    fun statusCodes(action: Action<StatusCodeComparator>) =
        statusCodesComparator.add(StatusCodeComparator().apply { action.execute(this) })

    fun accessibility(
        reportLevel: String = "ERROR",
        ignoreNotice: Boolean = true,
        showExcluded: Boolean = true
    ) {
        if (!setOf("ERROR", "WARN", "NOTICE").contains(reportLevel)) {
            throw AetException("(compareAccessibility) Given reportLevel allows only \"ERROR\", \"WARN\", \"NOTICE\"")
        }
        elements.add("<accessibility report-level=\"$reportLevel\" ignore-notice=\"$ignoreNotice\" showExcluded=\"$showExcluded\" />")
    }

    fun cookie(
        action: String = "list",
        cookieName: String = "",
        cookieValue: String = "",
        showMatched: Boolean = true
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

    fun jsErrors() {
        elements.add("<js-errors />")
    }

    fun layout(pixelThreshold: Int = 0, percentageThreshold: Int = 0, fuzz: Int = 0) {
        val pixTr = if (pixelThreshold > 0) " pixelThreshold=\"$pixelThreshold\"" else ""
        val perTr = if (percentageThreshold > 0) " percentageThreshold=\"$percentageThreshold\"" else ""
        val fu = if (fuzz > 0) " fuzz=\"$fuzz\"" else ""

        elements.add("<screen comparator=\"layout\"$pixTr$perTr$fu />")
    }

    fun W3C(ignoreWarnings: Boolean = true) {
        elements.add("<source comparator=\"w3c-html5\" ignore-warnings=\"$ignoreWarnings\" />")
    }
}