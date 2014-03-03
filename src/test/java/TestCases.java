/**
 * Created by Kush on 2/17/14.
 */
import org.json.simple.JSONArray;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;
public class TestCases {
    @Test
    public void testPasses() {
        String expected = "Hello, JUnit!";
        String hello = "Hello, JUnit!";
        assertEquals(hello, expected);
    }
    @Test
    public void testFails() {
        // The worlds most obvious bug:
        assertTrue(true);
    }

    @Test
    public void checkTest(){
//        String st = "C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\";
//        MyTest test = new MyTest(st);
//        LinkedList<String> l1 = new LinkedList<String>();
//        LinkedList<String> l2 = new LinkedList<String>();
//        l1.add("1");
//        l1.add("2");
//        l1.add("3");
//        l2.add("0");
//        l2.add("1");
//        l2.add("2");
//        JSONArray arr = test.getOccurence(new String[]{"1", "2", "3"}, l1,l2);
//        JSONArray expArr = new JSONArray();
//        //expArr.add([]);
//        //assertEquals(arr,);
    }
}
