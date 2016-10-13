public class CurrentDirectory {

    private static String currentDirectory;

    public CurrentDirectory() {
        currentDirectory = System.getProperty("user.dir");
    }

    public void setCurrentDirectory(String path) {
        currentDirectory = path;
    }

    public String toString() {
        return currentDirectory;
    }
}
