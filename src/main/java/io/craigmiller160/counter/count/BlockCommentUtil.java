package io.craigmiller160.counter.count;

import static io.craigmiller160.counter.count.CommentConstants.C_STYLE_END_BLOCK_COMMENT;
import static io.craigmiller160.counter.count.CommentConstants.C_STYLE_START_BLOCK_COMMENT;
import static io.craigmiller160.counter.count.CommentConstants.MARKUP_COMMENT_END;
import static io.craigmiller160.counter.count.CommentConstants.MARKUP_COMMENT_START;

/**
 * Created by craig on 12/17/16.
 */
public abstract class BlockCommentUtil implements CommentUtil{

    private boolean inBlockComment;

    private BlockCommentUtil(){}

    protected abstract String getCommentStart();

    protected abstract String getCommentEnd();

    protected abstract int getCommentStartLength();

    protected abstract int getCommentEndLength();

    @Override
    public boolean acceptLine(String line) {
        if(line.trim().startsWith(getCommentStart())){
            //If block comment, first test to see if the comment ends within the current line
            int commentStartIndex = line.indexOf(getCommentStart());
            int commentEndIndex = line.indexOf(getCommentEnd(), commentStartIndex + getCommentStartLength());
            if(commentEndIndex == -1){
                //If it does not, the block comment will continue to the next line, and the line is rejected
                inBlockComment = true;
                return false;
            }
            else if(line.substring(commentEndIndex + getCommentEndLength()).trim().length() > 0){
                //If there is content after the end of the comment, accept the line
                return true;
            }
            //Fallback to rejecting the line
            return false;
        }
        else if(line.trim().contains(getCommentStart())){
            //If the line contains a block comment start marker, but not at the beginning, it still gets counted, but inBlockComment needs to be determined
            int commentStartIndex = line.indexOf(getCommentStart());
            int commentEndIndex = line.indexOf(getCommentEnd(), commentStartIndex + getCommentStartLength());
            if(commentEndIndex == -1){
                inBlockComment = true;
            }

            return true;
        }
        else if(line.trim().contains(getCommentEnd())){
            //If the line contains a block comment end marker, test to see if there is content after it. If there is, the line should be accepted, otherwise rejected
            inBlockComment = false;
            int commentEndIndex = line.indexOf(getCommentEnd());
            if(line.substring(commentEndIndex + getCommentEndLength()).trim().length() > 0){
                return true;
            }
            return false;
        }
        else{
            return !inBlockComment;
        }
    }

    public static class MarkupStyle extends BlockCommentUtil{

        private boolean inBlockComment;

        @Override
        protected String getCommentStart() {
            return MARKUP_COMMENT_START;
        }

        @Override
        protected String getCommentEnd() {
            return MARKUP_COMMENT_END;
        }

        @Override
        protected int getCommentStartLength() {
            return 4;
        }

        @Override
        protected int getCommentEndLength() {
            return 3;
        }
    }

    public static class CStyle extends BlockCommentUtil{

        private boolean inBlockComment;

        @Override
        protected String getCommentStart() {
            return C_STYLE_START_BLOCK_COMMENT;
        }

        @Override
        protected String getCommentEnd() {
            return C_STYLE_END_BLOCK_COMMENT;
        }

        @Override
        protected int getCommentStartLength() {
            return 2;
        }

        @Override
        protected int getCommentEndLength() {
            return 2;
        }
    }

}
