package pklimai.jsnapykotlindsl.lib

import java.lang.StringBuilder

// Represents a file with JSNAPy tests
class JSNAPyTestFile {
    // Test definitions
    private val tests = mutableListOf<JSNAPyTest>()

    fun jsnapyTest(block: JSNAPyTest.() -> Unit) {
        tests.add(JSNAPyTest().apply(block))
    }

    // Optional list of test names to execute - by default, all tests will be executed
    private val testsInclude = ListOfTestsInclude()

    fun testsInclude(block: ListOfTestsInclude.() -> Unit) {
        testsInclude.apply(block)
    }

    override fun toString() = StringBuilder().apply {
        tests.forEach{ append(it) }
        if (testsInclude.isNotEmpty()) {
            append("tests_include:\n")
            testsInclude.forEach { append(" - $it\n") }
        }
    }.toString()
}
