package pklimai.jsnapykotlindsl.examples

import pklimai.jsnapykotlindsl.lib.*

fun main() {
    val myTestFile = createJSNAPyTestFile {
        jsnapyTest("First test") {
            command = "show interfaces"
        }
        jsnapyTest("Test interface admin status") {
            rpc = "get-interface-information"
            kwarg("terse", "True")
            kwarg("name", "ge-0/0/0")
            iterate {
                xpath = "physical-interface"
                id = "./name"
                testClause {
                    testop = "equals"
                    values = "admin-status, up"
                    info = "Test Succeeded for {{pre['name']}}"
                    err = "Test Failed for {{pre['name']}}"
                }
            }
        }
        testsInclude {
            +"Test interface admin status"
            +"Second test"
        }
    }

    print(myTestFile.toYAML())

}
