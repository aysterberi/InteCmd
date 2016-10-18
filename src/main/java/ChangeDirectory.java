import java.io.File;
import java.util.ArrayList;

public class ChangeDirectory {

    private CurrentDirectory currentDirectory = new CurrentDirectory();

    public ChangeDirectory(String[] options) {
        if (options.length > 1) {
            switch (options[1]) {
                case "..":
                    moveUp(currentDirectory.toString());
                    break;
                case "C:\\": case "D:\\":
                    changeRoot(options[1]);
                    break;
                default:
                    moveDown(options[1]);
                    break;
            }
        } else {
            homeDirectory();
        }

    }

    public ChangeDirectory() {
        this(new String[] {""});
    }

    public void homeDirectory() {
        currentDirectory.setCurrentDirectory(System.getProperty("user.home"));
    }

    public void moveUp(String path) {
        String[] directories = path.split("\\\\");
        path = directories[0];
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
        ArrayList<File> files = new LSCommand(currentDirectory.toString()).getDirectories();
        for(File file : files)
            if (file.getName().equals(directory))
                return true;
        return false;
    }

    private void changeRoot(String root) {
        for(File file : File.listRoots()) {
            if(file.toString().equals(root)) {
                currentDirectory.setCurrentDirectory(root);
                return;
            }
        }
    }
}
