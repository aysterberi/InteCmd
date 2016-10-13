public class CurrentDirectory {

    private static String currentDirectory;

    public CurrentDirectory() {
        currentDirectory = System.getProperty("user.dir");
    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(String path) {
        currentDirectory = path;
    }
}
