package io.craigmiller160.counter.count;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static io.craigmiller160.counter.count.CommentConstants.*;

/**
 * Created by craig on 12/17/16.
 */
public class SQLLineCountingTask extends LineCountingTask {

    private final CommentUtil commentUtil;

    public SQLLineCountingTask(File file, LineCountStorage storage, CountDownLatch latch, boolean includeComments) {
        super(file, storage, latch, includeComments);
        this.commentUtil = new BlockCommentUtil.CStyle();
    }

    @Override
    protected boolean acceptLine(String line) {
        if(isIncludeComments()){
            return true;
        }

        if(line.trim().startsWith(POUND_SINGLE_COMMENT)){
            return false;
        }

        return commentUtil.acceptLine(line);
    }

    @Override
    protected void addToStorage(int lineCount) {
        storage.addSqlLines(lineCount);
    }
}
