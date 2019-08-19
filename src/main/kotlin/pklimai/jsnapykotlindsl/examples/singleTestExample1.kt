package pklimai.jsnapykotlindsl.examples

import pklimai.jsnapykotlindsl.lib.*

fun main() {
    val myTestFile = createJSNAPyTestFile {
        jsnapyTest("Test interface admin status") {
            rpc = "get-interface-information"
            kwarg("terse", "True")
            iterate {
                xpath = "physical-interface"
                id = "./name"
                testClause {
                    condition("is-equal", "admin-status, up")
                    info = "Test Succeeded for {{pre['name']}}"
                    err = "Test Failed for {{pre['name']}}"
                }
            }
        }
        testsInclude {
            +"Test interface admin status"
        }
    }
    print(myTestFile.toYAML())
}
