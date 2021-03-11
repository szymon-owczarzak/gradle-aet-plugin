package com.wttech.aet.gradle.suite.test.compare

import com.wttech.aet.gradle.suite.test.filter.Filter

interface Compare: Filter {

    fun buildCompare(
        comparators: Set<SourceComparator> = setOf(),
        statusCodes: Set<StatusCodeComparator> = setOf()
    ): String

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

    fun compareW3C(ignoreWarnings: Boolean = true)
}