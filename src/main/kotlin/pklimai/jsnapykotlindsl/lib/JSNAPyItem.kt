package pklimai.jsnapykotlindsl.lib

class JSNAPyItem {
    var xpath: String? = null
    var id: String? = null
    val tests = mutableListOf<TestClause>()

    fun testClause(block: TestClause.() -> Unit) {
        tests.add(TestClause().apply(block))
    }

//    override fun toString() = buildString {
//        append(" - item:\n")
//        if (xpath != null) append("     xpath: $xpath\n")
//        if (id != null) append("     id: $id\n")
//        if (tests.isNotEmpty()) {
//            append("     tests:\n")
//            tests.forEach { append(it.toString()) }
//        }
//    }

}
