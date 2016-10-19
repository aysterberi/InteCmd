import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ChangeDirectory {

    public final static String ROOT_PATTERN_WINDOWS = "[a-zA-Z]{1}:\\\\{0,1}+";
    public final static String ROOT_PATTERN_UNIX = "\\";
    private CurrentDirectory currentDirectory = new CurrentDirectory();

    public ChangeDirectory(String[] options) {
        if (options.length > 1) {
            try {
                switch (options[1]) {
                    case "..":
                        moveUp(currentDirectory.toString());
                        break;
                    default:
                        if(options[1].matches(ROOT_PATTERN_WINDOWS))
                            changeRoot(options[1].toLowerCase());
                        else
                            moveDown(options[1]);
                        break;
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
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
        String[] directories = path.split(CurrentDirectory.SEPARATOR+CurrentDirectory.SEPARATOR);
        path = directories[0];
        for(int i = 1; i < directories.length - 1; i++)
            path += CurrentDirectory.SEPARATOR + directories[i];
        currentDirectory.setCurrentDirectory(path);
    }

    public void moveDown(String directory) {
        if (directoryExists(directory))
            currentDirectory.setCurrentDirectory(currentDirectory.toString() + CurrentDirectory.SEPARATOR + directory);
    }

    private boolean directoryExists(String directory) {
        ArrayList<File> files = new LSCommand(currentDirectory.toString()).getDirectories();
        for(File file : files)
            if (file.getName().equals(directory))
                return true;
        return false;
    }

    private void changeRoot(String root) throws FileNotFoundException {
        if(!rootExists(root))
            throw new FileNotFoundException("No such file or directory.");
        else
            currentDirectory.setCurrentDirectory(root.toUpperCase());

    }

    private boolean rootExists(String root) {
        for(File file : File.listRoots())
            if(file.toString().toLowerCase().equals(root.toLowerCase()) || file.toString().toLowerCase().equals(root.toLowerCase() + CurrentDirectory.SEPARATOR))
                return true;
        return false;
    }
}
