package com.wttech.aet.gradle.suite.test.compare

interface Compare {

    fun buildCompare(): String

    fun compareAccessibility(
        reportLevel: String = "ERROR",
        ignoreNotice: Boolean = true,
        showExcluded: Boolean = true
    )

    fun compareCookie(
        action: String = "list",
        cookieName: String = "",
        cookieValue: String = "",
        showMatched: Boolean = true
    )

    fun compareJsErrors()

    fun compareLayout(pixelThreshold: Int = 0, percentageThreshold: Int = 0, fuzz: Int = 0)

    fun compareSource(compareType: String = "all")

    fun compareStatusCodes(
        filterRange: IntRange = 400..600,
        filterCodes: Set<Int> = setOf(),
        showExcluded: Boolean = true
    )

    fun compareW3C(ignoreWarnings: Boolean = true)

    fun jsErrorFilter(
        error: String = "",
        source: String = "",
        sourcePattern: String = "",
        errorPattern: String = "",
        line: Int = 0
    )
}