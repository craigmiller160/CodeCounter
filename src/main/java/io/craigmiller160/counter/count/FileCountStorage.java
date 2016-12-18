package io.craigmiller160.counter.count;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by craig on 12/17/16.
 */
public class FileCountStorage {

    private final List<File> javaFiles = new ArrayList<>();
    private final List<File> javaScriptFiles = new ArrayList<>();
    private final List<File> xmlFiles = new ArrayList<>();
    private final List<File> htmlFiles = new ArrayList<>();
    private final List<File> sqlFiles = new ArrayList<>();
    private final List<File> bashFiles = new ArrayList<>();
    private final List<File> propertiesFiles = new ArrayList<>();
    private final List<File> jarFiles = new ArrayList<>();
    private final List<File> cssFiles = new ArrayList<>();
    private final List<File> otherFiles = new ArrayList<>();

    public synchronized int getJavaFileCount(){
        return javaFiles.size();
    }

    public synchronized int getJavaScriptFileCount(){
        return javaScriptFiles.size();
    }

    public synchronized int getXmlFileCount(){
        return xmlFiles.size();
    }

    public synchronized int getHtmlFileCount(){
        return htmlFiles.size();
    }

    public synchronized int getSqlFileCount(){
        return sqlFiles.size();
    }

    public synchronized int getBashFileCount(){
        return bashFiles.size();
    }

    public synchronized int getPropertiesFileCount(){
        return propertiesFiles.size();
    }

    public synchronized int getJarFileCount(){
        return jarFiles.size();
    }

    public synchronized int getCssFileCount(){
        return cssFiles.size();
    }

    public synchronized int getOtherFileCount(){
        return otherFiles.size();
    }

    public synchronized int getTotalFileCount(){
        return javaFiles.size() + javaScriptFiles.size() +
                xmlFiles.size() + htmlFiles.size() +
                sqlFiles.size() + bashFiles.size() +
                propertiesFiles.size() + jarFiles.size() +
                cssFiles.size() + otherFiles.size();
    }

    public synchronized List<File> getJavaFiles() {
        return javaFiles;
    }

    public synchronized List<File> getJavaScriptFiles() {
        return javaScriptFiles;
    }

    public synchronized List<File> getXmlFiles() {
        return xmlFiles;
    }

    public synchronized List<File> getHtmlFiles() {
        return htmlFiles;
    }

    public synchronized List<File> getSqlFiles() {
        return sqlFiles;
    }

    public synchronized List<File> getBashFiles() {
        return bashFiles;
    }

    public synchronized List<File> getPropertiesFiles() {
        return propertiesFiles;
    }

    public synchronized List<File> getJarFiles() {
        return jarFiles;
    }

    public synchronized void addJavaFile(File file){
        javaFiles.add(file);
    }

    public synchronized void addJavaScriptFile(File file){
        javaScriptFiles.add(file);
    }

    public synchronized void addXmlFile(File file){
        xmlFiles.add(file);
    }

    public synchronized void addHtmlFile(File file){
        htmlFiles.add(file);
    }

    public synchronized void addSqlFile(File file){
        sqlFiles.add(file);
    }

    public synchronized void addBashFile(File file){
        bashFiles.add(file);
    }

    public synchronized void addJarFile(File file){
        jarFiles.add(file);
    }

    public synchronized void addPropertiesFile(File file){
        propertiesFiles.add(file);
    }

    public List<File> getCssFiles() {
        return cssFiles;
    }

    public synchronized void addCssFile(File file){
        cssFiles.add(file);
    }

    public synchronized void addOtherFile(File file){
        otherFiles.add(file);
    }

    public synchronized List<File> getOtherFiles(){
        return otherFiles;
    }
}
