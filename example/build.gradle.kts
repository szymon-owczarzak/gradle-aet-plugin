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
                            addCookie("cookie_name", "the_value")
                            open()
                            waitForPage()
                            execute("console.log('Hello World')")
                            waitForElementBySelector(".some-class", timeout = 15000)
                            resolution(1080)
                            takeScreenShot("Screen at 1080", css = ".main-container")
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