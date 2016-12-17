package io.craigmiller160.counter.listener;

/**
 * Created by craig on 12/17/16.
 */
public class ViewActionEvent extends ViewEvent {

    private String command;

    public ViewActionEvent(){
        this(null);
    }

    public ViewActionEvent(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
