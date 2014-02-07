import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Kush on 2/3/14.
 */
public class Test {
    public static boolean isPresent(String [] substring, String [] sequence){
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
    public static void main(String[] args) {
        BufferedReader bufferedReader;
        BufferedWriter methodMap;
        BufferedWriter sequenceMap;
        BufferedWriter bide_ip;
        BufferedWriter bide_ip_spec;
        Scanner sc;
        HashMap<String,Integer> hm = new HashMap<String, Integer>();
        int methodMapCounter = 0;
        int sequenceMapCounter = 0;
        int longest_seq = 0;
        int longest_seq_counter = 0;
        float avg_count = 0;

        ArrayList<String> functions = new ArrayList<String>();
        try {
            //bufferedReader = new BufferedReader(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTrace.log"));
            sc = new Scanner(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTrace.log"));
            methodMap = new BufferedWriter(new FileWriter("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodMap.log"));
            sequenceMap = new BufferedWriter(new FileWriter("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\SequenceMap.log"));
            bide_ip = new BufferedWriter(new FileWriter("C:\\Users\\Kush\\Documents\\CS598\\BIDE\\bide_ip.txt"));
            bide_ip_spec = new BufferedWriter(new FileWriter("C:\\Users\\Kush\\Documents\\CS598\\BIDE\\bide_ip.spec"));
            bide_ip_spec.write("C:\\Users\\Kush\\Documents\\CS598\\BIDE\\bide_ip.txt");
            String line = null;
            StringBuffer sequenceMapLine = new StringBuffer();
            String seqMap = "";
            int counter;
            line = sc.nextLine();
            //line = bufferedReader.readLine();
            String[] parts = line.split(":");
            int leastLevel = Integer.parseInt(parts[4].split("=")[1].trim());
            //bufferedReader.close();
            //bufferedReader = new BufferedReader(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTrace.log"));
            sc.close();
            sc = new Scanner(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\MethodTrace.log"));
            //while((line = bufferedReader.readLine()) != null){
            while(sc.hasNextLine()){
                line = sc.nextLine();
                //System.out.println(line);
                parts = line.split(":");
                //get the counter
                counter = Integer.parseInt(parts[1].trim());
                int level = Integer.parseInt(parts[4].split("=")[1].trim());
                String method = parts[3];
                //System.out.println("counter:"+counter);
                //get the level number
                //System.out.println("level:" + parts[4].split("=")[1].trim());
                longest_seq_counter = longest_seq_counter + 1;
                avg_count = avg_count + 1;
                if(level == leastLevel){
                    if(sequenceMapCounter != 0){
                        if(longest_seq_counter > longest_seq){
                            longest_seq = longest_seq_counter - 1;
                        }
                        longest_seq_counter = 1;
                        sequenceMap.write(sequenceMapCounter  + seqMap);
                        sequenceMap.newLine();
                        //sequenceMap.write("hello");
                        //sequenceMap.write(sequenceMapCounter + sequenceMapLine.toString());
                        //sequenceMap.newLine();
                        seqMap = "";

                        bide_ip.append(" -1");
                        bide_ip.newLine();
                        //System.out.println(seqMap);
                    }
                    sequenceMapCounter = sequenceMapCounter + 1;
                }
                seqMap = seqMap.concat(" , " + line);
                //System.out.println(seqMap);
                //sequenceMapLine.append(line);
                //get the function
                //System.out.println("method:" + parts[3]);
                //get the class
                //System.out.println("class:"+parts[2]);
                for(String part : parts){
                    //System.out.println(part);
                }
                methodMap.write(methodMapCounter);
                if(!functions.contains(method)){
                    hm.put(method,methodMapCounter);
                    methodMap.write(methodMapCounter + " ," + method);
                    functions.add(method);
                    methodMap.newLine();
                    methodMapCounter = methodMapCounter + 1;
                }
                System.out.println(hm.get(method));
                bide_ip.append(hm.get(method).toString() + " ");

            }
            bide_ip.append("-1 -2");
            bide_ip.close();
            bide_ip_spec.newLine();
            System.out.println("mmc:" + methodMapCounter);
            System.out.println("hm:"+hm.size());
            bide_ip_spec.write(String.valueOf(methodMapCounter));
            bide_ip_spec.newLine();
            bide_ip_spec.write(String.valueOf(sequenceMapCounter));
            bide_ip_spec.newLine();
            bide_ip_spec.write(String.valueOf(longest_seq));
            bide_ip_spec.newLine();
            int avg = Math.round(avg_count/sequenceMapCounter);
            bide_ip_spec.write(String.valueOf(avg));
            bide_ip_spec.close();
            //sequenceMap.write(sequenceMapCounter + sequenceMapLine.toString());
            sequenceMap.write(sequenceMapCounter + seqMap);
            methodMap.close();
            sequenceMap.close();
            //bufferedReader.close();
            sc.close();
            Process pr = Runtime.getRuntime().exec("C:\\Users\\Kush\\Documents\\CS598\\BIDE\\BIDEwithOUTPUT.exe C:\\Users\\Kush\\Documents\\CS598\\BIDE\\bide_ip.spec 0.2");
            BufferedReader Resultset = new BufferedReader(
                    new InputStreamReader (
                            pr.getInputStream()));
            while((line=Resultset.readLine())!=null)
            {
                System.out.println(line);
            }
            sc = new Scanner(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\frequent.dat"));
            BufferedWriter final_op = new BufferedWriter(new FileWriter("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\BideMethodTracemap"));
            while (sc.hasNextLine()){
                String closed_seq = sc.nextLine();
                Scanner bide_ip_sc = new Scanner(new FileReader("C:\\Users\\Kush\\Documents\\CS598\\BIDE\\bide_ip.txt"));
                int counter_op = 0;
                final_op.write(closed_seq.split(":")[0]+" => ");
                while (bide_ip_sc.hasNextLine()){
                    counter_op = counter_op + 1;
                    String ip = bide_ip_sc.nextLine();
//                    System.out.println("ip:" + ip);
                    String [] ip_split = ip.split("-1");
                    String [] sequence = ip_split[0].split(" ");
                    //System.out.println(ip_split[0].replaceAll("\\s+",""));
                    String [] line_split = closed_seq.split(":");
                    String [] substring = line_split[0].split(" ");
                    if(isPresent(substring, sequence)){
                        String line_info = "";
//                        System.out.println(closed_seq+":"+ip);
                        Scanner info = new Scanner(new FileReader("C:\\Users\\Kush\\IdeaProjects\\TestProcessing\\src\\SequenceMap.log"));
                        for(int i = 0; i < counter_op; i++){
                            System.out.println(closed_seq+":"+ip);
//                            System.out.println(line_info);
                            line_info = info.nextLine();
                        }
                        String [] line_info_parts = line_info.split(",");
                        final_op.write("("+line_info_parts[0]+":");
                        int j = 0;
                        for(int i = 1; i < line_info_parts.length; i++){
                            String method_name = line_info_parts[i].split(":")[3];
                            if(substring[j].equals(hm.get(method_name).toString())){
                                j = j + 1;
                                System.out.println(line_info_parts[i].split(":")[1]);
                                final_op.write(line_info_parts[i].split(":")[1]);
                                if(j > (substring.length-1)){
                                    final_op.write(")");
                                    break;
                                }
                                final_op.write(",");
                            }
                        }
//                        System.out.println(counter_op + ":" +line_info);
                        info.close();
                    }
                }
                final_op.newLine();
            }
            final_op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
