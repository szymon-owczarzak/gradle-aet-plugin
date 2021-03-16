package com.wttech.aet.gradle.suite.test

import com.wttech.aet.gradle.common.sanitizeName
import com.wttech.aet.gradle.suite.test.collect.Collector
import com.wttech.aet.gradle.suite.test.compare.Comparator
import com.wttech.aet.gradle.suite.test.url.URLs
import org.gradle.api.Action

open class Test(private val name: String) {

    private var urls = URLs()
    fun urls(action: Action<URLs>) = action.execute(urls)

    private var collect = Collector()
    fun collect(action: Action<Collector>) = action.execute(collect)

    private var compare = Comparator()
    fun compare(action: Action<Comparator>) = action.execute(compare)

    fun build(): String {
        val builder = StringBuilder("\n  <test name=\"${name.sanitizeName()}\">")
        builder.append(collect.built())
        builder.append(compare.build())
        builder.append("\n    <urls>")
        builder.append(urls.build())
        builder.append("\n    </urls>")
        builder.append("\n  </test>")
        return builder.toString()
    }
}