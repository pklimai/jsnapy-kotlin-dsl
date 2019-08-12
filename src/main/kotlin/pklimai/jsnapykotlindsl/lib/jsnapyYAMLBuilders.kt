package pklimai.jsnapykotlindsl.lib

import kotlin.system.exitProcess

fun terminate(message: String) {
    println("Error: $message")
    exitProcess(1)
}

fun createJSNAPyTestFile(block: JSNAPyTestFile.() -> Unit) = JSNAPyTestFile().apply(block)

fun JSNAPyTestFile.jsnapyTest(block: JSNAPyTest.() -> Unit) {
    tests.add(JSNAPyTest().apply(block))
}

fun JSNAPyTestFile.testsInclude(block: ListOfTestsInclude.() -> Unit) {
    testsInclude.apply(block)
}

fun JSNAPyTest.item(block: JSNAPyItem.() -> Unit) {
    item = JSNAPyItem().apply(block)
}

fun JSNAPyTest.kwarg(key: String, value: String) {
    kwargs.add(Pair(key, value))
}

fun JSNAPyItem.testClause(block: TestClause.() -> Unit) {
    tests.add(TestClause().apply(block))
}
