package io.craigmiller160.counter;

/**
 * Created by craig on 12/16/16.
 */
public class CounterModel {

    private String path;
    private boolean java;
    private boolean javaScript;
    private boolean xml;
    private boolean html;
    private boolean bash;
    private boolean sql;
    private boolean props;
    private boolean jars;
    private boolean includeComments;

    public synchronized String getPath() {
        return path;
    }

    public synchronized void setPath(String path) {
        this.path = path;
    }

    public synchronized boolean isJava() {
        return java;
    }

    public synchronized void setJava(boolean java) {
        this.java = java;
    }

    public synchronized boolean isJavaScript() {
        return javaScript;
    }

    public synchronized void setJavaScript(boolean javaScript) {
        this.javaScript = javaScript;
    }

    public synchronized boolean isXml() {
        return xml;
    }

    public synchronized void setXml(boolean xml) {
        this.xml = xml;
    }

    public synchronized boolean isHtml() {
        return html;
    }

    public synchronized void setHtml(boolean html) {
        this.html = html;
    }

    public synchronized boolean isBash() {
        return bash;
    }

    public synchronized void setBash(boolean bash) {
        this.bash = bash;
    }

    public synchronized boolean isSql() {
        return sql;
    }

    public synchronized void setSql(boolean sql) {
        this.sql = sql;
    }

    public synchronized boolean isProps() {
        return props;
    }

    public synchronized void setProps(boolean props) {
        this.props = props;
    }

    public synchronized boolean isJars() {
        return jars;
    }

    public synchronized void setJars(boolean jars) {
        this.jars = jars;
    }

    public synchronized boolean isIncludeComments() {
        return includeComments;
    }

    public synchronized void setIncludeComments(boolean includeComments) {
        this.includeComments = includeComments;
    }
}
