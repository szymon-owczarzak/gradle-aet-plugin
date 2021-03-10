# gradle-aet-plugin
Kotlin Gradle DSL for creating AET suites - **Only Kotlin DSL is supported**

## Example build.gradle.kts

```kotlin
plugins {
    id("com.wttech.aet.gradle")
}

defaultTasks("buildSuite")
group = "com.example.aet"

aet {
    suites {
        "Suite" {
            domain = "example.com"
            projectName = "Example"
            tests {
                "Test 1" {
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
```
