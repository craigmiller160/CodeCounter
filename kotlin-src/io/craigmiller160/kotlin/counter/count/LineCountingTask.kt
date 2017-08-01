package io.craigmiller160.kotlin.counter.count

import java.io.File
import java.util.concurrent.CountDownLatch

abstract class LineCountingTask(val file: File, val storage: LineCountStorage, val latch: CountDownLatch, val includeComments: Boolean) : Runnable

abstract class DefaultLineCountingTask(file: File, storage: LineCountStorage, latch: CountDownLatch, includeComments: Boolean) : LineCountingTask(file, storage, latch, includeComments){

    //TODO any way to make this an abstract property???
    abstract fun getFileType(): String

    override fun run() {
        println("Counting lines in file. Type: ${getFileType()} File: ${file.absolutePath}")

        var lineCount = 0
        file.bufferedReader().useLines { lines ->
            lines.forEach { if(!it.trim().isEmpty() && acceptLine(it)) lineCount++ }
        }

        addToStorage(lineCount)

        latch.countDown()
    }

    //TODO also, acceptLine() should be a function too
    abstract fun acceptLine(line: String): Boolean

    //TODO instead of addToStorage() abstract method, use a passed-in function. Might even need less params here
    abstract fun addToStorage(lineCount: Int)

}