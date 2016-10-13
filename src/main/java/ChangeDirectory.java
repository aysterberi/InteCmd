public class ChangeDirectory {

    public String currentDirectory() {
        return System.getProperty("user.dir");
    }

    public String homeDirectory() {
        return System.getProperty("user.home");
    }
}
