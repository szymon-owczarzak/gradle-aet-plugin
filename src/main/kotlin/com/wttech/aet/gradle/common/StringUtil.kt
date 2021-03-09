package com.wttech.aet.gradle.common

fun String.sanitize(): String = this.replace("_", "-").replace(" ", "-")
