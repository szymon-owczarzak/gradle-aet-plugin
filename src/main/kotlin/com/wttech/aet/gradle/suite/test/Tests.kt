package com.wttech.aet.gradle.suite.test

import org.gradle.api.Action
import java.util.stream.Collectors

class Tests {

    private val tests = mutableSetOf<Test>()

    infix operator fun String.invoke(action: Action<Test>) = tests.add(Test(this).apply { action.execute(this) })

    fun build(): String {
        return tests
            .stream()
            .map { it.build() }
            .collect(Collectors.joining())
    }
}