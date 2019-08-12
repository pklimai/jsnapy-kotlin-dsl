package pklimai.jsnapykotlindsl.lib

import java.lang.StringBuilder

// Represents a file with JSNAPy tests
class JSNAPyTestFile {
    // Test definitions
    val tests = mutableListOf<JSNAPyTest>()

    // Optional list of test names to execute - by default, all tests will be executed
    val testsInclude = ListOfTestsInclude()

    override fun toString() = StringBuilder().apply {
        tests.forEach{ append(it) }
        if (testsInclude.isNotEmpty()) {
            append("tests_include:\n")
            testsInclude.forEach { append(" - $it\n") }
        }
    }.toString()
}
