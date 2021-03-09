package com.wttech.aet.gradle.element

import org.gradle.api.Action

class Suites {

    private val suites = mutableSetOf<Suite>()

    infix operator fun String.invoke(action: Action<Suite>) = suites.add(Suite(this).apply { action.execute(this) })

    fun build() {
        suites.forEach { it.build() }
    }
}