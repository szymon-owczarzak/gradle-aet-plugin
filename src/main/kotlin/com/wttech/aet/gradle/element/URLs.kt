package com.wttech.aet.gradle.element

import org.gradle.api.Action
import java.util.stream.Collectors

class URLs {

    private val urls = mutableSetOf<URL>()

    infix operator fun String.invoke(action: Action<URL>) = urls.add(URL(this).apply { action.execute(this) })

    fun build(): String {
        return urls
            .stream()
            .map { it.build() }
            .collect(Collectors.joining())
    }
}