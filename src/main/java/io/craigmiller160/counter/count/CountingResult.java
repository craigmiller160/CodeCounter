package io.craigmiller160.counter.count;

/**
 * Created by craig on 12/17/16.
 */
public class CountingResult {

    private final FileCountStorage fileCountStorage;
    private final LineCountStorage lineCountStorage;

    public CountingResult(FileCountStorage fileCountStorage, LineCountStorage lineCountStorage){
        this.fileCountStorage = fileCountStorage;
        this.lineCountStorage = lineCountStorage;
    }

    public FileCountStorage getFileCountStorage() {
        return fileCountStorage;
    }

    public LineCountStorage getLineCountStorage() {
        return lineCountStorage;
    }
}
