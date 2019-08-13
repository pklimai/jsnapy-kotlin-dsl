package pklimai.jsnapykotlindsl.lib

import kotlin.system.exitProcess

fun terminate(message: String) {
    println("Error: $message")
    exitProcess(1)
}

fun createJSNAPyTestFile(block: JSNAPyTestFile.() -> Unit) = JSNAPyTestFile().apply(block)
