package io.craigmiller160.counter.count;

import io.craigmiller160.counter.CounterModel;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by craig on 12/17/16.
 */
public class FileCounter extends SimpleFileVisitor<Path> {

    private static final String JAVA_EXT = "java";
    private static final String JAVASCRIPT_EXT = "js";
    private static final String XML_EXT = "xml";
    private static final String HTML_EXT = "html";
    private static final String HTM_EXT = "htm";
    private static final String SQL_EXT = "sql";
    private static final String BASH_EXT = "sh";
    private static final String PROPS_EXT = "properties";
    private static final String JAR_EXT = "jar";
    private static final String CSS_EXT = "css";

    private final CounterModel model;
    private final FileCountStorage storage;

    public static FileCounter createFileCounter(CounterModel model){
        return new FileCounter(model);
    }

    private FileCounter(CounterModel model){
        this.model = model;
        this.storage = new FileCountStorage();
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        File file = path.toFile();
        String extension = FilenameUtils.getExtension(file.getName());
        if(model.isJava() && JAVA_EXT.equals(extension)){
            storage.addJavaFile(file);
        }
        else if(model.isJavaScript() && JAVASCRIPT_EXT.equals(extension)){
            storage.addJavaScriptFile(file);
        }
        else if(model.isXml() && XML_EXT.equals(extension)){
            storage.addXmlFile(file);
        }
        else if(model.isHtml() && (HTM_EXT.equals(extension) || HTML_EXT.equals(extension))){
            storage.addHtmlFile(file);
        }
        else if(model.isSql() && SQL_EXT.equals(extension)){
            storage.addSqlFile(file);
        }
        else if(model.isBash() && BASH_EXT.equals(extension)){
            storage.addBashFile(file);
        }
        else if(model.isProps() && PROPS_EXT.equals(extension)){
            storage.addPropertiesFile(file);
        }
        else if(model.isJars() && JAR_EXT.equals(extension)){
            storage.addJarFile(file);
        }
        else if(model.isCss() && CSS_EXT.equals(extension)){
            storage.addCssFile(file);
        }

        return FileVisitResult.CONTINUE;
    }

    public FileCountStorage getFileCountStorage(){
        return storage;
    }


}
