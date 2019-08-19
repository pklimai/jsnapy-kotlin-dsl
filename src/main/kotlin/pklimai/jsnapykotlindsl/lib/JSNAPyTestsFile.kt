package pklimai.jsnapykotlindsl.lib

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule

// Represents a file with JSNAPy tests
@YAMLElementMarker
class JSNAPyTestsFile {
    // Test definitions
    @SuppressWarnings // Do not make private, or Jackson serialization does not work
    val tests = mutableListOf<JSNAPyTest>()

    fun jsnapyTest(name: String, block: JSNAPyTest.() -> Unit) {
        tests.add(JSNAPyTest(name).apply(block))
    }

    // Optional list of test names to execute - by default, all tests will be executed
    @SuppressWarnings // Do not make private, or Jackson serialization does not work
    val testsInclude = ListOfTestsInclude()

    fun testsInclude(block: ListOfTestsInclude.() -> Unit) {
        testsInclude.apply(block)
    }

    fun toYAML(): String {
        val mapper = ObjectMapper(YAMLFactory())
        // See e.g. https://www.baeldung.com/jackson-ignore-null-fields
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        // This is to avoid empty collections like kwargs: []
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT)
        mapper.registerModule(KotlinModule())
        return mapper.writeValueAsString(this)
    }

}
