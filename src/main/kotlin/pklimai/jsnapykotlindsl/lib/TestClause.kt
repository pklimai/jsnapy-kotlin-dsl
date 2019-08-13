package pklimai.jsnapykotlindsl.lib

class TestClause {
    var testop: String? = null
    var values: String? = null
    var info: String? = null
    var err: String? = null

    override fun toString() = buildString {
        append("       - $testop: $values\n")
        append("         err: $err\n")
        append("         info: $info\n")
    }

}
