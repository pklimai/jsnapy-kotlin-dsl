package pklimai.jsnapykotlindsl.lib

class JSNAPyIterate {
    lateinit var xpath: String
    var id: String? = null
    val tests = mutableListOf<TestClause>()

    fun testClause(block: TestClause.() -> Unit) {
        tests.add(TestClause().apply(block))
    }
}