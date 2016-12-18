package io.craigmiller160.counter.count;

import io.craigmiller160.counter.TestConstants;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

/**
 * Created by craig on 12/18/16.
 */
public class TestHtmlCssJsCounting {

    @Test
    public void testCount(){
        String path = TestConstants.TEST_SRC_ROOT + "/io/craigmiller160/counter/samples/HTMLCssJsTest.html";
        LineCountStorage noCommentsStorage = new LineCountStorage();
        LineCountStorage withCommentsStorage = new LineCountStorage();
        HTMLLineCountingTask noCommentsTask = new HTMLLineCountingTask(new File(path), noCommentsStorage, new CountDownLatch(1), false);
        HTMLLineCountingTask withCommentsTask = new HTMLLineCountingTask(new File(path), withCommentsStorage, new CountDownLatch(1), true);

        noCommentsTask.run();
        withCommentsTask.run();

        assertEquals("Incorrect number of HTML lines for test with comments", 15, withCommentsStorage.getHtmlLines());
        assertEquals("Incorrect number of CSS lines for test with comments", 7, withCommentsStorage.getCssHtmlLines());
        assertEquals("Incorrect number of JS lines for test with comments", 9, withCommentsStorage.getJsHtmlLines());

        assertEquals("Incorrect number of HTML lines for test without comments", 13, noCommentsStorage.getHtmlLines());
        assertEquals("Incorrect number of CSS lines for test without comments", 6, noCommentsStorage.getCssHtmlLines());
        assertEquals("Incorrect number of JS lines for test without comments", 5, noCommentsStorage.getJsHtmlLines());
    }

}
