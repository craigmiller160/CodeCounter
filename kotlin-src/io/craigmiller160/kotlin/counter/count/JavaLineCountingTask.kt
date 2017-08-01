package io.craigmiller160.kotlin.counter.count

import java.io.File
import java.util.concurrent.CountDownLatch

class JavaLineCountingTask(file: File, storage: LineCountStorage, latch: CountDownLatch, includeComments: Boolean) :
        DefaultLineCountingTask(file, storage, latch, includeComments){

    override fun acceptLine(line: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val fileType = "Java"

    override fun addToStorage(lineCount: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}