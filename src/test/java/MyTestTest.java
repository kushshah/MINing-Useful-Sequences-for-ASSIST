import com.google.common.collect.Sets;
import junit.framework.TestCase;

import java.util.*;

/**
 * Created by Kush on 3/1/14.
 */
public class MyTestTest extends TestCase {
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {

    }

    public void testGetTempList() throws Exception {

        String folderName = "C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\";
        MyTest test = new MyTest(folderName);
        String[] stArr = "0,1,2".split(",");
        ArrayList<String> mthd = new ArrayList(Arrays.asList("0", "1", "2","0","0","1","2","4","5","6","1","2"));
        ArrayList<String> ctr = new ArrayList(Arrays.asList("0", "1", "2","3","4","5","6","7","8","9","10","11"));
        List<HashSet<String>> res = test.getTempList(stArr,mthd, ctr);
        for(HashSet<String> set: res){
            System.out.println(Arrays.toString(set.toArray()));
        }

    }

    public void testGetOccurrences() throws Exception {

    }
    public void testGetOccurrence() throws Exception {
        String folderName = "C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\";
        MyTest test = new MyTest(folderName);
        String[] stArr = "0,1,2".split(",");
        ArrayList<String> mthd = new ArrayList(Arrays.asList("0", "1", "2","0","0","1","2","4","5","6","1","2"));
        ArrayList<String> ctr = new ArrayList(Arrays.asList("0", "1", "2","3","4","5","6","7","8","9","10","11"));
        ArrayList<List<String>> res = test.getOccurence(stArr, mthd, ctr);
        for(List<String> list: res){
            System.out.println(Arrays.toString(list.toArray()));
        }
    }
    public void testIsIncreasing() throws Exception {
        String folderName = "C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\";
        MyTest test = new MyTest(folderName);
    }
}
