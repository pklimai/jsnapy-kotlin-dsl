package pklimai.jsnapykotlindsl.lib

import java.lang.StringBuilder

// Represents a single JSNAPy test
class JSNAPyTest {
    lateinit var name: String

    // Either command or rpc, not both
    var command: String? = null
    var rpc: String? = null
    // kwargs is only for rpc
    val kwargs = mutableListOf<Pair<String, String>>()
    fun kwarg(key: String, value: String) {
        kwargs.add(Pair(key, value))
    }

    var item: JSNAPyItem? = null
    fun item(block: JSNAPyItem.() -> Unit) {
        item = JSNAPyItem().apply(block)
    }

    var iterate: JSNAPyIterate? = null
    fun iterate(block: JSNAPyIterate.() -> Unit) {
        iterate = JSNAPyIterate().apply(block)
    }

    override fun toString() = StringBuilder().apply {
        append("$name:\n")
        if (rpc != null && command == null) {
            append(" - rpc: $rpc\n")
            if (kwargs.isNotEmpty()) {
                append("   kwargs:\n")
                kwargs.forEach { append("     ${it.first}: ${it.second}\n") }
            }
        }
        else if (rpc == null && command != null) {
            append(" - command: $command\n")
            if (kwargs.isNotEmpty()) terminate("'kwargs' is only for 'rpc'")
        }
        else terminate("'rpc' XOR 'command' must be present (not both)")

        if (item != null && iterate == null) {
            append(" - item:\n")
            with(item!!) {
                if (xpath != null) append("     xpath: $xpath\n")
                if (id != null) append("     id: $id\n")
                if (tests.isNotEmpty()) {
                    append("     tests:\n")
                    tests.forEach {
                        append("       - ${it.testop}: ${it.values}\n")
                        append("         err: ${it.err}\n")
                        append("         info: ${it.info}\n")
                    }
                }
            }
        } else if (item == null && iterate != null) {
            append(" - iterate:\n")
            with(iterate!!) {
                if (xpath != null) append("     xpath: $xpath\n")
                if (id != null) append("     id: $id\n")
                if (tests.isNotEmpty()) {
                    append("     tests:\n")
                    tests.forEach {
                        append("       - ${it.testop}: ${it.values}\n")
                        append("         err: ${it.err}\n")
                        append("         info: ${it.info}\n")
                    }
                }
            }
        }
    }.toString()
}
