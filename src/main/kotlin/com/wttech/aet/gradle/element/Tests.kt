package com.wttech.aet.gradle.element

import org.gradle.api.Action

class Tests {

    private val tests = mutableSetOf<Test>()

    infix operator fun String.invoke(action: Action<Suite>) = tests.add(Test(this).apply { action.execute(this) })

    fun build() {
        tests.forEach { println(it.build()) }
    }
}