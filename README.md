# gradle-aet-plugin
Kotlin Gradle DSL for creating AET suites - **Only Kotlin DSL is supported**

## Example build.gradle.kts

Example project: [click here](https://github.com/szymon-owczarzak/gradle-aet-plugin-examples)

```kotlin
plugins {
    id("com.wttech.aet.gradle")
}

defaultTasks("buildSuite") // 'buildSuite' task will generate suite xml
group = "com.example.aet"

aet {
    suites {
        "Suite" { // add suite
            domain = "example.com"
            projectName = "Example"
            tests {
                "Test 1" { // add test
                    collect {
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
                        click(".cta-button", timeout = 2000)
                    }
                    compare {
                        compareLayout(percentageThreshold = 10)
                        compareCookie()
                        compareJsErrors()
                        compareSource {
                            compareType = "content"
                            extractElementFilter(elementId = "login_form")
                            removeLinesFilter(patternRanges = "10,14;27,28")
                            removeNodesFilter("//*[@id='blueBarNAXAnchor']/div/div/div/a/i")
                            removeRegexpFilter(regExp = "\"correlationId\": \".*\"")
                        }
                        compareSource("allFormatted")
                        compareAccessibility(reportLevel = "WARN")
                        compareStatusCodes(400..500, filterCodes = setOf(404, 405))
                        compareStatusCodes {
                            filterRange = 400..405
                            exclude("http://www.external.com/_optional.js", pattern = "^.*js$")
                        }
                        jsErrorFilter(sourcePattern = ".*\\/content\\/dam")
                        jsErrorFilter(errorPattern = "^.*net::ERR_TUNNEL_CONNECTION_FAILED.*\$")
                        accessibilityFilter(
                            error = "This button element does not have a name available to the accessibility API.",
                            principle = "WCAG2A.Principle4.Guideline4_1.4_1_2.H91.Button.Name",
                            line = 21, column = 5
                        )
                        accessibilityFilter(principle = "WCAG2A.Principle1.Guideline1_3.1_3_1.F68")
                        accessibilityFilter(line = 317, column = 50)
                    }
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
