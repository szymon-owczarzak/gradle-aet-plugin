package com.wttech.aet.gradle.suite.test.modify

interface Modify {

    fun click(css: String = "", xpath: String = "", timeout: Int = 1000)

    fun addCookie(name: String, value: String, domain: String = "", path: String = "")

    fun removeCookie(name: String)

    fun executeJS(
        cmd: String = "",
        snippetUrl: String = "",
        basicAuthUsername: String = "",
        basicAuthPassword: String = ""
    )

    fun header(key: String, value: String, override: Boolean = false)

    fun hide(css: String = "", xpath: String = "", timeout: Int = 1000, leaveBlankSpace: Boolean = true)

    fun login(
        loginPage: String,
        login: String = "",
        password: String = "",
        loginInputSelector: String = "",
        passwordInputSelector: String = "",
        submitButtonSelector: String = "",
        loginTokenKey: String = "",
        timeout: Int = 1000,
        retrialNumber: Int = 3,
        forceLogin: Boolean = false
    )

    fun replaceText(
        value: String = "",
        attributeName: String = "innerHTML",
        css: String = "",
        xpath: String = "",
        timeout: Int = 1000
    )

    fun resolution(width: Int, height: Int = 0, samplingPeriod: Int = 100)

    fun scroll(css: String = "", xpath: String = "", position: String = "bottom")

    fun sleep(duration: Int = 1000)

    fun waitForElementBySelector(css: String = "", xpath: String = "", timeout: Int = 1000)

    fun waitForPage()
}