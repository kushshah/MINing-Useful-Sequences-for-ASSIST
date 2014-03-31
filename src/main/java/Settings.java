import org.apache.commons.io.FilenameUtils;

public class Settings {

    public static String basePath = "C:\\MyData\\";
    public static String subjectAppName = "nanoxml";
    public static int rank = 10;

    //nanoxml
    public static String bide_threshold_values[] = {"0.11", "0.12", "0.13", "0.14", "0.15", "0.16", "0.17", "0.18", "0.19", "0.20" };


    //input files to generating bide input files
    public static String methodMapFile = FilenameUtils.concat(basePath, subjectAppName +"\\"+subjectAppName +  "-" + "method-map.json");
    public static String methodSequenceFile = FilenameUtils.concat(basePath, subjectAppName +"\\"+subjectAppName +  "-" +  "sequence.json");

    //BIDE related data
    public static String bideExecFolder = "C:\\Users\\Kush\\Documents\\CS598\\BIDE\\";
    public static String bideInputFileName = FilenameUtils.concat(basePath, subjectAppName+"\\"+subjectAppName + "-ip.txt");
    public static String bideSpecFileName = FilenameUtils.concat(basePath, subjectAppName +"\\"+subjectAppName+ "-ip.spec");
    public static String batchFileNameBase = FilenameUtils.concat(basePath, subjectAppName  + "\\");
    public static String batchFileNameEnd =   "\\"  + "cmd.bat";
    public static String bideOutputFileNameBase = FilenameUtils.concat(basePath, subjectAppName + "\\" );
    public static String bideOutputFileNameEnd =  "\\"+"frequent.dat";


    //BIDE output converted to methods

    public static String jsonOutputFileNameBase = FilenameUtils.concat(basePath, subjectAppName +"\\");
    public static String jsonOutputFileNameEnd = "\\"+subjectAppName+"-" + "OP.json";

}
