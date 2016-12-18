package io.craigmiller160.counter;

import io.craigmiller160.counter.count.CountingResult;
import io.craigmiller160.counter.count.FileCountStorage;
import io.craigmiller160.counter.count.FileCounter;
import io.craigmiller160.counter.count.LineCountStorage;
import io.craigmiller160.counter.count.LineCountingProcessor;
import io.craigmiller160.counter.listener.ViewActionEvent;
import io.craigmiller160.counter.listener.ViewChangeEvent;
import io.craigmiller160.counter.listener.ViewEvent;
import io.craigmiller160.counter.listener.ViewEventListener;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by craig on 12/16/16.
 */
public class CounterController implements ViewEventListener {

    public static final String PATH_PROP = "Path";
    public static final String JAVA_PROP = "Java";
    public static final String JAVA_SCRIPT_PROP = "JavaScript";
    public static final String XML_PROP = "Xml";
    public static final String HTML_PROP = "Html";
    public static final String BASH_PROP = "Bash";
    public static final String SQL_PROP = "SQL";
    public static final String PROPS_PROP = "Properties";
    public static final String JARS_PROP = "Jars";
    public static final String CSS_PROP = "CSS";
    public static final String EXECUTE_ACTION = "Execute";

    public static final String INCLUDE_COMMENT_PROP = "IncludeComment";

    private final CounterModel model;

    public CounterController(CounterModel model){
        this.model = model;
    }

    @Override
    public void viewEvent(ViewEvent event) {
        if(event instanceof ViewChangeEvent){
            handleChangeEvent((ViewChangeEvent) event);
        }
        else if(event instanceof ViewActionEvent){
            handleActionEvent((ViewActionEvent) event);
        }
    }

    private void handleActionEvent(ViewActionEvent event){
        if(event.getCommand().equals(EXECUTE_ACTION)){
            final String pathString = model.getPath();
            if(StringUtils.isEmpty(pathString)){
                Counter.handleError("Cannot execute without path", null);
                return;
            }

            SwingWorker<CountingResult,Void> worker = new SwingWorker<CountingResult, Void>() {

                @Override
                protected CountingResult doInBackground() throws Exception {
                    try{
                        System.out.println("Executing count operation");
                        Path path = Paths.get(pathString);
                        FileCounter fileCounter = FileCounter.createFileCounter(model);
                        System.out.println("Counting files...");
                        Files.walkFileTree(path, fileCounter);

                        FileCountStorage fileCountStorage = fileCounter.getFileCountStorage();
                        System.out.println("TOTAL FILES: " + fileCountStorage.getTotalFileCount());

                        System.out.println("Counting lines...");
                        LineCountingProcessor lineCountingProcessor = new LineCountingProcessor(fileCountStorage, model.isIncludeComments());
                        lineCountingProcessor.execute();

                        LineCountStorage lineCountStorage = lineCountingProcessor.getLineCountStorage();
                        System.out.println("TOTAL LINES: " + lineCountStorage.getTotalLines());

                        return new CountingResult(fileCountStorage, lineCountStorage);
                    }
                    catch(IOException ex){
                        //TODO need to handle the event... let it propagate to the done() method???
                        ex.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void done() {
                    super.done();
                }
            };

            //TODO include a progress dialog
            worker.execute();



        }
    }

    private void handleChangeEvent(ViewChangeEvent event){
        if(event.getKey().equals(PATH_PROP)){
            model.setPath((String) event.getValue());
        }
        else if(event.getKey().equals(JAVA_PROP)){
            model.setJava((Boolean) event.getValue());
        }
        else if(event.getKey().equals(JAVA_SCRIPT_PROP)){
            model.setJavaScript((Boolean) event.getValue());
        }
        else if(event.getKey().equals(XML_PROP)){
            model.setXml((Boolean) event.getValue());
        }
        else if(event.getKey().equals(HTML_PROP)){
            model.setHtml((Boolean) event.getValue());
        }
        else if(event.getKey().equals(BASH_PROP)){
            model.setBash((Boolean) event.getValue());
        }
        else if(event.getKey().equals(SQL_PROP)){
            model.setSql((Boolean) event.getValue());
        }
        else if(event.getKey().equals(CSS_PROP)){
            model.setCss((Boolean) event.getValue());
        }
        else if(event.getKey().equals(INCLUDE_COMMENT_PROP)){
            model.setIncludeComments((Boolean) event.getValue());
        }
    }

}
