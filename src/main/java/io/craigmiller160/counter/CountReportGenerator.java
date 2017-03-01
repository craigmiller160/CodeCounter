package io.craigmiller160.counter;

import io.craigmiller160.counter.count.FileCountStorage;
import io.craigmiller160.counter.count.LineCountStorage;
import org.apache.commons.lang3.text.StrBuilder;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by craig on 12/18/16.
 */
public class CountReportGenerator {

    private static final NumberFormat format = new DecimalFormat("###,###,###,###");

    public static String generateReport(String path, FileCountStorage fileCountStorage, LineCountStorage lineCountStorage){
        StrBuilder builder = new StrBuilder();
        builder.appendln("CODE COUNTING REPORT")
                .append("Project Path: ").appendln(path)
                .appendNewLine()
                .appendln("CODE FILES")
                .append("Total: ").appendln(format.format(fileCountStorage.getTotalFileCount()))
                .append("Java: ").appendln(format.format(fileCountStorage.getJavaFileCount()))
                .append("JavaScript: ").appendln(format.format(fileCountStorage.getJavaScriptFileCount()))
                .append("XML: ").appendln(format.format(fileCountStorage.getXmlFileCount()))
                .append("HTML: ").appendln(format.format(fileCountStorage.getHtmlFileCount()))
                .append("CSS: ").appendln(format.format(fileCountStorage.getCssFileCount()))
                .append("SQL: ").appendln(format.format(fileCountStorage.getSqlFileCount()))
                .append("Bash: ").appendln(format.format(fileCountStorage.getBashFileCount()))
                .append("Properties: ").appendln(format.format(fileCountStorage.getPropertiesFileCount()))
                .append("Jars: ").appendln(format.format(fileCountStorage.getJarFileCount()))
                .append("Other Files: ").appendln(format.format(fileCountStorage.getOtherFileCount()))
                .appendNewLine()
                .appendln("LINES OF CODE")
                .append("Total: ").appendln(format.format(lineCountStorage.getTotalLines()))
                .append("Java: ").appendln(format.format(lineCountStorage.getJavaLines()))
                .append("JavaScript: ").append(format.format(lineCountStorage.getTotalJavaScriptLines()))
                    .append(" (From JS Files: ").append(format.format(lineCountStorage.getJavaScriptLines()))
                    .append(", From HTML Files: ").append(format.format(lineCountStorage.getJsHtmlLines())).appendln(")")
                .append("XML: ").appendln(format.format(lineCountStorage.getXmlLines()))
                .append("HTML: ").appendln(format.format(lineCountStorage.getHtmlLines()))
                .append("CSS: ").append(format.format(lineCountStorage.getTotalCssLines()))
                    .append(" (From CSS Files: ").append(format.format(lineCountStorage.getCssLines()))
                    .append(", From HTML Files: ").append(format.format(lineCountStorage.getCssHtmlLines())).appendln(")")
                .append("SQL: ").appendln(format.format(lineCountStorage.getSqlLines()))
                .append("Bash: ").appendln(format.format(lineCountStorage.getBashLines()))
                .append("Properties: ").appendln(format.format(lineCountStorage.getPropsLines()));

        return builder.toString();
    }

}
