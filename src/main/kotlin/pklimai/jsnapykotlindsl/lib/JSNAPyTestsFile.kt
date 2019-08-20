package pklimai.jsnapykotlindsl.lib

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.KotlinModule

// Represents a file with JSNAPy tests
@YAMLElementMarker
class JSNAPyTestsFile: LinkedHashMap<String, JSNAPyTest>() {

    fun jsnapyTest(name: String, block: JSNAPyTest.() -> Unit) {
        this[name]=JSNAPyTest().apply(block)
    }

    // Optional list of test names to execute - by default, all tests will be executed
    val testsInclude = ListOfTestsInclude()

    fun testsInclude(block: ListOfTestsInclude.() -> Unit) {
        testsInclude.apply(block)
    }

    fun toYAML(): String {
        val mapper = ObjectMapper(
            YAMLFactory()
            .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
        )
        // See e.g. https://www.baeldung.com/jackson-ignore-null-fields
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        // This is to avoid empty collections like kwargs: []
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT)
        mapper.registerModule(KotlinModule())
        var result = mapper.writeValueAsString(this)
        // TODO("Dirty hack for now:")
        if (testsInclude.isNotEmpty()) result += "tests_include:\n" + mapper.writeValueAsString(this.testsInclude)
        return result
    }

}
