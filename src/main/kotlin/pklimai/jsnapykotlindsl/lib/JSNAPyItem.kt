package pklimai.jsnapykotlindsl.lib

@YAMLElementMarker
class JSNAPyItem {
    var xpath: String? = null
    var id: String? = null
    val tests = mutableListOf<TestClause>()

    fun testClause(block: TestClause.() -> Unit) {
        tests.add(TestClause().apply(block))
    }

}
