package com.wttech.aet.gradle

import com.wttech.aet.gradle.element.Suites
import org.gradle.api.Action

open class AetExtension {

    private val suites = Suites()

    fun suites(action: Action<Suites>) = action.execute(suites)

    fun build() {
        suites.build()
    }

    companion object {
        val NAME = "aet"
    }

}