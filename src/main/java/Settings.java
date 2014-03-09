import org.apache.commons.io.FilenameUtils;

public class Settings {

    public static String basePath = "z:\\Documents\\bide-files";
    public static String subjectAppName = "nanoxml";

    //nanoxml
    public double bide_threshold_values[] = {0.04, 0.05, 0.06, 0.07, 0.08, 0.09, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8 };


    //input files to generating bide input files
    public static String methodMapFile = FilenameUtils.concat(basePath, subjectAppName + "-" + "method-map.json");
    public static String methodSequenceFile = FilenameUtils.concat(basePath, subjectAppName + "-" + "sequence.json");

    //BIDE related data
    public static String bideFilesFolder = basePath;
    public static String bideInputFileName = subjectAppName + "_" + "input.txt";
    public static String bideSpecFileName = subjectAppName + "_" + "input.spec";
    public static String pathToInputFileName = FilenameUtils.concat(basePath, bideInputFileName);

    //BIDE output converted to methods


}
