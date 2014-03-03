import com.google.common.collect.Sets;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.tools.jar.resources.jar;

import java.io.*;
import java.util.*;

/**
 * Created by Kush on 2/3/14.
 */
public class MyTest {
    String folderName;
    String bideFolder;
    Object obj;
    JSONArray array;
    Iterator iterator;
    JSONParser parser;
    ContainerFactory containerFactory;
    HashMap<String, Integer > methodHashMap;
    BufferedWriter bufferedWriter;
    int seqCount;
    int maxSeq;
    float seqTotal;

//    public MyTest(String folderName, String bideFolder) {
//        createMethodMapAndSequenceMap(folderName, bideFolder);
//        runBIDE(bideFolder);
//        formatBIDEOP();
//    }



    public MyTest(String folderName) {
        this.folderName = folderName;
        this.bideFolder = "C:\\Users\\Kush\\Documents\\CS598\\BIDE\\";
        this.containerFactory = new ContainerFactory(){
            public List creatArrayContainer() {
                return new ArrayList();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

        };
        this.methodHashMap = new HashMap<String, Integer>();
        this.parser = new JSONParser();
        seqCount = 0;
        maxSeq = 0;
        seqTotal = 0;

    }
    public void generateOP(){
        createMethodMap();
        createSequenceMap();
        createBIDEIP();
        createBIDESpec();
        runBIDE(this.bideFolder);
        formatBIDEOP(this.folderName, this.bideFolder);
    }

    private void createBIDESpec() {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(bideFolder + "BideIP1.spec"));
            bufferedWriter.write(bideFolder + "BideIP1.txt");
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(methodHashMap.size()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(seqCount));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(maxSeq));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(Math.round(seqTotal/seqCount)));
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createBIDEIP() {
        try {
            boolean flag = false;
            int seqCount = 0;
            JSONArray seq = new JSONArray();
            bufferedWriter = new BufferedWriter(new FileWriter(bideFolder + "BideIP.txt"));
            obj = JSONValue.parse(new FileReader(this.folderName + "MethodTraceJSON.log"));
            array=(JSONArray)obj;
            iterator = array.iterator();
            String leastLevel = ((Map)parser.parse(array.get(0).toString(), containerFactory)).get("level").toString();
            while (iterator.hasNext()){
                String line = iterator.next().toString();
                Map lineMap = (Map)parser.parse(line, containerFactory);
                if(lineMap.get("level").toString().equals(leastLevel) && flag ){
                    seqCount = seqCount + 1;
                    for(String each : seq.toJSONString().substring(1,seq.toJSONString().length()-1).split(",")){
                        bufferedWriter.write(each + " ");
                    }
                    if(seq.size() > this.maxSeq){
                        this.maxSeq = seq.size();
                    }
                    this.seqTotal = this.seqTotal + seq.size();
                    this.seqCount = this.seqCount + 1;
                    bufferedWriter.write("-1");
                    bufferedWriter.newLine();
                    seq.removeAll(seq);
                }
                seq.add(methodHashMap.get(lineMap.get("class-name").toString()+lineMap.get("method-name").toString()));
                flag = true;
            }
            this.seqCount = this.seqCount + 1;
            for(String each : seq.toJSONString().substring(1,seq.toJSONString().length()-1).split(",")){
                bufferedWriter.write(each + " ");
            }
            bufferedWriter.write("-1 -2");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void createSequenceMap() {

        try {
            int count = 0;
            int seqCount = 0;
            JSONArray seqMapArray = new JSONArray();
            JSONArray seq = new JSONArray();
            JSONArray counterSeq = new JSONArray();
            bufferedWriter = new BufferedWriter(new FileWriter(folderName + "SequenceMapJSON.log"));
            obj = JSONValue.parse(new FileReader(this.folderName + "MethodTraceJSON.log"));
            array=(JSONArray)obj;
            iterator = array.iterator();
            String leastLevel = ((Map)parser.parse(array.get(0).toString(), containerFactory)).get("level").toString();
            while (iterator.hasNext()){
                String line = iterator.next().toString();
                Map lineMap = (Map)parser.parse(line, containerFactory);
                if(lineMap.get("level").toString().equals(leastLevel) && count != 0 ){
                    JSONObject sequence = new JSONObject();
                    sequence.put("sequence-id", seqCount);
                    seqCount = seqCount + 1;
                    sequence.put("method-id", seq.clone());
                    sequence.put("counters", counterSeq.clone());
                    seqMapArray.add(sequence);
                    seq.removeAll(seq);
                    counterSeq.removeAll(counterSeq);
                }
                seq.add(methodHashMap.get(lineMap.get("class-name").toString()+lineMap.get("method-name").toString()));
                counterSeq.add(lineMap.get("counter"));
                count = count + 1;
            }
            JSONObject sequence = new JSONObject();
            sequence.put("sequence-id", seqCount);
            seqCount = seqCount + 1;
            sequence.put("method-id", seq.clone());
            sequence.put("counters", counterSeq.clone());
            seqMapArray.add(sequence);
            bufferedWriter.write(seqMapArray.toJSONString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void createMethodMap() {
        int methodCount = 0;
        JSONArray methodMapArray = new JSONArray();
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(folderName + "MethodMapJSON1.log"));
            obj = JSONValue.parse(new FileReader(this.folderName + "MethodTraceJSON.log"));
            array=(JSONArray)obj;
            iterator = array.iterator();
            while (iterator.hasNext()){
                String line = iterator.next().toString();
                Map lineMap = (Map)parser.parse(line, containerFactory);

                if(ismethodNotPresent(lineMap)){
                    JSONObject method = new JSONObject();
                    method.put("method-id",methodCount);
                    method.put("class-name",lineMap.get("class-name"));
                    method.put("method-name", lineMap.get("method-name"));
                    methodMapArray.add(method);
                    this.methodHashMap.put(lineMap.get("class-name").toString()+lineMap.get("method-name").toString(),methodCount);
                    methodCount = methodCount + 1;
                }
            }
            bufferedWriter.write(methodMapArray.toJSONString());
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean ismethodNotPresent( Map line) {
        return !(this.methodHashMap.containsKey(line.get("class-name").toString()+line.get("method-name").toString()));
    }
    public static void main(String[] args) {
        String folderName = "C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\"; /*OR = args[1]*/
        String bideFolder = "C:\\Users\\Kush\\Documents\\CS598\\BIDE\\";
//       Test test = new Test(folderName, bideFolder);
        MyTest test = new MyTest(folderName);
        test.generateOP();


       }

    private void formatBIDEOP(String folderName, String bideFolder) {
        try {
            BufferedWriter bideOP = new BufferedWriter(new FileWriter(folderName + "BIDEOP.log"));
            Scanner sc = new Scanner(new FileReader(folderName+"frequent.dat"));
            JSONArray mainArray = new JSONArray();

            while (sc.hasNextLine()){
                JSONObject  lineObject = new JSONObject();
                String bideOPLine = sc.nextLine();
//                System.out.println(bideOPLine);
                String [] seqArray = bideOPLine.split(":")[0].split(" ");
                JSONArray seqJSONArray = new JSONArray();
                JSONArray occurencesJSONArray = new JSONArray();
                for(String each : seqArray){
                    seqJSONArray.add(Integer.parseInt(each));
                }
                lineObject.put("method-sequence", seqJSONArray);
                occurencesJSONArray = getOccurrences(seqArray);
                //getOccurrences(seqArray);
                lineObject.put("occurences", occurencesJSONArray);
                System.out.println(lineObject.toJSONString());
//                Iterator iterator = occurencesJSONArray.iterator();
//                while(iterator.hasNext()){
//                    System.out.println(iterator.next());
//
//                }
                mainArray.add(lineObject);
            }
            bideOP.write(mainArray.toJSONString());
            bideOP.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONArray getOccurrences(String[] seqArray) {
        JSONArray mainArray = new JSONArray();
        try {
            Object obj= JSONValue.parse(new FileReader(folderName + "SequenceMapJSON.log"));
            JSONArray array=(JSONArray)obj;
            Iterator iterator = array.iterator();
            JSONObject occurencesObject = new JSONObject();
            while (iterator.hasNext()){
                String line = iterator.next().toString();
                Map lineMap = (Map)parser.parse(line, containerFactory);
//                System.out.println(line);
//                System.out.println(lineMap.get("counters"));
                ArrayList<String> counterList = (ArrayList)parser.parse(lineMap.get("counters").toString(),containerFactory);
                HashSet<String> ctrTemp = new HashSet<String>(counterList);
                HashSet<String> ctrSet = new HashSet<String>();
                for(Object s: ctrTemp){
                    ctrSet.add(s.toString());
                }
                ArrayList<String> methodList = (ArrayList<String>)parser.parse(lineMap.get("method-id").toString(),containerFactory);
                HashSet<String> mthdTemp = new HashSet<String>(methodList);
                HashSet<String> methodSet = new HashSet<String>();
                for(Object s: mthdTemp){
                    methodSet.add(s.toString());
                }
                //String [] mthdArray = methodList.toArray(new String[methodList.size()]);
                ArrayList<List<String>> occurence = getOccurence(seqArray,methodList, counterList);

                JSONArray counterArray = new JSONArray();
                for(List<String> lstr : occurence){
                    for(Object str: lstr){
                        counterArray.add(str);
                    }
                    occurencesObject.put("counters",counterArray.clone());
                    occurencesObject.put("sequence-no",lineMap.get("sequence-id"));
                    mainArray.add(occurencesObject.clone());
                    occurencesObject.clear();
                    counterArray.clear();
                }
//                System.out.println(linkedList.size());
//                System.out.println(linkedList1.size());
//                int subsequenceCounter = 0;
//                JSONArray counterArray = new JSONArray();
//                int j = 0;
//                for(int i = 0; i < methodList.size(); i++){
//                    if(seqArray[subsequenceCounter].equals(String.valueOf(methodList.get(i)))){
//                        counterArray.add(counterList.get(i));
//                        if(subsequenceCounter == 0){
//                            j = i;
//                        }
//                        subsequenceCounter = subsequenceCounter + 1;
//
//                    }
//                    if(subsequenceCounter == seqArray.length){
////                        mainArray.add(counterArray.clone());
//                        occurencesObject.put("counters",counterArray.clone());
//                        occurencesObject.put("sequence-no",lineMap.get("sequence-id"));
//                        mainArray.add(occurencesObject.clone());
//                        occurencesObject.clear();
////                        System.out.println(counterArray.toJSONString());
//                        counterArray.removeAll(counterArray);
//                        subsequenceCounter = 0;
//                        i=j;
//                    }
//                }
            }
        }catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mainArray;
    }

    public ArrayList<List<String>> getOccurence(String[] seqArray, ArrayList<String> mthdList, ArrayList<String> ctrList) {
        List<HashSet<String>> tempList = getTempList(seqArray, mthdList, ctrList);

        Set<List<String>> lists = Sets.cartesianProduct(tempList);
        ArrayList<List<String>> setList = new ArrayList<List<String>>(lists);
        Iterator it = setList.iterator();

        while(it.hasNext()){
            String s = it.next().toString();
//            System.out.println(s);
            boolean bool = isIncreasing(s);
            if(!bool){
//                System.out.println(s+ "bool");
                it.remove();
            }
        }
       return setList;
    }

    public boolean isIncreasing(String s) {
        s = s.substring(1,s.length()-1);
        String[] sarr = s.split(",");
//        System.out.println(Arrays.toString(sarr));
        String previous = "";
        int prev = -1;
        for (final String current: sarr) {
//            System.out.println("current:" + current + "previous:" + previous + ":"+current.trim().compareTo(previous));
            if (Integer.parseInt(current.trim()) <= prev){
                return false;
            }
            prev = Integer.parseInt(current.trim());
        }

        return true;
    }

    public List<HashSet<String>> getTempList(String[] seqArray, ArrayList<String> mthdList, ArrayList<String> ctrList) {
        List<HashSet<String>> tempList = new ArrayList<HashSet<String>>();
        for(String seq : seqArray){
            HashSet<String> set = new HashSet<String>();
            int i=0;
            for (Iterator<String> iter = mthdList.iterator(); iter.hasNext(); i++) {
                Object obj = iter.next();
                String st =String.valueOf(obj);
//                System.out.println(st + ":" + seq);

                if(seq.trim().equals(st.trim())){
//                    System.out.println("str:"+st+"in:"+mthdList.indexOf(st));
                    set.add(ctrList.get(i));

//                    System.out.println(Arrays.toString(set.toArray()));
                }
            }

//            System.out.println(Arrays.toString(set.toArray()));
            tempList.add((HashSet)set.clone());
            //set.clear();
        }

        return tempList;
    }

    public JSONArray  getOccurrences(String[] seqArray, String folderName) {

        JSONArray mainArray = new JSONArray();
        try {
            Object obj= JSONValue.parse(new FileReader(folderName + "SequenceMapJSON1.log"));
            JSONArray array=(JSONArray)obj;
            Iterator iterator = array.iterator();
            JSONObject occurencesObject = new JSONObject();
            while (iterator.hasNext()){
                String line = iterator.next().toString();
                Map lineMap = (Map)parser.parse(line, containerFactory);
//                System.out.println(line);
//                System.out.println(lineMap.get("counters"));
                LinkedList<String> linkedList1 = (LinkedList)parser.parse(lineMap.get("counters").toString(),containerFactory);
                LinkedList<String> linkedList = (LinkedList)parser.parse(lineMap.get("method-id").toString(),containerFactory);
//                System.out.println(linkedList.size());
//                System.out.println(linkedList1.size());
                int subsequenceCounter = 0;
                JSONArray counterArray = new JSONArray();
                int j = 0;
                for(int i = 0; i < linkedList.size(); i++){
                    if(seqArray[subsequenceCounter].equals(String.valueOf(linkedList.get(i)))){
                        counterArray.add(linkedList1.get(i));
                        if(subsequenceCounter == 0){
                            j = i;
                        }
                        subsequenceCounter = subsequenceCounter + 1;

                    }
                    if(subsequenceCounter == seqArray.length){
//                        mainArray.add(counterArray.clone());
                        occurencesObject.put("counters",counterArray.clone());
                        occurencesObject.put("sequence-no",lineMap.get("sequence-id"));
                        mainArray.add(occurencesObject.clone());
                        occurencesObject.clear();
//                        System.out.println(counterArray.toJSONString());
                        counterArray.removeAll(counterArray);
                        subsequenceCounter = 0;
                        i=j;
                    }
                }
            }
        }catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mainArray;
    }

    private void runBIDE(String folderName) {
        String line;
        Process pr = null;
        try {
//            comment the following 3 lines if direct command from another file
            BufferedWriter bw = new BufferedWriter(new FileWriter(folderName + "BIDE_cmd.bat"));
            bw.write(folderName + "BIDEwithOUTPUT.exe " + folderName + "BideIP.spec 0.2");
            bw.close();
//            change file name here for direct execution
            pr = Runtime.getRuntime().exec(folderName + "BIDE_cmd.bat");

        BufferedReader Resultset = new BufferedReader(
                    new InputStreamReader (
                            pr.getInputStream()));

            while((line=Resultset.readLine())!=null)
            {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
