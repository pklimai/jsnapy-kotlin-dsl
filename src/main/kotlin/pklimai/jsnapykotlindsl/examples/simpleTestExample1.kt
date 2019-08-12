package pklimai.jsnapykotlindsl.examples

import pklimai.jsnapykotlindsl.lib.*

fun main() {
    val myTestFile = createJSNAPyTestFile {
        jsnapyTest {
            name = "First test"
            command = "show interfaces"
        }
        jsnapyTest {
            name = "Second test"
            rpc = "get-interface-information"
            kwargs = mutableListOf(Pair("terse", "True"), Pair("name", "ge-0/0/0"))
            item {
                xpath = "XPATH"
                testClause {
                    testop = "equals"
                    values = "admin-status, up"
                    info = "Test Succeeded for {{pre['name']}}"
                    err = "Test Failed for {{pre['name']}}"
                }
            }
        }
        testInclude("First test")
        testInclude("Second test")
    }
    print(myTestFile)
}

