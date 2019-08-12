package pklimai.jsnapykotlindsl.examples

import pklimai.jsnapykotlindsl.lib.*

fun main() {
    val myTestFile = createJSNAPyTestFile {
//        jsnapyTest {
//            name = "First test"
//            command = "show interfaces"
//        }
        jsnapyTest {
            name = "Test interface admin status"
            rpc = "get-interface-information"
            kwarg("terse", "True")
            //kwarg("name", "ge-0/0/0")
            item {
                xpath = "physical-interface"
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
            //+"Second test"
        }
    }
    print(myTestFile)
}
