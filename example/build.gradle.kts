plugins {
    id("com.wttech.aet.gradle")
}

defaultTasks("buildSuite")
description = "Gradle AET Plugin Example"
group = "com.cognifide.gradle"

val MARKETS = mapOf(
    "DE" to arrayOf("Test 1", "Test 2"),
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
                            execute("console.log('Hello World')")
                            waitForElementBySelector(".some-class", timeout = 15000)
                            resolution(1080)
                            takeScreenShot("Screen at 1080", css = ".main-container")
                            jsErrorFilter(errorPattern = "^.*net::ERR_TUNNEL_CONNECTION_FAILED.*\$")
                            jsErrorFilter(sourcePattern = ".*\\/content\\/dam")
                            click(".cta-button", timeout = 2000)
                            compareScreens(percentageThreshold = 10)
                            urls {
                                "Main Page" {
                                    url = "www.example.com/main"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}