package util;

import java.io.File;

public class FileUtil {

    private FileUtil() {
        // Utility class
    }

    public static void createDataDirectory() {
        File directory = new File("data");

        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}