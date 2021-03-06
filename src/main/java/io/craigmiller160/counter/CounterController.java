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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by craig on 12/16/16.
 */
public class CounterController implements ViewEventListener {

    public static final String PATH_PROP = "Path";
    public static final String EXECUTE_ACTION = "Execute";
    public static final String SAVE_ACTION = "Save";

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
            final CounterUI view = (CounterUI) event.getSource();
            final String pathString = model.getPath();
            if(StringUtils.isEmpty(pathString)){
                Counter.handleError("Cannot execute without path", null);
                return;
            }

            SwingWorker<CountingResult,Void> worker = new SwingWorker<CountingResult, Void>() {

                @Override
                protected CountingResult doInBackground() throws Exception {
                    System.out.println("Executing count operation");
                    Path path = Paths.get(pathString);
                    FileCounter fileCounter = FileCounter.createFileCounter(model);
                    System.out.println("Counting files...");
                    Files.walkFileTree(path, fileCounter);

                    FileCountStorage fileCountStorage = fileCounter.getFileCountStorage();

                    System.out.println("Counting lines...");
                    LineCountingProcessor lineCountingProcessor = new LineCountingProcessor(fileCountStorage, model.isIncludeComments());
                    lineCountingProcessor.execute();

                    LineCountStorage lineCountStorage = lineCountingProcessor.getLineCountStorage();

                    return new CountingResult(fileCountStorage, lineCountStorage);
                }

                @Override
                protected void done() {
                    try{
                        firePropertyChange("done", null, true);
                        System.out.println("Count operation finished");
                        CountingResult result = get();
                        String report = CountReportGenerator.generateReport(pathString, result.getFileCountStorage(), result.getLineCountStorage());
                        System.out.println(report);

                        CounterReportPanel reportPanel = new CounterReportPanel();
                        reportPanel.addListener(CounterController.this);
                        reportPanel.setReport(report);

                        JOptionPane.showMessageDialog(view.getWindow(), reportPanel.getPanel(), "Code Count Report", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch(InterruptedException ex){
                        Counter.handleError("Counting operation interrupted", ex);
                    }
                    catch(ExecutionException ex){
                        Counter.handleError("Error during counting operation", ex.getCause());
                    }
                }
            };

            worker.addPropertyChangeListener((e) -> {
                if("done".equals(e.getPropertyName())) {
                    view.stopProgressBar();
                }
            });

            view.startProgressBar();
            worker.execute();
        }
        else if(event.getCommand().equals(SAVE_ACTION)){
            Object[] data = (Object[]) event.getData();
            saveReport((File) data[0], (String) data[1]);
        }
    }

    private void handleChangeEvent(ViewChangeEvent event){
        if(event.getKey().equals(PATH_PROP)){
            model.setPath((String) event.getValue());
        }
        else if(event.getKey().equals(INCLUDE_COMMENT_PROP)){
            model.setIncludeComments((Boolean) event.getValue());
        }
    }

    private void saveReport(final File file, final String report){
        Thread t = new Thread(() -> {
            System.out.println("Saving report. File: " + file.getAbsolutePath());
            try(FileWriter writer = new FileWriter(file)){
                writer.write(report);
            }
            catch(IOException ex){
                Counter.handleError("Unable to save report. File: " + file.getAbsolutePath(), ex);
            }
        });
        t.start();
    }

}
