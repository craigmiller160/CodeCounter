package io.craigmiller160.counter.count;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static io.craigmiller160.counter.count.CommentConstants.*;

/**
 * Created by craig on 12/17/16.
 */
public class HTMLLineCountingTask extends LineCountingTask {

    private static final String STYLE_START_TAG = "<style";
    private static final String STYLE_END_TAG = "</style";
    private static final String SCRIPT_START_TAG = "<script";
    private static final String SCRIPT_END_TAG = "</script";

    private static final int HTML_LINE = 101;
    private static final int JS_LINE = 102;
    private static final int CSS_LINE = 103;

    private final CommentUtil markupCommentUtil;
    private final CommentUtil cStyleCommentUtil;

    private int lineType;
    private int htmlLineCount = 0;
    private int cssLineCount = 0;
    private int jsLineCount = 0;

    public HTMLLineCountingTask(File file, LineCountStorage storage, CountDownLatch latch, boolean includeComments) {
        super(file, storage, latch, includeComments);
        this.markupCommentUtil = new BlockCommentUtil.MarkupStyle();
        this.cStyleCommentUtil = new BlockCommentUtil.CStyle();
        this.lineType = HTML_LINE;
    }



    @Override
    public void run() {
        System.out.println("Counting lines in file. Type: HTML File: " + file.getAbsolutePath());
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = null;
            while((line = reader.readLine()) != null){
                if(!StringUtils.isEmpty(line.trim())){
                    if(lineType == HTML_LINE && (includeComments || markupCommentUtil.acceptLine(line))){
                        parseLine(line);
                    }
                    else if(lineType == CSS_LINE && (includeComments || cStyleCommentUtil.acceptLine(line))){
                        parseLine(line);
                    }
                    else if(lineType == JS_LINE && (includeComments || (!line.trim().startsWith(C_STYLE_SINGLE_COMMENT) && cStyleCommentUtil.acceptLine(line)))){
                        parseLine(line);
                    }
                }
            }

            storage.addHtmlLines(htmlLineCount);
            storage.addJsHtmlLines(jsLineCount);
            storage.addCssHtmlLines(cssLineCount);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        latch.countDown();
    }

    @SuppressWarnings("Duplicates")
    private void parseLine(String line){
        if(line.trim().startsWith(STYLE_START_TAG)){
            //If starting a CSS section, it still counts as an HTML line
            lineType = CSS_LINE;
            htmlLineCount++;
            //Check for content on this line after the start tag, which would be CSS content if it's before an end tag
            int styleStartTagIndex = line.indexOf(STYLE_START_TAG);
            int styleStartTagEndBracket = line.indexOf(">", styleStartTagIndex);
            if(line.substring(styleStartTagEndBracket + 1).trim().length() > 0){
                int styleEndTagIndex = line.indexOf(STYLE_END_TAG);
                if(styleEndTagIndex == -1){
                    cssLineCount++;
                }
                else if(line.substring(styleStartTagIndex + 1, styleEndTagIndex).trim().length() > 0){
                    cssLineCount++;
                    lineType = HTML_LINE;
                }
            }
        }
        else if(line.trim().contains(STYLE_START_TAG)){
            //If starting a CSS section, it still counts as an HTML line
            lineType = CSS_LINE;
            htmlLineCount++;
            //Check for content on this line after the start tag, which would be CSS content if it's before an end tag
            int styleStartTagIndex = line.indexOf(STYLE_START_TAG);
            int styleStartTagEndBracket = line.indexOf(">", styleStartTagIndex);
            if(line.substring(styleStartTagEndBracket + 1).trim().length() > 0){
                int styleEndTagIndex = line.indexOf(STYLE_END_TAG);
                if(styleEndTagIndex == -1){
                    cssLineCount++;
                }
                else if(line.substring(styleStartTagIndex + 1, styleEndTagIndex).trim().length() > 0){
                    cssLineCount++;
                    lineType = HTML_LINE;
                }
            }
        }
        else if(line.trim().contains(STYLE_END_TAG)){
            //If ending a CSS section, it still counts as an HTML line
            lineType = HTML_LINE;
            htmlLineCount++;
            //Check for content before the end tag to determine if there is CSS content on this line
            int styleEndTagIndex = line.indexOf(STYLE_END_TAG);
            if(line.substring(0, styleEndTagIndex).trim().length() > 0){
                cssLineCount++;
            }
        }
        else if(line.trim().startsWith(SCRIPT_START_TAG)){
            //If starting a Script section, it still counts as an HTML line
            lineType = JS_LINE;
            htmlLineCount++;
            //Check for content on this line after the start tag, which would be JS content if it's before an end tag
            int scriptStartTagIndex = line.indexOf(SCRIPT_START_TAG);
            int scriptStartTagEndBracket = line.indexOf(">", scriptStartTagIndex);
            if(line.substring(scriptStartTagEndBracket + 1).trim().length() > 0){
                int scriptEndTagIndex = line.indexOf(SCRIPT_END_TAG);
                if(scriptEndTagIndex == -1){
                    jsLineCount++;
                }
                else if(line.substring(scriptStartTagIndex + 1, scriptEndTagIndex).trim().length() > 0){
                    jsLineCount++;
                    lineType = HTML_LINE;
                }
            }
        }
        else if(line.trim().contains(SCRIPT_START_TAG)){
            //If starting a JS section, it still counts as an HTML line
            lineType = JS_LINE;
            htmlLineCount++;
            //Check for content on this line after the start tag, which would be JS content if it's before an end tag
            int scriptStartTagIndex = line.indexOf(SCRIPT_START_TAG);
            int scriptStartTagEndBracket = line.indexOf(">", scriptStartTagIndex);
            if(line.substring(scriptStartTagEndBracket + 1).trim().length() > 0){
                int scriptEndTagIndex = line.indexOf(SCRIPT_END_TAG);
                if(scriptEndTagIndex == -1){
                    jsLineCount++;
                }
                else if(line.substring(scriptStartTagIndex + 1, scriptEndTagIndex).trim().length() > 0){
                    jsLineCount++;
                    lineType = HTML_LINE;
                }
            }
        }
        else if(line.trim().contains(SCRIPT_END_TAG)){
            //If ending a JS section, it still counts as an HTML line
            lineType = HTML_LINE;
            htmlLineCount++;
            //Check for content before the end tag to determine if there is JS content on this line
            int scriptEndTagIndex = line.indexOf(SCRIPT_END_TAG);
            if(line.substring(0, scriptEndTagIndex).trim().length() > 0){
                jsLineCount++;
            }
        }
        else{
            switch(lineType){
                case HTML_LINE:
                    htmlLineCount++;
                    break;
                case JS_LINE:
                    jsLineCount++;
                    break;
                case CSS_LINE:
                    cssLineCount++;
                    break;
            }
        }
    }
}
