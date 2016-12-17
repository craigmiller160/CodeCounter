package io.craigmiller160.counter.listener;

/**
 * Created by craig on 12/17/16.
 */
public class ViewChangeEvent extends ViewEvent{

    private String key;
    private Object value;

    public ViewChangeEvent(){
        this(null, null);
    }

    public ViewChangeEvent(String key, Object value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
