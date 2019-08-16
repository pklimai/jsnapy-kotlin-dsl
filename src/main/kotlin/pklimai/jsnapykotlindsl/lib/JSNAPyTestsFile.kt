package pklimai.jsnapykotlindsl.lib

// Represents a file with JSNAPy tests
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

    override fun toString() = buildString {
        tests.forEach{ append(it) }
        if (testsInclude.isNotEmpty()) {
            append("tests_include:\n")
            testsInclude.forEach { append(" - $it\n") }
        }
    }
}
