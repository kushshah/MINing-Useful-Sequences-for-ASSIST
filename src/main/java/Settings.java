import org.apache.commons.io.FilenameUtils;

public class Settings {

    public static String basePath = "C:\\MyData\\";
    public static String subjectAppName = "nanoxml";

    //nanoxml
    public static double bide_threshold_values[] = {0.04, 0.05, 0.06, 0.07, 0.08, 0.09, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8 };


    //input files to generating bide input files
    public static String methodMapFile = FilenameUtils.concat(basePath, subjectAppName +"\\"+subjectAppName +  "-" + "method-map.json");
    public static String methodSequenceFile = FilenameUtils.concat(basePath, subjectAppName +"\\"+subjectAppName +  "-" +  "sequence.json");

    //BIDE related data
    public static String bideExecFolder = "C:\\Users\\Kush\\Documents\\CS598\\BIDE\\";
    public static String bideInputFileName = FilenameUtils.concat(basePath, subjectAppName+"\\"+subjectAppName + "-ip.txt");
    public static String bideSpecFileName = FilenameUtils.concat(basePath, subjectAppName +"\\"+subjectAppName+ "-ip.spec");
    public static String batchFileName = FilenameUtils.concat(basePath, subjectAppName +"\\"+subjectAppName +  "-" + "cmd.bat");
    public static String bideOutputFileName = FilenameUtils.concat(basePath, subjectAppName + "\\" + "frequent.dat");


    //BIDE output converted to methods

    public static String jsonOutputFileName = FilenameUtils.concat(basePath, subjectAppName +"\\"+subjectAppName +  "-" + "OP.json");

}
