package com.wttech.aet.gradle.common

import com.wttech.aet.gradle.AetException

fun String.sanitize(): String = this.replace("_", "-").replace(" ", "-")

fun getSelector(method: String, css: String, xpath: String, mandatory: Boolean = true): String {
    if (mandatory && css == "" && xpath == "") {
        throw AetException("($method) Either css or xpath must be provided.")
    }
    return if (css.isNotBlank()) "css=\"$css\""
    else "xpath=\"$xpath\""
}

fun getTimeout(timeout: Int = 1000, max: Int = 15000): String {
    if (timeout > max) {
        throw AetException("Given timeout value ($timeout) exceeds maxim value ($max)")
    }
    return if (timeout == 1000) ""
    else " timeout=\"$timeout\""
}