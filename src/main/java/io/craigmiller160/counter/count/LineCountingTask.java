package io.craigmiller160.counter.count;


import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * Created by craig on 12/17/16.
 */
public abstract class LineCountingTask implements Runnable {

    protected final File file;
    protected final LineCountStorage storage;
    protected final CountDownLatch latch;
    protected final boolean includeComments;

    protected LineCountingTask(File file, LineCountStorage storage, CountDownLatch latch, boolean includeComments){
        this.file = file;
        this.storage = storage;
        this.latch = latch;
        this.includeComments = includeComments;
    }


}
