package com.wttech.aet.gradle.suite.test.compare

import com.wttech.aet.gradle.AetException

class Comparator : Compare {

    private val compare = mutableSetOf<String>()
    private val errors = mutableSetOf<String>()

    override fun buildCompare(): String {
        val builder = StringBuilder()
        if (compare.isNotEmpty() || errors.isEmpty()) {
            builder.append("\n    <compare>")
            compare.forEach { builder.append("\n      $it") }
            if (errors.isNotEmpty()) {
                builder.append("\n      <js-errors>")
                errors.forEach { builder.append("\n        $it") }
                builder.append("\n      </js-errors>")
            }
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
        compare.add("<accessibility report-level=\"$reportLevel\" ignore-notice=\"$ignoreNotice\" showExcluded=\"$showExcluded\" />")
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
        compare.add("<cookie$act$cn$cv$sm />")
    }

    override fun compareJsErrors() {
        compare.add("<js-errors />")
    }

    override fun compareLayout(pixelThreshold: Int, percentageThreshold: Int, fuzz: Int) {
        val pixTr = if (pixelThreshold > 0) " pixelThreshold=\"$pixelThreshold\"" else ""
        val perTr = if (percentageThreshold > 0) " percentageThreshold=\"$percentageThreshold\"" else ""
        val fu = if (fuzz > 0) " fuzz=\"$fuzz\"" else ""

        compare.add("<screen comparator=\"layout\"$pixTr$perTr$fu />")
    }

    override fun compareSource(compareType: String) {
        if (!setOf("all", "content", "markup", "allFormatted").contains(compareType)) {
            throw AetException("(compareSource) Given 'compareType' is not supported. Use \"all\", \"content\", \"markup\" or \"allFormatted\".")
        }
        compare.add("<source comparator=\"source\" compareType=\"$compareType\" />")
    }

    override fun compareStatusCodes(
        filterRange: IntRange,
        filterCodes: Set<Int>,
        showExcluded: Boolean
    ) {
        val fc =
            if (filterCodes.isEmpty()) ""
            else " filterCodes=\"${filterCodes.joinToString { i: Int -> i.toString() }}\""
        compare.add("<status-codes filterRange=\"${filterRange.first},${filterRange.last}\"$fc showExcluded=\"$showExcluded\" />")
    }

    override fun compareW3C(ignoreWarnings: Boolean) {
        compare.add("<source comparator=\"w3c-html5\" ignore-warnings=\"$ignoreWarnings\" />")
    }

    override fun jsErrorFilter(
        error: String,
        source: String,
        sourcePattern: String,
        errorPattern: String,
        line: Int
    ) {
        val err = if (error != "") " error=\"$error\"" else ""
        val errPat = if (errorPattern != "") " errorPattern=\"$errorPattern\"" else ""
        val src = if (source != "") " source=\"$source\"" else ""
        val srcPat = if (sourcePattern != "") " sourcePattern=\"$sourcePattern\"" else ""
        val li = if (line > 0) " line=\"$line\"" else ""
        errors.add("<js-errors-filter$err$errPat$src$srcPat$li />")
    }
}