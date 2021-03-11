package com.wttech.aet.gradle.suite.test.filter

interface Filter {

    fun buildFilters(): String

    fun accessibilityFilter(
        error: String = "",
        errorPattern: String = "",
        principle: String = "",
        line: Int = 0,
        column: Int = 0,
        markupCss: String = ""
    )

    fun jsErrorFilter(
        error: String = "",
        source: String = "",
        sourcePattern: String = "",
        errorPattern: String = "",
        line: Int = 0
    )
}