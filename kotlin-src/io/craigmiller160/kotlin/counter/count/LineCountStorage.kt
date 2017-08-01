package io.craigmiller160.kotlin.counter.count

class LineCountStorage {

    var javaLines: Int = 0
    var htmlLines: Int = 0
    var xmlLines: Int = 0
    var javaScriptLines: Int = 0
    var sqlLines: Int = 0
    var bashLines: Int = 0
    var propsLines: Int = 0
    var cssLines: Int = 0
    var jsHtmlLines: Int = 0
    var cssHtmlLines: Int = 0

    fun getTotalLines(): Int{
        return javaLines + javaScriptLines + xmlLines + htmlLines +
                sqlLines + bashLines + propsLines + cssLines +
                jsHtmlLines + cssHtmlLines
    }

}