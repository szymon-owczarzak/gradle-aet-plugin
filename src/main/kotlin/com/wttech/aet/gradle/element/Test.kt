package com.wttech.aet.gradle.element

import com.wttech.aet.gradle.common.sanitize
import org.gradle.api.Action

open class Test(private val name: String) : Collect() {

    private var urls = URLs()
    fun urls(action: Action<URLs>) = action.execute(urls)

    private val compare = mutableSetOf<String>()
    private val errors = mutableSetOf<String>()

    fun compareScreens(pixelThreshold: Int = 0, percentageThreshold: Int = 0, fuzz: Int = 0) {
        val pixTr = if (pixelThreshold > 0) " pixelThreshold=\"$pixelThreshold\"" else ""
        val perTr = if (percentageThreshold > 0) " percentageThreshold=\"$percentageThreshold\"" else ""
        val fu = if (fuzz > 0) " fuzz=\"$fuzz\"" else ""

        compare.add("<screen comparator=\"layout\"$pixTr$perTr$fu />")
    }

    fun jsErrorFilter(
        error: String = "",
        source: String = "",
        sourcePattern: String = "",
        errorPattern: String = "",
        line: Int = 0
    ) {
        val err = if (error != "") " error=\"$error\"" else ""
        val errPat = if (errorPattern != "") " errorPattern=\"$errorPattern\"" else ""
        val src = if (source != "") " source=\"$source\"" else ""
        val srcPat = if (sourcePattern != "") " sourcePattern=\"$sourcePattern\"" else ""
        val li = if (line > 0) " line=\"$line\"" else ""
        errors.add("<js-errors-filter$err$errPat$src$srcPat$li />")
    }

    fun build(): String {
        val builder = StringBuilder("\n  <test name=\"${name.sanitize()}\">")
        builder.append(buildCollect())
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

        builder.append("\n    <urls>")
        builder.append(urls.build())
        builder.append("\n    </urls>")
        builder.append("\n  </test>")
        return builder.toString()
    }

}