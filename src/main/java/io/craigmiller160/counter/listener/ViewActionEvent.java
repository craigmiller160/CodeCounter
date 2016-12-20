package io.craigmiller160.counter.listener;

/**
 * Created by craig on 12/17/16.
 */
public class ViewActionEvent extends ViewEvent {

    private String command;
    private Object data;

    public ViewActionEvent(Object source){
        this(source, null);
    }

    public ViewActionEvent(Object source, String command){
        this(source, command, null);
    }

    public ViewActionEvent(Object source, String command, Object data){
        super(source);
        this.command = command;
        this.data = data;
    }

    public void setData(Object data){
        this.data = data;
    }

    public Object getData(){
        return data;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
