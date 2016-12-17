package io.craigmiller160.counter.count;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static io.craigmiller160.counter.count.CommentConstants.*;

/**
 * Created by craig on 12/17/16.
 */
public class JavaScriptLineCountingTask extends DefaultLineCountingTask {

    private final CommentUtil commentUtil;

    public JavaScriptLineCountingTask(File file, LineCountStorage storage, CountDownLatch latch, boolean includeComments) {
        super(file, storage, latch, includeComments);
        this.commentUtil = new BlockCommentUtil.CStyle();
    }

    @Override
    protected boolean acceptLine(String line) {
        if(isIncludeComments()){
            return true;
        }

        //Logic to locate comments
        if(line.trim().startsWith(C_STYLE_SINGLE_COMMENT)){
            //If single line comment, reject the line
            return false;
        }

        //If the line is not a part of a block comment, it will be accepted
        return commentUtil.acceptLine(line);
    }

    @Override
    protected void addToStorage(int lineCount) {
        storage.addJavaScriptLines(lineCount);
    }
}
