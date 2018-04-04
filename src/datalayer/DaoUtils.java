package datalayer;

import java.io.File;
import java.util.logging.Logger;


public class DaoUtils {
    private String REST_SERVICE_URL = "http://localhost:8080/College_sim3_war_exploded/finances";
    private static Logger logger = Logger.getLogger("DaoUtils");

    static public String storageDirectoryName() {
        return getStorageDirectory();
    }

    static private String getStorageDirectory() {
        String collegeDir = System.getenv("SystemDrive")+ File.separator +"webAppStories";
        new File(collegeDir).mkdirs();
        return collegeDir;
    }
}
