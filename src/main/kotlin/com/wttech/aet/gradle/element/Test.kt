package com.wttech.aet.gradle.element

import com.wttech.aet.gradle.common.sanitize
import org.gradle.api.Action

open class Test(private val name: String) : Collect() {

    private var urls = URLs()
    fun urls(action: Action<URLs>) = action.execute(urls)

    fun build(): String {
        val builder = StringBuilder("\n  <test name=\"${name.sanitize()}\">")
        builder.append(buildCollect())

        builder.append("\n    <urls>")
        builder.append(urls.build())
        builder.append("\n    </urls>")
        builder.append("\n  </test>\n")
        return builder.toString()
    }
}