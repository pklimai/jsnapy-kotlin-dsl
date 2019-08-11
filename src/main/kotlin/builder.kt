import java.lang.StringBuilder
import kotlin.system.exitProcess

fun terminate(message: String) {
    println("Error: $message")
    exitProcess(1)
}

// Represents a file with JSNAPy tests
class JSNAPyTestFile {
    lateinit var tests: List<JSNAPyTest>
    var testsInclude: List<String>? = null

    override fun toString() = StringBuilder().apply {
        tests.forEach{ append(it) }
        if (testsInclude != null) {
            append("tests_include:\n")
            testsInclude!!.forEach { append(" - $it") }
        }
    }.toString()

}

// Represents a single JSNAPy test
class JSNAPyTest {
    lateinit var name: String

    // Either command or rpc, not both
    var command: String? = null
    var rpc: String? = null
    // kwargs is only for rpc
    var kwargs: List<Pair<String, String>>? = null

    //var item: String? = null
    //var iterate: String? = null


    override fun toString() = StringBuilder().apply {
        append("$name:\n")
        if (rpc != null && command == null) {
            append(" - rpc: $rpc\n")
            if (kwargs != null) {
                append("   kwargs:\n")
                kwargs!!.forEach { append("     ${it.first}: ${it.second}\n") }
            }
        }
        else if (rpc == null && command != null) {
            append(" - command: $command\n")
            if (kwargs != null) terminate("'kwargs' is only for 'rpc'")
        }
        else terminate("'rpc' XOR 'command' must be present (not both)")
    }.toString()

}

fun buildTest(t_builder: JSNAPyTest.() -> Unit) =  JSNAPyTest().apply(t_builder)

fun buildTestFile(tf_builder: JSNAPyTestFile.() -> Unit) = JSNAPyTestFile().apply(tf_builder)


fun main() {
    val myTestFile = buildTestFile {
        tests = listOf(
            buildTest {
                name = "First test"
                command = "show interfaces"
            },
            buildTest {
                name = "Second test"
                rpc = "get-interface-information"
                kwargs = listOf(Pair("terse", "True"), Pair("name", "ge-0/0/0"))
            }
        )
        testsInclude = listOf("First test")
    }
    print(myTestFile)
}

