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
@YAMLElementMarker
class JSNAPyTest: ArrayList<JSNAPyTestListElements>() {
    init {
        add(ListElementRPCorCommand())
        add(ListElementItemORIterate())
    }

    var command: String? = null
        set(value) {
            (this[0] as ListElementRPCorCommand).command = value
        }

    var rpc: String? = null
        set(value) {
            (this[0] as ListElementRPCorCommand).rpc = value
        }

    fun kwarg(key: String, value: String) {
        (this[0] as ListElementRPCorCommand).kwargs[key] = value
    }

    fun item(block: JSNAPyItem.() -> Unit) {
        (this[1] as ListElementItemORIterate).item = JSNAPyItem().apply(block)
    }

    fun iterate(block: JSNAPyIterate.() -> Unit) {
        (this[1] as ListElementItemORIterate).iterate = JSNAPyIterate().apply(block)
    }

}
