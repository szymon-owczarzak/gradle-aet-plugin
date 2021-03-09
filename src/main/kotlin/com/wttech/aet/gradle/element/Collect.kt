package com.wttech.aet.gradle.element

open class Collect {
    private val collect = StringBuilder("\n    <collect>")

    fun addCookie(name: String, value: String, domain: String = "") =
        collect("<modify-cookie action=\"add\" cookie-name=\"${name}\" cookie-value=\"${value}\" ${domainProp(domain)}/>")

    fun removeCookie(name: String, value: String) =
        collect("<modify-cookie action=\"remove\" cookie-name=\"${name}\" />")

    fun open() = collect("<open />")

    fun waitForPage() = collect("<wait-for-page-loaded />")

    fun execute(
        cmd: String = "",
        snippetUrl: String = "",
        basicAuthUsername: String = "",
        basicAuthPassword: String = ""
    ) {
        val cmdOrSnippet =
            if (cmd.isNotBlank()) " cmd=\"$cmd\""
            else " snippetUrl=\"$snippetUrl\""
        val credentials =
            if (basicAuthUsername.isBlank() && basicAuthPassword.isBlank()) ""
            else " basicAuthUsername=\"$basicAuthUsername\" basicAuthPassword=\"$basicAuthPassword\""

        collect("<executejavascript$cmdOrSnippet$credentials />")
    }

    fun waitForElementBySelector(css: String = "", xpath: String = "", timeout: Int = 1000) {
        val selector =
            if (css.isNotBlank()) "css=\"$css\""
            else "xpath=\"$xpath\""
        collect("<wait-for-element-to-be-visible $selector timeout=\"$timeout\"/>")
    }

    fun takeScreenShot(name: String, css: String = "", xpath: String = "", exclude: String = "", timeout: Int = 1000) {
        val selector =
            if (css.isNotBlank()) "css=\"$css\""
            else "xpath=\"$xpath\""
        val exlEl =
            if (exclude != "") " exclude-elements=\"$exclude\""
            else ""
        collect("<screen name=\"$name\" $selector$exlEl timeout=\"$timeout\" />")
    }

    fun resolution(width: Int, height: Int = 0) {
        val h =
            if (height > 0) " height=\"$height\""
            else ""
        collect("<resolution width=\"$width\"$h />")
    }

    fun click(css: String = "", xpath: String = "", timeout: Int = 1000) {
        val selector =
            if (css.isNotBlank()) " css=\"$css\""
            else " xpath=\"$xpath\""
        collect("<click$selector timeout=\"$timeout\" />")
    }

    fun hide(css: String = "", xpath: String = "", timeout: Int = 1000, leaveBlankSpace: Boolean = true) {
        val selector =
            if (css.isNotBlank()) " css=\"$css\""
            else " xpath=\"$xpath\""
        collect("<hide$selector timeout=\"$timeout\" leaveBlankSpace=\"$leaveBlankSpace\" />")
    }

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
    ) {
        val l = if (login != "") " login=\"$login\"" else ""
        val p = if (password != "") " password=\"$password\"" else ""
        val lis = if (loginInputSelector != "") " login-input-selector=\"$loginInputSelector\"" else ""
        val pis = if (passwordInputSelector != "") " password-input-selector=\"$passwordInputSelector\"" else ""
        val sbs = if (submitButtonSelector != "") " submit-button-selector=\"$submitButtonSelector\"" else ""
        val ltk = if (loginTokenKey != "") " loginTokenKey=\"$loginTokenKey\"" else ""
        collect(
            "<login login-page=\"$loginPage\"$l$p$lis$pis$sbs$ltk" +
                    " timeout=\"$timeout\" force-login=\"$forceLogin\" retrial-number=\"$retrialNumber\" />"
        )
    }

    fun header(key: String, value: String, override: Boolean = false) =
        collect("<header key=\"$key\" value=\"$value\" override=\"$override\" />")

    fun sleep(duration: Int = 1000) = collect("<sleep duration=\"$duration\" />")

    fun buildCollect(): String {
        collect.append("\n    </collect>")
        return collect.toString()
    }

    private fun collect(element: String) = collect.append("\n      $element")

    private fun domainProp(domain: String) = if (domain == "") "" else "cookie-domain=\"${domain}\""
}