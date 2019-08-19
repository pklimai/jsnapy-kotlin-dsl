package pklimai.jsnapykotlindsl.lib

@YAMLElementMarker
class ListOfTestsInclude: ArrayList<String>() {
    operator fun String.unaryPlus() {
        this@ListOfTestsInclude.add(this)
    }
}