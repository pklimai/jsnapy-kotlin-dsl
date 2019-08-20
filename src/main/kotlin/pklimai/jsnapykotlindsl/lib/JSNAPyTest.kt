package pklimai.jsnapykotlindsl.lib

open class JSNAPyTestListElements()

class ListElementRPCorCommand: JSNAPyTestListElements() {
    // Either command or rpc, not both
    var command: String? = null
    var rpc: String? = null
    // kwargs is only for rpc
    val kwargs = linkedMapOf<String, String>() // Use linked map to keep insertion order
}

class ListElementItemORIterate: JSNAPyTestListElements() {
    var item: JSNAPyItem? = null
    var iterate: JSNAPyIterate? = null
}

// Represents a single JSNAPy test
// This class is a bit crazy and you are welcome to rewrite it...
@YAMLElementMarker
class JSNAPyTest: ArrayList<JSNAPyTestListElements>() {

    private var commandORrpcAdded: Boolean = false

    var command: String? = null
        set(value) {
            if (!commandORrpcAdded) {
                add(ListElementRPCorCommand())
                commandORrpcAdded = true
            }
            else {
                terminate("command or rpc already added")
            }
            (this[0] as ListElementRPCorCommand).command = value
        }

    var rpc: String? = null
        set(value) {
            if (commandORrpcAdded) {
                terminate("command or rpc already added")
            }
            add(ListElementRPCorCommand())
            commandORrpcAdded = true
            (this[0] as ListElementRPCorCommand).rpc = value
        }

    fun kwarg(key: String, value: String) {
        (this[0] as ListElementRPCorCommand).kwargs[key] = value
    }

    fun item(block: JSNAPyItem.() -> Unit) {
        if (commandORrpcAdded) {
            add(ListElementItemORIterate())
            (this[1] as ListElementItemORIterate).item = JSNAPyItem().apply(block)
        }
    }

    fun iterate(block: JSNAPyIterate.() -> Unit) {
        if (commandORrpcAdded) {
            add(ListElementItemORIterate())
            (this[1] as ListElementItemORIterate).iterate = JSNAPyIterate().apply(block)
        }
    }

}
