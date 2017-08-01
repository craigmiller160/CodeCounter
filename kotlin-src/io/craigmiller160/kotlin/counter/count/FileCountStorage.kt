package io.craigmiller160.kotlin.counter.count

import java.io.File
import java.util.*

class FileCountStorage {

    val javaFiles = Collections.synchronizedList(ArrayList<File>())
    val javaScriptFiles = Collections.synchronizedList(ArrayList<File>())
    val xmlFiles = Collections.synchronizedList(ArrayList<File>())
    val htmlFiles = Collections.synchronizedList(ArrayList<File>())
    val sqlFiles = Collections.synchronizedList(ArrayList<File>())
    val propertiesFiles = Collections.synchronizedList(ArrayList<File>())
    val jarFiles = Collections.synchronizedList(ArrayList<File>())
    val cssFiles = Collections.synchronizedList(ArrayList<File>())
    val otherFiles = Collections.synchronizedList(ArrayList<File>())
    val bashFiles = Collections.synchronizedList(ArrayList<File>())

    fun getTotalFileCount(): Int{
        return javaFiles.size + javaScriptFiles.size +
                xmlFiles.size + htmlFiles.size +
                sqlFiles.size + bashFiles.size +
                propertiesFiles.size + jarFiles.size +
                cssFiles.size + otherFiles.size
    }

}