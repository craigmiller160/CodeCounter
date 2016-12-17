package io.craigmiller160.counter.count;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by craig on 12/17/16.
 */
public class LineCountingProcessor {

    private static final int THREAD_COUNT = 20;

    private final ExecutorService countingExecutor;
    private final FileCountStorage fileCountStorage;
    private final LineCountStorage lineCountStorage;
    private final CountDownLatch latch;
    private final boolean includeComments;

    public LineCountingProcessor(FileCountStorage fileCountStorage, boolean includeComments){
        this.countingExecutor = Executors.newFixedThreadPool(THREAD_COUNT);
        this.fileCountStorage = fileCountStorage;
        this.lineCountStorage = new LineCountStorage();
        this.includeComments = includeComments;
        this.latch = new CountDownLatch(fileCountStorage.getTotalFileCount());
    }

    public void execute(){
        fileCountStorage.getJavaFiles().forEach((f) -> countingExecutor.submit(new JavaLineCountingTask(f, lineCountStorage, latch, includeComments)));
        fileCountStorage.getJavaScriptFiles().forEach((f) -> countingExecutor.submit(new JavaScriptLineCountingTask(f, lineCountStorage, latch, includeComments)));
        fileCountStorage.getXmlFiles().forEach((f) -> countingExecutor.submit(new XMLLineCountingTask(f, lineCountStorage, latch, includeComments)));
        fileCountStorage.getHtmlFiles().forEach((f) -> countingExecutor.submit(new HTMLLineCountingTask(f, lineCountStorage, latch, includeComments)));
        fileCountStorage.getSqlFiles().forEach((f) -> countingExecutor.submit(new SQLLineCountingTask(f, lineCountStorage, latch, includeComments)));
        fileCountStorage.getBashFiles().forEach((f) -> countingExecutor.submit(new BashLineCountingTask(f, lineCountStorage, latch, includeComments)));
        fileCountStorage.getPropertiesFiles().forEach((f) -> countingExecutor.submit(new PropsLineCountingTask(f, lineCountStorage, latch, includeComments)));
        fileCountStorage.getCssFiles().forEach((f) -> countingExecutor.submit(new CSSLineCountingTask(f, lineCountStorage, latch, includeComments)));
        //Just count down the latch for each jar file, since there aren't any lines to parse
        fileCountStorage.getJavaFiles().forEach((f) -> latch.countDown());

        try{
            latch.await();
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public LineCountStorage getLineCountStorage(){
        return lineCountStorage;
    }

    public boolean isIncludeComments(){
        return includeComments;
    }

}
