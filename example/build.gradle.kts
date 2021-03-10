plugins {
    id("com.wttech.aet.gradle")
}

defaultTasks("buildSuite")
description = "Gradle AET Plugin Example"
group = "com.cognifide.gradle"

val MARKETS = mapOf(
    "DE" to arrayOf("Test 1"),
    "UK" to arrayOf("Test 3")
)

aet {
    suites {
        MARKETS.forEach {
            "Suite ${it.key}" {
                domain = "example.com"
                projectName = "Example"
                tests {
                    it.value.forEach { test ->
                        test {
                            login("www.example.com/login", submitButtonSelector = ".submit-cta")
                            addCookie("cookie_name", "the_value")
                            open()
                            waitForPage()
                            header("Accept", "application/json")
                            executeJS("console.log('Hello World')")
                            waitForElementBySelector(".some-class", timeout = 15000)
                            resolution(1080)
                            collectScreen("Screen at 1080", css = ".main-container")
                            collectCookie()
                            collectAccessibility()
                            collectJsErrors()
                            jsErrorFilter(errorPattern = "^.*net::ERR_TUNNEL_CONNECTION_FAILED.*\$")
                            jsErrorFilter(sourcePattern = ".*\\/content\\/dam")
                            click(".cta-button", timeout = 2000)
                            compareLayout(percentageThreshold = 10)
                            compareCookie()
                            compareJsErrors()
                            compareAccessibility(reportLevel = "WARN")
                            compareStatusCodes(400..500, filterCodes = setOf(404, 405))
                            urls {
                                "Main Page" {
                                    url = "www.example.com/main"
                                }
                                "Login Page" {
                                    url = "www.example.com/login"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}