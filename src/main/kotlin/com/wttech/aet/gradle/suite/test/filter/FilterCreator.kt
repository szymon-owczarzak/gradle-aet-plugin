package com.wttech.aet.gradle.suite.test.filter

import com.wttech.aet.gradle.common.asAttr

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
        val errPat = errorPattern.asAttr { " errorPattern=\"$errorPattern\"" }
        val pri = principle.asAttr { " principle=\"$principle\"" }
        val li = line.asAttr { " line=\"$line\"" }
        val col = column.asAttr { " column=\"$column\"" }
        val css = markupCss.asAttr { " markupCss=\"$markupCss\"" }
        accessibility.add("<accessibility-filter$err$errPat$pri$li$col$css />")
    }

    override fun jsErrorFilter(
        error: String,
        source: String,
        sourcePattern: String,
        errorPattern: String,
        line: Int
    ) {
        val err = error.asAttr { " error=\"$error\"" }
        val errPat = errorPattern.asAttr { " errorPattern=\"$errorPattern\"" }
        val src = source.asAttr { " source=\"$source\"" }
        val srcPat = sourcePattern.asAttr { " sourcePattern=\"$sourcePattern\"" }
        val li = line.asAttr { " line=\"$line\"" }
        errors.add("<js-errors-filter$err$errPat$src$srcPat$li />")
    }
}
