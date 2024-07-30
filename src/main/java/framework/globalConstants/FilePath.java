package framework.globalConstants;

import java.io.File;

public class FilePath {
    public final static String basePath = new File("").getAbsolutePath();

    // directory
    public final static String dirTestData = basePath + "/src/main/resources/TestData/";
    public final static String dirFileDownloads = basePath + "\\downloads";
    public final static String dirReports = basePath + "\\reports\\";

    // Status log files
    public final static String dirFailSuitesPath = basePath + "/src/main/resources/Suites/_02_Fail/";
    public final static String dirFailTestRecord = basePath + "/src/main/resources/FailTestRecords/";
    public final static String dirDailyExecStatusLog = dirFailTestRecord + "DailyLog/";
    public final static String fileCurrentExecutionLog = basePath + "/src/main/resources/FailTestRecords/CCLog.csv";
    public final static String fileBrowserConfig = basePath + "/src/main/resources/BrowserConfig.json";


}
