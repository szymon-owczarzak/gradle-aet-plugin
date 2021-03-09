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
            if (exclude !="") " exclude-elements=\"$exclude\""
            else ""
        collect("<screen name=\"$name\" $selector$exlEl timeout=\"$timeout\"/>")
    }

    fun resolution(width: Int, height: Int = 0) {
        val h =
            if (height > 0) " height=\"$height\""
            else ""
        collect("<resolution width=\"$width\" $h />")
    }

    fun sleep(duration: Int = 1000) = collect("<sleep duration=\"$duration\" />")

    fun buildCollect(): String {
        collect.append("\n    </collect>")
        return collect.toString()
    }

    private fun collect(element: String) = collect.append("\n      $element")

    private fun domainProp(domain: String) = if (domain == "") "" else "cookie-domain=\"${domain}\""
}