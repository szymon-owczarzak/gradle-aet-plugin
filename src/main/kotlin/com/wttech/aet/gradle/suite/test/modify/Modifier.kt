package com.wttech.aet.gradle.suite.test.modify

import com.wttech.aet.gradle.AetException
import com.wttech.aet.gradle.common.getSelector
import com.wttech.aet.gradle.common.getTimeout

class Modifier(private val elements: MutableList<String>) : Modify {

    override fun click(css: String, xpath: String, timeout: Int) {
        val selector = getSelector("click", css, xpath)
        elements.add("<click $selector${getTimeout(timeout)} />")
    }

    override fun addCookie(name: String, value: String, domain: String, path: String) {
        val dom = if (domain == "") "" else " cookie-domain=\"${domain}\""
        val pat = if (path == "") "" else " cookie-path=\"${path}\""
        elements.add("<modify-cookie action=\"add\" cookie-name=\"${name}\" cookie-value=\"${value}\"$dom$pat />")
    }

    override fun removeCookie(name: String) {
        elements.add("<modify-cookie action=\"remove\" cookie-name=\"${name}\" />")
    }

    override fun executeJS(
        cmd: String,
        snippetUrl: String,
        basicAuthUsername: String,
        basicAuthPassword: String
    ) {
        if (cmd == "" && snippetUrl == "") {
            throw AetException("Either cmd or snippetUrl must be provided.")
        }
        val cmdOrSnippet =
            if (cmd.isNotBlank()) " cmd=\"$cmd\""
            else " snippetUrl=\"$snippetUrl\""
        val credentials =
            if (basicAuthUsername.isBlank() && basicAuthPassword.isBlank()) ""
            else " basicAuthUsername=\"$basicAuthUsername\" basicAuthPassword=\"$basicAuthPassword\""

        elements.add("<executejavascript$cmdOrSnippet$credentials />")
    }

    override fun header(key: String, value: String, override: Boolean) {
        elements.add("<header key=\"$key\" value=\"$value\" override=\"$override\" />")
    }

    override fun hide(css: String, xpath: String, timeout: Int, leaveBlankSpace: Boolean) {
        val selector = getSelector("hide", css, xpath)
        elements.add("<hide$selector timeout=\"$timeout\" leaveBlankSpace=\"$leaveBlankSpace\" />")
    }

    override fun login(
        loginPage: String,
        login: String,
        password: String,
        loginInputSelector: String,
        passwordInputSelector: String,
        submitButtonSelector: String,
        loginTokenKey: String,
        timeout: Int,
        retrialNumber: Int,
        forceLogin: Boolean
    ) {
        val l = if (login != "") " login=\"$login\"" else ""
        val p = if (password != "") " password=\"$password\"" else ""
        val lis = if (loginInputSelector != "") " login-input-selector=\"$loginInputSelector\"" else ""
        val pis = if (passwordInputSelector != "") " password-input-selector=\"$passwordInputSelector\"" else ""
        val sbs = if (submitButtonSelector != "") " submit-button-selector=\"$submitButtonSelector\"" else ""
        val ltk = if (loginTokenKey != "") " loginTokenKey=\"$loginTokenKey\"" else ""
        elements.add(
            "<login login-page=\"$loginPage\"$l$p$lis$pis$sbs$ltk" +
                    " timeout=\"$timeout\" force-login=\"$forceLogin\" retrial-number=\"$retrialNumber\" />"
        )
    }

    override fun replaceText(
        value: String,
        attributeName: String,
        css: String,
        xpath: String,
        timeout: Int
    ) {
        val selector = getSelector("replaceText", css, xpath)
        elements.add("<replaceText$selector attribute=\"$attributeName\" value=\"$value\"${getTimeout(timeout)} />")
    }

    override fun resolution(width: Int, height: Int, samplingPeriod: Int) {
        val h =
            if (height > 0) " height=\"$height\""
            else ""
        val sp =
            if (samplingPeriod != 100 && samplingPeriod > 0) " sampling-period=\"$samplingPeriod\""
            else ""
        elements.add("<resolution width=\"$width\"$h$sp />")
    }

    override fun scroll(css: String, xpath: String, position: String) {
        val selector = getSelector("scroll", css, xpath)
        val pos =
            if (position != "bottom") " position=\"$position\""
            else ""
        elements.add("<scroll$selector$pos />")
    }

    override fun sleep(duration: Int) {
        if (duration in 0..30000) {
            elements.add("<sleep duration=\"$duration\" />")
        } else {
            throw  AetException("Sleep duration ($duration) should be in range ${0..30000}")
        }
    }

    override fun waitForElementBySelector(css: String, xpath: String, timeout: Int) {
        val selector = getSelector("waitForElementBySelector", css, xpath)
        elements.add("<wait-for-element-to-be-visible $selector${getTimeout(timeout)} />")
    }

    override fun waitForPage() {
        elements.add("<wait-for-page-loaded />")
    }
}