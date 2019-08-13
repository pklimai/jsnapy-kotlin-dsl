package pklimai.jsnapykotlindsl.lib

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

    override fun toString() = buildString {
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
            append(item.toString())
        }
        else if (item == null && iterate != null) {
            append(iterate.toString())
        }
        else if (item != null && iterate != null) {
            terminate("'item' OR 'iterate' must be present (but not both)")
        }
    }
}
