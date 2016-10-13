public class ChangeDirectory {

    private CurrentDirectory currentDirectory = new CurrentDirectory();

    public String currentDirectory() {
        return System.getProperty("user.dir");
    }

    public void homeDirectory() {
        currentDirectory.setCurrentDirectory(System.getProperty("user.home"));
    }
}
