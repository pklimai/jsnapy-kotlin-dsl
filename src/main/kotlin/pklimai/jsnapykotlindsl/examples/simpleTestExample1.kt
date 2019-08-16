package pklimai.jsnapykotlindsl.examples

import pklimai.jsnapykotlindsl.lib.*
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

fun main() {
    val myTestFile = createJSNAPyTestFile {
        jsnapyTest("First test") {
            command = "show interfaces"
        }
        jsnapyTest("Test interface admin status") {
            rpc = "get-interface-information"
            kwarg("terse", "True")
            //kwarg("name", "ge-0/0/0")
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
    // print(myTestFile)  // Using 'toString'

    val mapper = ObjectMapper(YAMLFactory())
    // See e.g. https://www.baeldung.com/jackson-ignore-null-fields
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    // This is to avoid empty collections like kwargs: []
    mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT)
    mapper.registerModule(KotlinModule())
    println(mapper.writeValueAsString(myTestFile))

}
