package io.craigmiller160.counter.count;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static io.craigmiller160.counter.count.CommentConstants.*;

/**
 * Created by craig on 12/17/16.
 */
public class JavaScriptLineCountingTask extends LineCountingTask {

    private final CStyleBlockCommentUtil blockCommentUtil;

    public JavaScriptLineCountingTask(File file, LineCountStorage storage, CountDownLatch latch, boolean includeComments) {
        super(file, storage, latch, includeComments);
        this.blockCommentUtil = new CStyleBlockCommentUtil();
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
        return blockCommentUtil.acceptLine(line);
    }

    @Override
    protected void addToStorage(int lineCount) {
        storage.addJavaScriptLines(lineCount);
    }
}
