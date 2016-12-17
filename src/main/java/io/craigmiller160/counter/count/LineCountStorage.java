package io.craigmiller160.counter.count;

/**
 * Created by craig on 12/17/16.
 */
public class LineCountStorage {

    private int javaLines;
    private int htmlLines;
    private int xmlLines;
    private int javaScriptLines;
    private int sqlLines;
    private int bashLines;
    private int propsLines;
    private int cssLines;

    public synchronized int getJavaLines() {
        return javaLines;
    }

    public synchronized void addJavaLines(int javaLines) {
        this.javaLines+= javaLines;
    }

    public synchronized int getHtmlLines() {
        return htmlLines;
    }

    public synchronized void addHtmlLines(int htmlLines) {
        this.htmlLines+= htmlLines;
    }

    public synchronized int getXmlLines() {
        return xmlLines;
    }

    public synchronized void addXmlLines(int xmlLines) {
        this.xmlLines+= xmlLines;
    }

    public synchronized int getJavaScriptLines() {
        return javaScriptLines;
    }

    public synchronized void addJavaScriptLines(int javaScriptLines) {
        this.javaScriptLines+= javaScriptLines;
    }

    public synchronized int getSqlLines() {
        return sqlLines;
    }

    public synchronized void addSqlLines(int sqlLines) {
        this.sqlLines+= sqlLines;
    }

    public synchronized int getBashLines() {
        return bashLines;
    }

    public synchronized void addBashLines(int bashLines) {
        this.bashLines+= bashLines;
    }

    public synchronized int getPropsLines() {
        return propsLines;
    }

    public synchronized void addPropsLines(int propsLines) {
        this.propsLines+= propsLines;
    }

    public synchronized void addCssLines(int cssLines){
        this.cssLines+= cssLines;
    }

    public synchronized int getCssLines(){
        return cssLines;
    }

    public synchronized int getTotalLines(){
        return javaLines + javaScriptLines + xmlLines + htmlLines +
                sqlLines + bashLines + propsLines + cssLines;
    }
}
