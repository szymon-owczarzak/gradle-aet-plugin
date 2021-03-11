package com.wttech.aet.gradle.suite.test.filter

import com.wttech.aet.gradle.AetException

class FilterCreator : Filter {

    private val errors = mutableSetOf<String>()
    private val accessibility = mutableSetOf<String>()

    override fun buildFilters(): String {
        val builder = StringBuilder()
        if (accessibility.isNotEmpty()) {
            builder.append("\n      <accessibility>")
            accessibility.forEach { builder.append("\n        $it") }
            builder.append("\n      </accessibility>")
        }
        if (errors.isNotEmpty()) {
            builder.append("\n      <js-errors>")
            errors.forEach { builder.append("\n        $it") }
            builder.append("\n      </js-errors>")
        }
        return builder.toString()
    }

    override fun accessibilityFilter(
        error: String,
        errorPattern: String,
        principle: String,
        line: Int,
        column: Int,
        markupCss: String
    ) {
        val err = if (error != "" && errorPattern == "") " error=\"$error\"" else ""
        val errPat = if (errorPattern != "") " errorPattern=\"$errorPattern\"" else ""
        val pri = if (principle != "") " principle=\"$principle\"" else ""
        val li = if (line > 0) " line=\"$line\"" else ""
        val col = if (column > 0) " column=\"$column\"" else ""
        val css = if (markupCss != "") " markupCss=\"$markupCss\"" else ""
        accessibility.add("<accessibility-filter$err$errPat$pri$li$col$css />")
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
