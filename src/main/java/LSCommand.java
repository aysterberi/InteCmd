import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class LSCommand{
    private final String DEFAULTPATH = ".";
    private File file = new File(DEFAULTPATH);
    private CurrentDirectory currentDirectory = new CurrentDirectory();
    private ArrayList<File> directories = new ArrayList<>();
    private ArrayList<File> files = new ArrayList<>();

    public LSCommand (String pathToFile){
        this.file = new File(pathToFile);
        setDirectories();
        setFiles();
    }

    public LSCommand(String[] commands, CurrentDirectory currentDirectory) {
        this.currentDirectory = currentDirectory;
        this.file = new File(currentDirectory.toString());
        setDirectories();
        setFiles();
        if (commands.length > 1){
            switch (commands[1]){
                case "-l":
                    listDirectoriesTopDown();
                    listFilesTopDown();
                    break;
                case "-f":
                    System.out.println("Files");
                    getFiles();
                    break;
                case "-lf":
                    System.out.println("Files");
                    listFilesTopDown();
                    break;
                case "-d":
                    System.out.println("Directories");
                    getDirectories();
                    break;
                case "-ld":
                    System.out.println("Directories");
                    listDirectoriesTopDown();
                    break;
                default:
                    System.out.println("are u retarded?");
            }
        }else {
            System.out.println(getDirectories().size() > 0 ? getDirectories() :
                    "No directories in this directory");
            System.out.println(getFiles().size() > 0 ? getFiles(): "No files in this directory");
        }

    }

    public void setDirectories (){
        directories.addAll(Arrays.asList(file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        })));
    }

    public void setFiles () {
        files.addAll(Arrays.asList(file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isFile();
            }
        })));
    }

    public ArrayList<File> getFiles() {
        if (files == null){
            throw new NullPointerException();
        }else {
        return files;
        }
    }

    public File getFile() {
        if (file == null || file.length() == 0){
            throw new NullPointerException();
        }else{
        return file;
        }
    }

    public ArrayList<File> getDirectories() {
        if (directories == null || directories.size() == 0){
            throw new NullPointerException();
        }else{
            return directories;
        }
    }

    public void setFile(File file) {
        this.file = file;
    }

    private void listFilesTopDown() {
        files.forEach(file1 -> System.out.println(file1.getName()));
    }

    private void listDirectoriesTopDown() {
        directories.forEach(directories1 -> System.out.println(directories1.getName()));
    }
}
