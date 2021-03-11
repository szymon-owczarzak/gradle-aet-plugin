package com.wttech.aet.gradle.suite.test

import com.wttech.aet.gradle.common.sanitize
import com.wttech.aet.gradle.suite.test.collect.Collect
import com.wttech.aet.gradle.suite.test.collect.Collector
import com.wttech.aet.gradle.suite.test.compare.Comparator
import com.wttech.aet.gradle.suite.test.compare.Compare
import com.wttech.aet.gradle.suite.test.compare.SourceComparator
import com.wttech.aet.gradle.suite.test.compare.StatusCodeComparator
import com.wttech.aet.gradle.suite.test.url.URLs
import org.gradle.api.Action

open class Test(private val name: String, comparator: Compare = Comparator(), collector: Collect = Collector()) :
    Compare by comparator, Collect by collector {

    private var urls = URLs()
    fun urls(action: Action<URLs>) = action.execute(urls)

    private var sourceComparator = mutableSetOf<SourceComparator>()
    fun compareSource(compareType: String) = sourceComparator.add(SourceComparator(compareType))
    fun compareSource(action: Action<SourceComparator>) =
        sourceComparator.add(SourceComparator().apply { action.execute(this) })

    private var statusCodesComparator = mutableSetOf<StatusCodeComparator>()
    fun compareStatusCodes(
        filterRange: IntRange = 400..600,
        filterCodes: Set<Int> = setOf(),
        showExcluded: Boolean = true
    ) = statusCodesComparator.add(StatusCodeComparator(filterRange, filterCodes, showExcluded))

    fun compareStatusCodes(action: Action<StatusCodeComparator>) =
        statusCodesComparator.add(StatusCodeComparator().apply { action.execute(this) })

    fun build(): String {
        val builder = StringBuilder("\n  <test name=\"${name.sanitize()}\">")
        builder.append(buildCollect())
        builder.append(buildCompare(sourceComparator, statusCodesComparator))
        builder.append("\n    <urls>")
        builder.append(urls.build())
        builder.append("\n    </urls>")
        builder.append("\n  </test>")
        return builder.toString()
    }
}