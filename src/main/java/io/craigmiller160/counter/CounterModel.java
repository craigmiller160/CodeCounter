package io.craigmiller160.counter;

/**
 * Created by craig on 12/16/16.
 */
public class CounterModel {

    private String path;
    private boolean includeComments;

    public synchronized String getPath() {
        return path;
    }

    public synchronized void setPath(String path) {
        this.path = path;
    }

    public synchronized boolean isIncludeComments() {
        return includeComments;
    }

    public synchronized void setIncludeComments(boolean includeComments) {
        this.includeComments = includeComments;
    }
}
