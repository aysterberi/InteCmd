import java.io.File;

public class ChangeDirectory {

    private CurrentDirectory currentDirectory = new CurrentDirectory();

    public ChangeDirectory(String[] options) {

    }

    public ChangeDirectory() {
        this(new String[] {""});
    }

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

    public boolean moveDown(String directory) {
        if (directoryExists(directory)) {
            currentDirectory.setCurrentDirectory(currentDirectory.toString() + "\\" + directory);
            return true;
        }
        return false;
    }

    private boolean directoryExists(String directory) {
        File[] files = new LSCommand(currentDirectory.toString()).getDirectories();
        for(File file : files)
            if (file.getName().equals(directory))
                return true;
        return false;
    }
}
