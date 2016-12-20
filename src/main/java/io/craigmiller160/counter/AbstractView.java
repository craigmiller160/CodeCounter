package io.craigmiller160.counter;

import io.craigmiller160.counter.listener.ViewActionEvent;
import io.craigmiller160.counter.listener.ViewChangeEvent;
import io.craigmiller160.counter.listener.ViewEventListener;

import java.util.ArrayList;

/**
 * Created by craigmiller on 12/20/16.
 */
public class AbstractView {

    private final java.util.List<ViewEventListener> listeners = new ArrayList<>();

    public void addListener(ViewEventListener listener){
        this.listeners.add(listener);
    }

    public void removeListener(ViewEventListener listener){
        this.listeners.remove(listener);
    }

    protected void fireChangeEvent(String key, Object value){
        listeners.forEach((l) -> l.viewEvent(new ViewChangeEvent(this, key, value)));
    }

    protected void fireActionEvent(String command){
        fireActionEvent(command, null);
    }

    protected void fireActionEvent(String command, Object data){
        listeners.forEach((l) -> l.viewEvent(new ViewActionEvent(this, command, data)));
    }

}
