package io.craigmiller160.counter.count;

import static io.craigmiller160.counter.count.CommentConstants.*;

/**
 * A utility class to parse lines for the C-Style
 * block comment, denoted by /*.
 *
 * Created by craig on 12/17/16.
 */
public class CStyleBlockCommentUtil {

    private boolean inBlockComment;

    public boolean acceptLine(String line){
        if(line.trim().startsWith(C_STYLE_START_BLOCK_COMMENT)){
            //If block comment, first test to see if the comment ends within the current line
            int commentStartIndex = line.indexOf(C_STYLE_START_BLOCK_COMMENT);
            int commentEndIndex = line.indexOf(C_STYLE_END_BLOCK_COMMENT, commentStartIndex);
            if(commentEndIndex == -1){
                //If it does not, the block comment will continue to the next line, and the line is rejected
                inBlockComment = true;
                return false;
            }
            else if(line.substring(commentEndIndex + 2).trim().length() > 0){
                //If there is content after the end of the comment, accept the line
                return true;
            }
            //Fallback to rejecting the line
            return false;
        }
        else if(line.trim().contains(C_STYLE_START_BLOCK_COMMENT)){
            //If the line contains a block comment start marker, but not at the beginning, it still gets counted, but inBlockComment needs to be determined
            int commentStartIndex = line.indexOf(C_STYLE_START_BLOCK_COMMENT);
            int commentEndIndex = line.indexOf(C_STYLE_END_BLOCK_COMMENT, commentStartIndex);
            if(commentEndIndex == -1){
                inBlockComment = true;
            }

            return true;
        }
        else if(line.trim().contains(C_STYLE_END_BLOCK_COMMENT)){
            //If the line contains a block comment end marker, test to see if there is content after it. If there is, the line should be accepted, otherwise rejected
            inBlockComment = false;
            int commentEndIndex = line.indexOf(C_STYLE_END_BLOCK_COMMENT);
            if(line.substring(commentEndIndex + 2).trim().length() > 0){
                return true;
            }
            return false;
        }
        else{
            return !inBlockComment;
        }
    }
}
