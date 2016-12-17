package io.craigmiller160.counter.count;

import io.craigmiller160.counter.TestConstants;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

/**
 * Created by craig on 12/17/16.
 */
public class TestXMLCount {

    @Test
    public void testCount(){
        String path = TestConstants.TEST_SRC_ROOT + "/io/craigmiller160/counter/samples/XMLTest.xml";
        LineCountStorage noCommentsStorage = new LineCountStorage();
        LineCountStorage withCommentsStorage = new LineCountStorage();
        XMLLineCountingTask noCommentsTask = new XMLLineCountingTask(new File(path), noCommentsStorage, new CountDownLatch(1), false);
        XMLLineCountingTask withCommentsTask = new XMLLineCountingTask(new File(path), withCommentsStorage, new CountDownLatch(1), true);

        noCommentsTask.run();
        withCommentsTask.run();

        assertEquals("Incorrect number of lines for test with comments", 8, withCommentsStorage.getXmlLines());
        assertEquals("Incorrect number of lines for test without comments", 5, noCommentsStorage.getXmlLines());
    }

}
