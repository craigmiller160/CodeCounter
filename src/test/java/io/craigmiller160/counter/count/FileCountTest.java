package io.craigmiller160.counter.count;

import io.craigmiller160.counter.CounterModel;
import io.craigmiller160.counter.TestConstants;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by craig on 12/17/16.
 */
public class FileCountTest {

    @Test
    public void testCount() throws Exception{
        String pathString = TestConstants.TEST_SRC_ROOT + "/io/craigmiller160/counter/samples";
        CounterModel model = new CounterModel();
        model.setJava(true);
        model.setJavaScript(true);
        model.setXml(true);
        model.setHtml(true);
        model.setBash(true);
        model.setSql(true);
        model.setProps(true);
        model.setJars(true);
        model.setCss(true);

        Path path = Paths.get(pathString);
        FileCounter fileCounter  = FileCounter.createFileCounter(model);
        Files.walkFileTree(path, fileCounter);

        FileCountStorage storage = fileCounter.getFileCountStorage();

        File f = new File(pathString);
        int actualSize = f.listFiles().length;

        assertEquals("Wrong actual file count for samples directory", 9, actualSize);
        assertEquals("Wrong total file count", 9, storage.getTotalFileCount());
        assertEquals("Wrong java file count", 1, storage.getJavaFileCount());
        assertEquals("Wrong javascript file count", 1, storage.getJavaScriptFileCount());
        assertEquals("Wrong xml file count", 1, storage.getXmlFileCount());
        assertEquals("Wrong html file count", 1, storage.getHtmlFileCount());
        assertEquals("Wrong bash file count", 1, storage.getBashFileCount());
        assertEquals("Wrong sql file count", 1, storage.getSqlFileCount());
        assertEquals("Wrong props file count", 1, storage.getPropertiesFileCount());
        assertEquals("Wrong jars file count", 1, storage.getJarFileCount());
        assertEquals("Wrong css file count", 1, storage.getCssFileCount());
    }

}
