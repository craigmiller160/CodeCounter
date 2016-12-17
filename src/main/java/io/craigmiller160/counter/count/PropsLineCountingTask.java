package io.craigmiller160.counter.count;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static io.craigmiller160.counter.count.CommentConstants.*;

/**
 * Created by craig on 12/17/16.
 */
public class PropsLineCountingTask extends LineCountingTask {

    public PropsLineCountingTask(File file, LineCountStorage storage, CountDownLatch latch, boolean includeComments) {
        super(file, storage, latch, includeComments);
    }

    @Override
    protected boolean acceptLine(String line) {
        if(isIncludeComments()){
            return true;
        }

        if(line.trim().startsWith(POUND_SINGLE_COMMENT)){
            return false;
        }

        return true;
    }

    @Override
    protected void addToStorage(int lineCount) {
        storage.addPropsLines(lineCount);
    }
}
