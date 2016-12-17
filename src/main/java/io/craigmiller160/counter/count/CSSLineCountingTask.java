package io.craigmiller160.counter.count;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * Created by craig on 12/17/16.
 */
public class CSSLineCountingTask extends DefaultLineCountingTask {

    private final CommentUtil commentUtil;

    public CSSLineCountingTask(File file, LineCountStorage storage, CountDownLatch latch, boolean includeComments) {
        super(file, storage, latch, includeComments);
        this.commentUtil = new BlockCommentUtil.CStyle();
    }

    @Override
    protected boolean acceptLine(String line) {
        if(isIncludeComments()){
            return true;
        }

        return commentUtil.acceptLine(line);
    }

    @Override
    protected void addToStorage(int lineCount) {
        storage.addCssLines(lineCount);
    }
}
