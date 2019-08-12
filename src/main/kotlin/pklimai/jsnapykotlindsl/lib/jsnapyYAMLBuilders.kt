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

fun JSNAPyTestFile.testInclude(name: String) {
    testsInclude.add(name)
}

fun JSNAPyTest.item(block: JSNAPyItem.() -> Unit) {
    item = JSNAPyItem().apply(block)
}

fun JSNAPyItem.testClause(block: TestClause.() -> Unit) {
    tests.add(TestClause().apply(block))
}


