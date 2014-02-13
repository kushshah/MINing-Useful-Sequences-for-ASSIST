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
public class Test {
    public boolean isPresent(String [] substring, String [] sequence){
        if(substring.length > sequence.length){
            return false;
        }
        int j = 0;
        int counter = 0;
        for(String each_ss : substring){
            for(int i = j; i < sequence.length; i++){
                if(each_ss.equals(sequence[i])){
                    counter =  counter + 1;
                    j = i + 1;
                    if(counter == substring.length){
//                        for(String one:substring){
//                            System.out.print(one+":");
//                        }
//                        for(String two:sequence){
//                            System.out.print(two);
//                        }
//                        System.out.println(substring+":"+sequence);
                        return true;

                    }
                 break;
                }
            }
            //return false;
        }
        if(counter == substring.length){
            for(String one:substring){
                System.out.print(one+":");
            }
            for(String two:sequence){
                System.out.print(two);
            }
//            System.out.println(substring+":"+sequence);
            return true;

        }
        return false;
    }

    public Test(String folderName, String bideFolder) {
        createMethodMapAndSequenceMap(folderName, bideFolder);
        runBIDE(bideFolder);
        formatBIDEOP(folderName, bideFolder);

    }

    public static void main(String[] args) {
        //--------------------------------------new code---------------------------------------------------------------
        String folderName = "C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\"; /*OR = args[1]*/
        String bideFolder = "C:\\Users\\Kush\\Documents\\CS598\\BIDE\\";
        Test test = new Test(folderName, bideFolder);

        //--------------------------------------new code---------------------------------------------------------------
        //--------------------------------------old code---------------------------------------------------------------
//        BufferedWriter methodMap;
//        BufferedWriter sequenceMap;
//        BufferedWriter bide_ip;
//        BufferedWriter bide_ip_spec;
//        Scanner sc;
//        HashMap<String,Integer> hm = new HashMap<String, Integer>();
//        int methodMapCounter = 0;
//        int sequenceMapCounter = 0;
//        int longest_seq = 0;
//        int longest_seq_counter = 0;
//        float avg_count = 0;
//
//        ArrayList<String> functions = new ArrayList<String>();
//        try {
//            //bufferedReader = new BufferedReader(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTrace.log"));
//            sc = new Scanner(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTrace.log"));
//            methodMap = new BufferedWriter(new FileWriter("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodMap.log"));
//            sequenceMap = new BufferedWriter(new FileWriter("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\SequenceMap.log"));
//            bide_ip = new BufferedWriter(new FileWriter("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\bide_ip.txt"));
//            bide_ip_spec = new BufferedWriter(new FileWriter("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\bide_ip.spec"));
//            bide_ip_spec.write("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\bide_ip.txt");
//            String line = null;
//            StringBuffer sequenceMapLine = new StringBuffer();
//            String seqMap = "";
//            int counter;
//            line = sc.nextLine();
//            //line = bufferedReader.readLine();
//            String[] parts = line.split(":");
//            int leastLevel = Integer.parseInt(parts[4].split("=")[1].trim());
//            //bufferedReader.close();
//            //bufferedReader = new BufferedReader(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTrace.log"));
//            sc.close();
//            sc = new Scanner(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTrace.log"));
//            //while((line = bufferedReader.readLine()) != null){
//            while(sc.hasNextLine()){
//                line = sc.nextLine();
//                //System.out.println(line);
//                parts = line.split(":");
//                //get the counter
//                counter = Integer.parseInt(parts[1].trim());
//                int level = Integer.parseInt(parts[4].split("=")[1].trim());
//                String method = parts[3];
//                //System.out.println("counter:"+counter);
//                //get the level number
//                //System.out.println("level:" + parts[4].split("=")[1].trim());
//                longest_seq_counter = longest_seq_counter + 1;
//                avg_count = avg_count + 1;
//                if(level == leastLevel){
//                    if(sequenceMapCounter != 0){
//                        if(longest_seq_counter > longest_seq){
//                            longest_seq = longest_seq_counter - 1;
//                        }
//                        longest_seq_counter = 1;
//                        sequenceMap.write(sequenceMapCounter  + seqMap);
//                        sequenceMap.newLine();
//                        //sequenceMap.write("hello");
//                        //sequenceMap.write(sequenceMapCounter + sequenceMapLine.toString());
//                        //sequenceMap.newLine();
//                        seqMap = "";
//
//                        bide_ip.append(" -1");
//                        bide_ip.newLine();
//                        //System.out.println(seqMap);
//                    }
//                    sequenceMapCounter = sequenceMapCounter + 1;
//                }
//                seqMap = seqMap.concat(" , " + line);
//                //System.out.println(seqMap);
//                //sequenceMapLine.append(line);
//                //get the function
//                //System.out.println("method:" + parts[3]);
//                //get the class
//                //System.out.println("class:"+parts[2]);
//                for(String part : parts){
//                    //System.out.println(part);
//                }
//                methodMap.write(methodMapCounter);
//                if(!functions.contains(method)){
//                    hm.put(method,methodMapCounter);
//                    methodMap.write(methodMapCounter + " ," + method);
//                    functions.add(method);
//                    methodMap.newLine();
//                    methodMapCounter = methodMapCounter + 1;
//                }
//                System.out.println(hm.get(method));
//                bide_ip.append(hm.get(method).toString() + " ");
//
//            }
//            bide_ip.append("-1 -2");
//            bide_ip.close();
//            bide_ip_spec.newLine();
//            System.out.println("mmc:" + methodMapCounter);
//            System.out.println("hm:"+hm.size());
//            bide_ip_spec.write(String.valueOf(methodMapCounter));
//            bide_ip_spec.newLine();
//            bide_ip_spec.write(String.valueOf(sequenceMapCounter));
//            bide_ip_spec.newLine();
//            bide_ip_spec.write(String.valueOf(longest_seq));
//            bide_ip_spec.newLine();
//            int avg = Math.round(avg_count/sequenceMapCounter);
//            bide_ip_spec.write(String.valueOf(avg));
//            bide_ip_spec.close();
//            //sequenceMap.write(sequenceMapCounter + sequenceMapLine.toString());
//            sequenceMap.write(sequenceMapCounter + seqMap);
//            methodMap.close();
//            sequenceMap.close();
//            //bufferedReader.close();
//            sc.close();
//            Process pr = Runtime.getRuntime().exec("C:\\Users\\Kush\\Documents\\CS598\\BIDE\\BIDEwithOUTPUT.exe C:\\Users\\Kush\\Documents\\CS598\\BIDE\\bide_ip.spec 0.2");
//            BufferedReader Resultset = new BufferedReader(
//                    new InputStreamReader (
//                            pr.getInputStream()));
//            while((line=Resultset.readLine())!=null)
//            {
//                System.out.println(line);
//            }
//            sc = new Scanner(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\frequent.dat"));
//            BufferedWriter final_op = new BufferedWriter(new FileWriter("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\BideMethodTracemap"));
//            while (sc.hasNextLine()){
//                String closed_seq = sc.nextLine();
//                Scanner bide_ip_sc = new Scanner(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\bide_ip.txt"));
//                int counter_op = 0;
//                final_op.write(closed_seq.split(":")[0]+" => ");
//                while (bide_ip_sc.hasNextLine()){
//                    counter_op = counter_op + 1;
//                    String ip = bide_ip_sc.nextLine();
////                    System.out.println("ip:" + ip);
//                    String [] ip_split = ip.split("-1");
//                    String [] sequence = ip_split[0].split(" ");
//                    //System.out.println(ip_split[0].replaceAll("\\s+",""));
//                    String [] line_split = closed_seq.split(":");
//                    String [] substring = line_split[0].split(" ");
//                    if(isPresent(substring, sequence)){
//                        String line_info = "";
////                        System.out.println(closed_seq+":"+ip);
//                        Scanner info = new Scanner(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\SequenceMap.log"));
//                        for(int i = 0; i < counter_op; i++){
//                            //System.out.println(closed_seq+":"+ip);
////                            System.out.println(line_info);
//                            line_info = info.nextLine();
//                        }
//                        String [] line_info_parts = line_info.split(",");
//                        final_op.write("("+line_info_parts[0]+":");
//                        int j = 0;
//                        for(int i = 1; i < line_info_parts.length; i++){
//                            String method_name = line_info_parts[i].split(":")[3];
//                            if(substring[j].equals(hm.get(method_name).toString())){
//                                j = j + 1;
//                                System.out.println(line_info_parts[i].split(":")[1]);
//                                final_op.write(line_info_parts[i].split(":")[1]);
//                                if(j > (substring.length-1)){
//                                    final_op.write(")");
//                                    break;
//                                }
//                                final_op.write(",");
//                            }
//                        }
////                        System.out.println(counter_op + ":" +line_info);
//                        info.close();
//                    }
//                }
//                final_op.newLine();
//            }
//            final_op.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    //---------------------------------------------------old code-------------------------------------------------------
    //---------------------------------------------------new code-------------------------------------------------------

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
                occurencesJSONArray = getOccurrences(seqArray, folderName);
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

    private JSONArray getOccurrences(String[] seqArray, String folderName) {

        JSONArray mainArray = new JSONArray();
        try {
            Object obj= JSONValue.parse(new FileReader(folderName + "SequenceMapJSON.log"));
            JSONArray array=(JSONArray)obj;
            Iterator iterator = array.iterator();
            JSONParser parser = new JSONParser();
            JSONObject occurencesObject = new JSONObject();
            ContainerFactory containerFactory = new ContainerFactory(){
                public List creatArrayContainer() {
                    return new LinkedList();
                }

                public Map createObjectContainer() {
                    return new LinkedHashMap();
                }

            };
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

    private void createMethodMapAndSequenceMap(String folderName, String bideFolder) {
        try {
            BufferedWriter methodMap = new BufferedWriter(new FileWriter(folderName + "MethodMapJSON.log"));
            BufferedWriter sequenceMap = new BufferedWriter(new FileWriter(folderName + "SequenceMapJSON.log"));
            BufferedWriter bideIP = new BufferedWriter(new FileWriter(bideFolder + "BideIP.txt"));
            BufferedWriter bideSpec = new BufferedWriter(new FileWriter(bideFolder + "BideIP.spec"));
            bideSpec.write(bideFolder + "BideIP.txt");
            bideSpec.newLine();
            Object obj= JSONValue.parse(new FileReader(folderName + "MethodTraceJSON.log"));
            JSONArray array=(JSONArray)obj;
            Iterator iterator = array.iterator();
            JSONParser parser = new JSONParser();
            ContainerFactory containerFactory = new ContainerFactory(){
                public List creatArrayContainer() {
                    return new LinkedList();
                }

                public Map createObjectContainer() {
                    return new LinkedHashMap();
                }

            };

            String leastLevel = ((Map)parser.parse(array.get(0).toString(), containerFactory)).get("level").toString();
            int methodCount = 0;
            int seqCount = 0;
            int maxSeq = 0;
            float seqTotal = 0;
            JSONArray methodMapArray = new JSONArray();
            JSONArray seqMapArray = new JSONArray();
            JSONArray seq = new JSONArray();
            JSONArray counterSeq = new JSONArray();
            HashMap<String, Integer > methodHashMap = new HashMap<String, Integer>();
            while (iterator.hasNext()){
                String line = iterator.next().toString();
                Map lineMap = (Map)parser.parse(line, containerFactory);
                //System.out.println(line);
//                Iterator iter = lineMap.entrySet().iterator();
//                while(iter.hasNext()){
//                    Map.Entry entry = (Map.Entry)iter.next();
//                    System.out.println(entry.getKey() + "=>" + entry.getValue());
//                }
                if(methodNotPresent(methodHashMap, lineMap)){
                    JSONObject method = new JSONObject();
                    method.put("method-id",methodCount);
                    method.put("class-name",lineMap.get("class-name"));
                    method.put("method-name", lineMap.get("method-name"));
                    methodMapArray.add(method);
                    methodHashMap.put(lineMap.get("class-name").toString()+lineMap.get("method-name").toString(),methodCount);
                    methodCount = methodCount + 1;
                }
                if(lineMap.get("level").toString().equals(leastLevel) && methodCount != 1 ){
                    JSONObject sequence = new JSONObject();
                    sequence.put("sequence-id", seqCount);
                    seqCount = seqCount + 1;
                    String seqString = JSONValue.toJSONString(seq);
                    //sequence.put("counters", JSONArray.writeJSONString(seq,sequenceMap));//toJSONString(seq));//seq.toJSONString());
                    sequence.put("method-id", seq.clone());
                    sequence.put("counters", counterSeq.clone());
                    seqMapArray.add(sequence);
                    for(String each : seq.toJSONString().substring(1,seq.toJSONString().length()-1).split(",")){
                        bideIP.write(each + " ");
                    }
                    bideIP.write("-1");
                    bideIP.newLine();
                    if(seq.size() > maxSeq){
                        maxSeq = seq.size();
                    }
                    seqTotal = seqTotal + seq.size();
                    seq.removeAll(seq);
                    counterSeq.removeAll(counterSeq);
                }
                seq.add(methodHashMap.get(lineMap.get("class-name").toString()+lineMap.get("method-name").toString()));
                counterSeq.add(lineMap.get("counter"));

            }
            //System.out.println(seq.toJSONString());
            methodMap.write(methodMapArray.toJSONString());
            methodMap.close();
            JSONObject sequence = new JSONObject();
            sequence.put("sequence-id", seqCount);
            seqCount = seqCount + 1;
            sequence.put("method-id", seq.clone());
            sequence.put("counters", counterSeq.clone());
            seqMapArray.add(sequence);
            if(seq.size() > maxSeq){
                maxSeq = seq.size();
            }
            seqTotal = seqTotal + seq.size();
            sequenceMap.write(seqMapArray.toJSONString());
            sequenceMap.close();
            for(String each : seq.toJSONString().substring(1,seq.toJSONString().length()-1).split(",")){
                bideIP.write(each + " ");
            }
            bideIP.write("-1 -2");
            bideIP.close();
            bideSpec.write(String.valueOf(methodHashMap.size()));
            bideSpec.newLine();
            bideSpec.write(String.valueOf(seqCount));
            bideSpec.newLine();
            bideSpec.write(String.valueOf(maxSeq));
            bideSpec.newLine();
            bideSpec.write(String.valueOf(Math.round(seqTotal/seqCount)));
            bideSpec.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean methodNotPresent(HashMap<String,Integer> methodHashMap, Map line) {
        return !(methodHashMap.containsKey(line.get("class-name").toString()+line.get("method-name").toString()));
    }
}
