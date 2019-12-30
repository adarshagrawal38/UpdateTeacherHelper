package sample.FolderHelpers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageLocation {
    public final static Path STORAGE_PATH = Paths.get(System.getProperty("user.home"),"Teacher Helper", "Test");
    public final static String TEST_STORAGE_PATH = STORAGE_PATH.toString() + "\\";
}
