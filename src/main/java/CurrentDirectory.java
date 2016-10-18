public class CurrentDirectory {

    private static String currentDirectory = System.getProperty("user.dir");

    public void setCurrentDirectory(String path) {
        if(path == null)
            throw new IllegalArgumentException("Path was null.");
        currentDirectory = path;
    }

    public String toString() {
        return currentDirectory;
    }
}
