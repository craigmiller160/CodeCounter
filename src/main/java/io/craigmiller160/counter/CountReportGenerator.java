package io.craigmiller160.counter;

import io.craigmiller160.counter.count.FileCountStorage;
import io.craigmiller160.counter.count.LineCountStorage;
import org.apache.commons.lang3.text.StrBuilder;

/**
 * Created by craig on 12/18/16.
 */
public class CountReportGenerator {

    public static String generateReport(String path, FileCountStorage fileCountStorage, LineCountStorage lineCountStorage){
        StrBuilder builder = new StrBuilder();
        builder.appendln("CODE COUNTING REPORT")
                .append("Project Path: ").appendln(path)
                .appendNewLine()
                .appendln("CODE FILES")
                .append("Total: ").appendln(fileCountStorage.getTotalFileCount())
                .append("Java: ").appendln(fileCountStorage.getJavaFileCount())
                .append("JavaScript: ").appendln(fileCountStorage.getJavaScriptFileCount())
                .append("XML: ").appendln(fileCountStorage.getXmlFileCount())
                .append("HTML: ").appendln(fileCountStorage.getHtmlFileCount())
                .append("CSS: ").appendln(fileCountStorage.getCssFileCount())
                .append("SQL: ").appendln(fileCountStorage.getSqlFileCount())
                .append("Bash: ").appendln(fileCountStorage.getBashFileCount())
                .append("Properties: ").appendln(fileCountStorage.getPropertiesFileCount())
                .append("Jars: ").appendln(fileCountStorage.getJarFileCount())
                .append("Other Files: ").appendln(fileCountStorage.getOtherFileCount())
                .appendNewLine()
                .appendln("LINES OF CODE")
                .append("Total: ").appendln(lineCountStorage.getTotalLines())
                .append("Java: ").appendln(lineCountStorage.getJavaLines())
                .append("JavaScript: ").append(lineCountStorage.getTotalJavaScriptLines())
                    .append(" (From JS Files: ").append(lineCountStorage.getJavaScriptLines())
                    .append(", From HTML Files: ").append(lineCountStorage.getJsHtmlLines()).appendln(")")
                .append("XML: ").appendln(lineCountStorage.getXmlLines())
                .append("HTML: ").appendln(lineCountStorage.getHtmlLines())
                .append("CSS: ").append(lineCountStorage.getTotalCssLines())
                    .append(" (From CSS Files: ").append(lineCountStorage.getCssLines())
                    .append(", From HTML Files: ").append(lineCountStorage.getCssHtmlLines()).appendln(")")
                .append("SQL: ").appendln(lineCountStorage.getSqlLines())
                .append("Bash: ").appendln(lineCountStorage.getBashLines())
                .append("Properties: ").appendln(lineCountStorage.getPropsLines());

        return builder.toString();
    }

}
