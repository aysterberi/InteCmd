import java.io.File;
import java.util.ArrayList;

public class ChangeDirectory {

    public final static String CMD_PATTERN_PARENT_DIRECTORY = "..";
    public final static String PATTERN_ROOT_WINDOWS = "[a-zA-Z]{1}:\\\\{0,1}+";
    public final static String PATTERN_ROOT_UNIX = "/";
    public final static String PATTERN_WINDOWS_DIRECTORY = "^[^<>:\"/\\\\|?*]*$";
    public final static String PATTERN_UNIX_DIRECTORY = "^[^/]$";

    private CurrentDirectory currentDirectory = new CurrentDirectory();

    public ChangeDirectory(String[] data) {
        try {
            switch(data.length) {
                case 1:
                    homeDirectory();
                    break;
                case 2:
                    readOptions(data);
                    break;
                default:
                    moveDown(data);
                    break;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ChangeDirectory() {
        this(new String[] {""});
    }

    private void readOptions(String[] options) {
        if(options[1].matches(CMD_PATTERN_PARENT_DIRECTORY))
            moveUp();
        else if (options[1].matches(PATTERN_WINDOWS_DIRECTORY) || options[1].matches(PATTERN_UNIX_DIRECTORY))
            moveDown(options[1]);
        else if(options[1].matches(PATTERN_ROOT_WINDOWS) || options[1].matches(PATTERN_ROOT_UNIX))
            changeRoot(options[1].toLowerCase());
        else
            throw new IllegalArgumentException("No such file or directory.");
    }

    public void homeDirectory() {
        currentDirectory.setCurrentDirectory(System.getProperty("user.home"));
    }

    private void moveUp() {
        String[] pathParts = splitPath();
        String path = systemIsWindows() ? pathParts[0] : PATTERN_ROOT_UNIX;
        if (systemIsWindows()) {
            for (int i = 1; i < pathParts.length - 1; i++) {
                path += CurrentDirectory.SEPARATOR + pathParts[i];
            }
        } else {
            for (int i = 1; i < pathParts.length - 2; i++) {
                path += pathParts[i] + CurrentDirectory.SEPARATOR;
            }
            path += pathParts[pathParts.length-2];
        }
        currentDirectory.setCurrentDirectory(path);
    }

    private String[] splitPath() {
        return System.getProperty("os.name").startsWith("Windows") ? currentDirectory.toString().split(CurrentDirectory.SEPARATOR + CurrentDirectory.SEPARATOR) : currentDirectory.toString().split(CurrentDirectory.SEPARATOR);
    }

    private boolean systemIsWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    public void moveDown(String[] splitDirectory) {
        String directory = splitDirectory[1];
        for(int i = 2; i < splitDirectory.length; i++)
            directory += " " + splitDirectory[i];
        moveDown(directory);
    }

    public void moveDown(String directory) {
        if (!directoryExists(directory))
            throw new IllegalArgumentException("No such file or directory.");
        currentDirectory.setCurrentDirectory(currentDirectory.toString() + CurrentDirectory.SEPARATOR + directory);
    }

    private boolean directoryExists(String directory) {
        ArrayList<File> files = new LSCommand(currentDirectory.toString()).getDirectories();
        for(File file : files)
            if (file.getName().equals(directory))
                return true;
        return false;
    }

    public void changeRoot(String root) {
        if(!rootExists(root))
            throw new IllegalArgumentException("No such file or directory.");
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
