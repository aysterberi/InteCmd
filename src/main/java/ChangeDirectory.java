public class ChangeDirectory {

    private CurrentDirectory currentDirectory = new CurrentDirectory();

    public String currentDirectory() {
        return System.getProperty("user.dir");
    }

    public void homeDirectory() {
        currentDirectory.setCurrentDirectory(System.getProperty("user.home"));
    }

    public void moveUp() {
        String[] directories = currentDirectory.toString().split("\\\\");
        String path = directories[0];
        for(int i = 1; i < directories.length - 1; i++)
            path += "\\" + directories[i];
        currentDirectory.setCurrentDirectory(path);
    }
}
