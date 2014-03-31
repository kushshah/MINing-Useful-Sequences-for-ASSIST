import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    int noOfmthds;
//    ArrayList<ArrayList<String>> result;

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
        noOfmthds = 0;
//        result = new ArrayList<ArrayList<String>>();

    }
    public void generateOP(){
//        createMethodMap();
//        createSequenceMap();
//        createBIDEIP();
        createBIDEIPfromSeqMap();
        createBIDESpec();
        for(String threshold : Settings.bide_threshold_values){
            File file = new File(Settings.basePath + Settings.subjectAppName + "\\" + threshold);
            file.mkdir();
            runBIDE(threshold);
            formatBIDEOP(threshold);
        }

    }

    private void createBIDEIPfromSeqMap() {
        try {
//            obj = JSONValue.parse(new FileReader(this.folderName+ "data\\nanoxml-method-map.json"));
            obj = JSONValue.parse(new FileReader(Settings.methodMapFile));
            array=(JSONArray)obj;
            noOfmthds = array.size();
            boolean flag = false;
            JSONArray seq = new JSONArray();
//            bufferedWriter = new BufferedWriter(new FileWriter(bideFolder + "BideIP.txt"));
            bufferedWriter = new BufferedWriter(new FileWriter(Settings.bideInputFileName));
            obj = JSONValue.parse(new FileReader(Settings.methodSequenceFile));
            array=(JSONArray)obj;
            iterator = array.iterator();
            while (iterator.hasNext()){
                String line = iterator.next().toString();
                Map lineMap = (Map)parser.parse(line, containerFactory);
//                System.out.println(lineMap.get("method-id"));
                String mthdString = lineMap.get("method-id").toString();
                int tempMthdCount = 0;
                for(String str : mthdString.substring(1,mthdString.length()-1).split(", ")){
//                    System.out.println(str);
                    bufferedWriter.write(str + " ");
                    tempMthdCount = tempMthdCount + 1;
                }
                bufferedWriter.write("-1");
                bufferedWriter.newLine();
                if(tempMthdCount > this.maxSeq){
                    this.maxSeq = tempMthdCount;
                }
                this.seqTotal = this.seqTotal + tempMthdCount;
                this.seqCount = this.seqCount + 1;


            }
            bufferedWriter.write("-2");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void createBIDESpec() {
        try {
//            bufferedWriter = new BufferedWriter(new FileWriter(bideFolder + "BideIP.spec"));
            bufferedWriter = new BufferedWriter(new FileWriter(Settings.bideSpecFileName));
//            bufferedWriter.write(bideFolder + "BideIP.txt");
//            System.out.print(Settings.bideInputFileName);
//            System.out.println("haha");
            bufferedWriter.write(Settings.bideInputFileName.trim());
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(noOfmthds));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(seqCount));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(maxSeq));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(Math.round(seqTotal / seqCount)));
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
            noOfmthds = methodHashMap.size();
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

    private void formatBIDEOP(String threshold) {
        try {
            BufferedWriter bideOP = new BufferedWriter(new FileWriter(Settings.jsonOutputFileNameBase + threshold + Settings.jsonOutputFileNameEnd));
            bideOP.write("[");
            Scanner sc = new Scanner(new FileReader(Settings.bideOutputFileNameBase + threshold + Settings.bideOutputFileNameEnd));
            JSONArray mainArray = new JSONArray();
            int occCount = 0;
            while (sc.hasNextLine()){
                JSONObject  lineObject = new JSONObject();
                String bideOPLine = sc.nextLine();
//                System.out.println(bideOPLine);
                String [] seqArray = bideOPLine.split(":")[0].split(" ");
                JSONArray seqJSONArray = new JSONArray();
                JSONArray occurencesJSONArray;
                for(String each : seqArray){
                    seqJSONArray.add(Integer.parseInt(each));
                }
                lineObject.put("method-sequence", seqJSONArray);
                occurencesJSONArray = getOccurrences(seqArray);
                //getOccurrences(seqArray);
                lineObject.put("occurences", occurencesJSONArray);
//                System.out.println(Arrays.toString(seqArray) +":"+ occurencesJSONArray.size());
                occCount = occCount + occurencesJSONArray.size();
//                System.out.println("Total:"+occCount);
//                System.out.println(lineObject.toJSONString());
//                Iterator iterator = occurencesJSONArray.iterator();
//                while(iterator.hasNext()){
//                    System.out.println(iterator.next());
//
//                }
                //mainArray.add(lineObject);
                bideOP.append(lineObject.toJSONString() + ",");
                bideOP.newLine();
            }
            bideOP.append("]");
            bideOP.close();
            //bideOP.write(mainArray.toJSONString());
            //bideOP.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONArray getOccurrences(String[] seqArray) {
        JSONArray mainArray = new JSONArray();
        try {
            Object obj= JSONValue.parse(new FileReader(Settings.methodSequenceFile));
            JSONArray array=(JSONArray)obj;
            Iterator iterator = array.iterator();
//            JSONObject occurencesObject = new JSONObject();
            while (iterator.hasNext()){
                String line = iterator.next().toString();
                Map lineMap = (Map)parser.parse(line, containerFactory);
//                System.out.println(line);
//                System.out.println(lineMap.get("counters"));
                ArrayList<String> counterList = (ArrayList)parser.parse(lineMap.get("counters").toString(),containerFactory);
                ArrayList<String> ctrArrayTemp = new ArrayList<String>(counterList);
                ArrayList<String> ctrArray = new ArrayList<String>();
                for(Object s: ctrArrayTemp){
                    ctrArray.add(s.toString());
                }
//                HashSet<String> ctrTemp = new HashSet<String>(counterList);
//                HashSet<String> ctrSet = new HashSet<String>();
//                for(Object s: ctrTemp){
//                    ctrSet.add(s.toString());
//                }
                ArrayList<String> methodList = (ArrayList<String>)parser.parse(lineMap.get("method-id").toString(),containerFactory);
                ArrayList<String> mthdArrayTemp = new ArrayList<String>(methodList);
                ArrayList<String> mthdArray = new ArrayList<String>();
                for(Object s: mthdArrayTemp){
                    mthdArray.add(s.toString());
                }
//                HashSet<String> mthdTemp = new HashSet<String>(methodList);
//                HashSet<String> methodSet = new HashSet<String>();
//                for(Object s: mthdTemp){
//                    methodSet.add(s.toString());
//                }
                //String [] mthdArray = methodList.toArray(new String[methodList.size()]);
//                ArrayList<ArrayList<String>> occurence = getOccurence(seqArray,mthdArray, ctrArray);
//                if(seqArray.length > mthdArray.size()){
//                    ArrayList<ArrayList<String>> occurence = getSubSequences(seqArray,mthdArray, ctrArray);
//                    ArrayList<ArrayList<String>> occurence = getShortestSubSequences(seqArray,mthdArray, ctrArray);
                ArrayList<ArrayList<String>> occurence = getShortestRankedSubSequences(seqArray, mthdArray, ctrArray);
    //                if(occurence.size() != 0){
    //                System.out.println(lineMap.get("sequence-id")+":"+occurence.size());
    //                 }
    //                System.out.println("I am here");
    //                JSONArray counterArray = new JSONArray();
                    for(ArrayList<String> lstr : occurence){
                        JSONArray counterArray = new JSONArray();
                        for(String str: lstr){
                            counterArray.add(Integer.parseInt(str));
                        }
                        JSONObject occurencesObject = new JSONObject();
    //                    occurencesObject.put("counters",counterArray.clone());
                        occurencesObject.put("counters",counterArray);
                        occurencesObject.put("sequence-no",lineMap.get("sequence-id"));
                        mainArray.add(occurencesObject);
    //                    mainArray.add(occurencesObject.clone());
    //                    occurencesObject.clear();
    //                    counterArray.clear();
                    }
               // }
            }
        }catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mainArray;
    }

    private ArrayList<ArrayList<String>> getShortestRankedSubSequences(String[] seqArray, ArrayList<String> mthdArray, ArrayList<String> ctrArray) {
        ArrayList<ArrayList<String>> listOfOccurrences = getIndividualSet(seqArray, mthdArray, ctrArray);
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for(String each : listOfOccurrences.get(0)){
            result.add(new ArrayList<String>(Arrays.asList(each)));
        }
        for(int i =1; i < listOfOccurrences.size();i++){
            ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
            for(String comparer : listOfOccurrences.get(i)){
                for(ArrayList<String> partSeq : result){
                    if(Integer.parseInt(partSeq.get(partSeq.size()-1)) < Integer.parseInt(comparer)){
                        ArrayList<String> tempArr = new ArrayList<String>(partSeq);
                        tempArr.add(comparer);
                        temp.add(tempArr);
                    }
                }
            }
            result = temp;
        }
        int difference = Integer.MAX_VALUE;
        int counter = 0;
        ArrayList<ArrayList<String>> newResult = new ArrayList<ArrayList<String>>();
        for(int index = 0;index < Settings.rank;index++){
            for(int i = 0;i<result.size();i++){
                if((Integer.parseInt(result.get(i).get(result.get(i).size()-1)) - Integer.parseInt(result.get(i).get(0))) < difference){
                    difference = (Integer.parseInt(result.get(i).get(result.get(i).size()-1)) - Integer.parseInt(result.get(i).get(0)));
                    counter = i;
                }
            }
            if(result.size() != 0){
                newResult.add(result.get(counter));
                result.remove(counter);
                difference = Integer.MAX_VALUE;
                counter = 0;
            }
        }
        return newResult;
    }

    private ArrayList<ArrayList<String>> getShortestSubSequences(String[] seqArray, ArrayList<String> mthdArray, ArrayList<String> ctrArray) {
        ArrayList<ArrayList<String>> listOfOccurrences = getIndividualSet(seqArray, mthdArray, ctrArray);
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for(String each : listOfOccurrences.get(0)){
            result.add(new ArrayList<String>(Arrays.asList(each)));
        }
        for(int i =1; i < listOfOccurrences.size();i++){
            ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
            for(String comparer : listOfOccurrences.get(i)){
                for(ArrayList<String> partSeq : result){
                    if(Integer.parseInt(partSeq.get(partSeq.size()-1)) < Integer.parseInt(comparer)){
                        ArrayList<String> tempArr = new ArrayList<String>(partSeq);
                        tempArr.add(comparer);
                        temp.add(tempArr);
                    }
                }
            }
            result = temp;
        }
        int difference = Integer.MAX_VALUE;
        int counter = 0;
        ArrayList<ArrayList<String>> newResult = new ArrayList<ArrayList<String>>();
        for(int i = 0;i<result.size();i++){
            if((Integer.parseInt(result.get(i).get(result.get(i).size()-1)) - Integer.parseInt(result.get(i).get(0))) < difference){
                difference = (Integer.parseInt(result.get(i).get(result.get(i).size()-1)) - Integer.parseInt(result.get(i).get(0)));
                counter = i;
            }
        }
        if(result.size() != 0){
            newResult.add(result.get(counter));
        }
        return newResult;
    }

    public ArrayList<ArrayList<String>> getSubSequences(String[] seqArray, ArrayList<String> mthdArray, ArrayList<String> ctrArray) {
        ArrayList<ArrayList<String>> listOfOccurrences = getIndividualSet(seqArray, mthdArray, ctrArray);
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for(String each : listOfOccurrences.get(0)){
            result.add(new ArrayList<String>(Arrays.asList(each)));
        }
        for(int i =1; i < listOfOccurrences.size();i++){
            ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
            for(String comparer : listOfOccurrences.get(i)){
                for(ArrayList<String> partSeq : result){
                    if(Integer.parseInt(partSeq.get(partSeq.size()-1)) < Integer.parseInt(comparer)){
                        ArrayList<String> tempArr = new ArrayList<String>(partSeq);
                        tempArr.add(comparer);
                        temp.add(tempArr);
                    }
                }
            }
            result = temp;
        }

        return result;
    }

    public ArrayList<ArrayList<String>> getOccurence(String[] seqArray, ArrayList<String> mthdList, ArrayList<String> ctrList) {
//        List<HashSet<String>> tempList = getTempList(seqArray, mthdList, ctrList);
//        for(HashSet<String> set: tempList){
//            System.out.println(Arrays.toString(set.toArray()));
//        }
//        long beginTime = System.currentTimeMillis();
//        System.out.println("begin:"+beginTime);
        ArrayList<ArrayList<String>> tempListOfArrayList = getIndividualSet(seqArray, mthdList, ctrList);
        ArrayList<ArrayList<String>> res = getCartesianProduct(tempListOfArrayList);
//        for(ArrayList<String> combination : res) {
//            System.out.println(combination);
//        }
//        Set<List<String>> lists = Sets.cartesianProduct(tempList);
//        long endTime = System.currentTimeMillis();
//        System.out.println("end:"+endTime);
//        long difference = endTime - beginTime;
//        System.out.println(Arrays.toString(seqArray)+":"+mthdList+difference);
//        ArrayList<List<String>> setList = new ArrayList<List<String>>(lists);
//        ArrayList<ArrayList<String>> setList = new ArrayList<ArrayList<String>>(res);
//        System.out.println("set"+setList.size());
////        Iterator<List<String>> it = setList.iterator();
//        Iterator<ArrayList<String>> it = setList.iterator();
//        int mycounter = 0;
//        while(it.hasNext()){
////            mycounter = mycounter + 1;
////            System.out.println(mycounter);
//            String s = it.next().toString();
////            System.out.println(s);
////            List<String> list = it.next();
////            boolean sorted = Ordering.natural().isOrdered(list);
//            boolean bool = isIncreasing(s);
//            if(!bool){
////                System.out.println(s+ "bool");
//                it.remove();
//            }
//
//        }
//       return setList;
        return res;
    }

    public ArrayList<ArrayList<String>> getIndividualSet(String[] seqArray, ArrayList<String> mthdList, ArrayList<String> ctrList) {
        ArrayList<ArrayList<String>> tempList = new ArrayList<ArrayList<String>>();
        ArrayList<String> set = new ArrayList<String>();
        for(String seq : seqArray){
//            ArrayList<String> set = new ArrayList<String>();
            int i=0;
            for (Iterator<String> iter = mthdList.iterator(); iter.hasNext(); i++) {
                String obj = iter.next();
                String st =String.valueOf(obj);
                //                System.out.println(st + ":" + seq);

                if(seq.trim().equals(st.trim())){
                    //                    System.out.println("str:"+st+"in:"+mthdList.indexOf(st));
                    set.add(ctrList.get(i));

                    //                    System.out.println(Arrays.toString(set.toArray()));
                }
            }

            //            System.out.println(Arrays.toString(set.toArray()));
            tempList.add(new ArrayList<String>(set));
            set.clear();
        }

        return tempList;
    }

    public ArrayList<ArrayList<String>> getCartesianProduct(ArrayList<ArrayList<String>> tempList) {
        int n = tempList.size();
        int solutions = 1;

        for(ArrayList<String> vector : tempList) {
            solutions *= vector.size();
        }

        //String[][] allCombinations = new String[solutions + 1][];
        ArrayList<ArrayList<String>> allCombinations = new ArrayList<ArrayList<String>>();
        //allCombinations[0] = dataStructure.keySet().toArray(new String[n]);
        boolean breakFlag = false;
        int count = 0;
        for(int i = 0; i < solutions; i++) {
            breakFlag = false;
//            count = count + 1;
            int prev = -1;
            ArrayList<String> combination = new ArrayList<String>(n);
            int j = 1;
            for(ArrayList<String> vec : tempList) {
                int curr = Integer.parseInt(vec.get((i / j) % vec.size()));
                if(curr <= prev){
                    breakFlag = true;
                    break;
                }
                combination.add(vec.get((i / j) % vec.size()));
                prev = Integer.parseInt(vec.get((i / j) % vec.size()));
                j *= vec.size();

            }
            if(breakFlag){
                continue;
            }
            allCombinations.add(new ArrayList<String>(combination));
        }
//        System.out.println(count);
        return allCombinations;
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

    private void runBIDE(String threshold) {
        String line;
        Process pr = null;
        try {
//            comment the following 3 lines if direct command from another file
            BufferedWriter bw = new BufferedWriter(new FileWriter(Settings.batchFileNameBase + threshold + Settings.batchFileNameEnd));
            bw.write("cd " + Settings.basePath + Settings.subjectAppName + "\\" + threshold);
            bw.newLine();
            bw.write(Settings.bideExecFolder + "BIDEwithOUTPUT.exe " + Settings.bideSpecFileName + " " + threshold);
            bw.close();
//            change file name here for direct execution
            pr = Runtime.getRuntime().exec(Settings.batchFileNameBase + threshold + Settings.batchFileNameEnd);

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
