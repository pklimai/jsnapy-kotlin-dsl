package pklimai.jsnapykotlindsl.lib

class ListOfTestsInclude: ArrayList<String>() {
    operator fun String.unaryPlus() {
        this@ListOfTestsInclude.add(this)
    }
}