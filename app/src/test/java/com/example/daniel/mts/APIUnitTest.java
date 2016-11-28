package com.example.daniel.mts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import com.example.daniel.mts.RemoteFetch;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class APIUnitTest {
    @Mock
    private RemoteFetch rf = new RemoteFetch();

    @Test
    public void lineAmountisCorrect() throws Exception {
        Line[] lines = rf.getListOfAllLines();
        if (lines == null) {
            System.out.println("Hi");
        }
        System.out.println("Got list of lines");
        assertEquals("Number of lines should be this", 45,lines.length);
    }
}