package io.craigmiller160.counter.count;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static io.craigmiller160.counter.count.CommentConstants.*;

/**
 * Created by craig on 12/17/16.
 */
public class JavaLineCountingTask extends DefaultLineCountingTask {

    private final CommentUtil commentUtil;

    /*
     * Block comment rules:
     *
     * 1) Starts with /*
     * 2) Ends with \*\/
     * 3) Can be in middle of line
     *    a) If so, count the line if it has non-commented parts, but still start the flag.
     * 4) Can end on the same line
     *    a) Always test for the end command if detecting the start comment.
     * 5) If in the middle of a comment block, no further lines can be accepted.
     * 6) Lines that start with // are always comments, but just for that single line
     */

    public JavaLineCountingTask(File file, LineCountStorage storage, CountDownLatch latch, boolean includeComments) {
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
        storage.addJavaLines(lineCount);
    }
}
