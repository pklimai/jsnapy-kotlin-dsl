package pklimai.jsnapykotlindsl.lib

@YAMLElementMarker
class TestClause: LinkedHashMap<String, String>() {

    fun condition(op: String, vals: String) {
        // TODO check operators
        this[op] = vals
    }

    fun info(s: String) {
        this["info"] = s
    }
    var info: String? = null
        set(value) {info(value ?: "")}

    fun err(s: String) {
        this["err"] = s
    }
    var err: String? = null
       set(value) {err(value ?: "")}
}
