/**
 * Created by Kush on 2/11/14.
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class JSONTest {

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        System.out.println("=======decode=======");
        try {
            //Scanner sc = new Snew FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTraceJSON.log"));

            String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
            Object obj= JSONValue.parse(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTraceJSON.log"));
            JSONArray array=(JSONArray)obj;
            System.out.println("======the 2nd element of array======");
            System.out.println(array.get(1).toString());
            System.out.println();

//            JSONObject obj2=(JSONObject)array.get(1);
//            System.out.println("======field \"1\"==========");
//            System.out.println(obj2.get("1"));


            s="{}";
            obj=JSONValue.parse(s);
            System.out.println(obj);

            s="[5,]";
            obj=JSONValue.parse(s);
            System.out.println(obj);

            s="[5,,2]";
            obj=JSONValue.parse(s);
            System.out.println(obj);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        try {
//            Scanner sc = new Scanner(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTraceJSON.log"));
//            while (sc.hasNextLine()){
//                String line = sc.nextLine();
//                String jsonText = "{\"first\": 123, \"second\": [4, 5, 6], \"third\": 789}";
//                JSONParser parser = new JSONParser();
//                ContainerFactory containerFactory = new ContainerFactory(){
//                    public List creatArrayContainer() {
//                        return new LinkedList();
//                    }
//
//                    public Map createObjectContainer() {
//                        return new LinkedHashMap();
//                    }
//
//                };
//
//                try{
//                    Map json = (Map)parser.parse(line, containerFactory);
//                    Iterator iter = json.entrySet().iterator();
//                    System.out.println("==iterate result==");
//                    System.out.println(json.get("counter"));
//                    //System.out.println(json.get("second"));
//                    while(iter.hasNext()){
//                        Map.Entry entry = (Map.Entry)iter.next();
//                        System.out.println(entry.getKey() + "=>" + entry.getValue());
//                    }
//
//                    System.out.println("==toJSONString()==");
//                    System.out.println(JSONValue.toJSONString(json));
//                } catch (org.json.simple.parser.ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


        try {

            Object myObj = parser.parse(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTraceJSON.log"));

            JSONArray jsonObject = (JSONArray) myObj;
            Map m = new HashMap();
            Iterator iterator = jsonObject.iterator();
            System.out.println("xkjsoxn");
            System.out.println(jsonObject.get(0));
            while (iterator.hasNext()){
                System.out.println(iterator.next());
            }

//            long age = (Long) jsonObject.get("age");
//            System.out.println(age);
//
//            // loop array
//            JSONArray msg = (JSONArray) jsonObject.get("messages");
//            Iterator<String> iterator = msg.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}
