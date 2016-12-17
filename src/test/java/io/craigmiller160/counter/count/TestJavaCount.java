package io.craigmiller160.counter.count;

import io.craigmiller160.counter.TestConstants;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

/**
 * Created by craig on 12/17/16.
 */
public class TestJavaCount {

    @Test
    public void testJavaCount(){
        String path = TestConstants.TEST_SRC_ROOT + "/io/craigmiller160/counter/samples/JavaTest.java";
        LineCountStorage noCommentsStorage = new LineCountStorage();
        LineCountStorage withCommentsStorage = new LineCountStorage();
        JavaLineCountingTask noCommentsTask = new JavaLineCountingTask(new File(path), noCommentsStorage, new CountDownLatch(1), false);
        JavaLineCountingTask withCommentsTask = new JavaLineCountingTask(new File(path), withCommentsStorage, new CountDownLatch(1), true);

        noCommentsTask.run();
        withCommentsTask.run();

        assertEquals("Incorrect number of lines for test with comments", 10, withCommentsStorage.getJavaLines());
        assertEquals("Incorrect number of lines for test without comments", 6, noCommentsStorage.getJavaLines());
    }

}
