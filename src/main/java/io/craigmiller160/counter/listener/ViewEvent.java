package io.craigmiller160.counter.listener;

/**
 * Created by craig on 12/16/16.
 */
public abstract class ViewEvent {

    private final Object source;

    public ViewEvent(Object source){
        this.source = source;
    }

    public Object getSource(){
        return source;
    }

}
