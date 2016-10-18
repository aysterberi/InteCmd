import java.io.File;

public class CurrentDirectory {

    public static final String SEPARATOR = "\\";
    private static String currentDirectory = System.getProperty("user.dir");

    public void setCurrentDirectory(String path) {
        if(path == null)
            throw new IllegalArgumentException("Path was null.");
        else if(path.equals(SEPARATOR))
            throw new IllegalArgumentException("Path can't exceed root directory.");
        else if (path.equals(""))
            throw new IllegalArgumentException("Path can't be empty.");
        if(!pathExists(path))
            throw new IllegalArgumentException("Path does not exist.");
        currentDirectory = path;
    }

    private boolean pathExists(String path) {
        File directory = new File(path);
        return directory.exists();
    }

    public String toString() {
        return currentDirectory;
    }
}
