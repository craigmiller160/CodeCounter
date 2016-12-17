package io.craigmiller160.counter.count;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by craig on 12/17/16.
 */
public abstract class LineCountingTask implements Runnable {

    private final File file;
    protected final LineCountStorage storage;
    private final CountDownLatch latch;
    private final boolean includeComments;

    protected LineCountingTask(File file, LineCountStorage storage, CountDownLatch latch, boolean includeComments){
        this.file = file;
        this.storage = storage;
        this.latch = latch;
        this.includeComments = includeComments;
    }

    @Override
    public void run() {
        int lineCount = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = null;
            while((line = reader.readLine()) != null){
                if(!StringUtils.isEmpty(line.trim()) && acceptLine(line)){
                    lineCount++;
                }
            }

            addToStorage(lineCount);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        latch.countDown();
    }

    public boolean isIncludeComments(){
        return includeComments;
    }

    protected abstract boolean acceptLine(String line);

    protected abstract void addToStorage(int lineCount);
}
