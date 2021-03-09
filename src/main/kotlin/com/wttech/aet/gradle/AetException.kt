package com.wttech.aet.gradle

import org.gradle.api.GradleException

class AetException: GradleException {

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(message: String) : super(message)
}