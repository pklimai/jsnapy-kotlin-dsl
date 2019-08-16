package pklimai.jsnapykotlindsl.lib

// Represents a single JSNAPy test
class JSNAPyTest(val name: String) {

    // Either command or rpc, not both
    var command: String? = null
    var rpc: String? = null
    // kwargs is only for rpc
    val kwargs = linkedMapOf<String, String>() // Use linked map to keep insertion order
    fun kwarg(key: String, value: String) {
        kwargs[key] = value
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
                kwargs.forEach { append("     ${it.key}: ${it.value}\n") }
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
