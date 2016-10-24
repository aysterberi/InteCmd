package intecmd.commands;

import com.sun.org.apache.xpath.internal.SourceTree;
import intecmd.CurrentDirectory;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class LSCommand{
    private final String DEFAULTPATH = ".";
    private File file = new File(DEFAULTPATH);
    private ArrayList<File> directories;
    private ArrayList<File> files;
    public String helpString;

    public LSCommand (String pathToFile){
        file = new File(pathToFile);
        if (!file.getName().equals("")){
            files = new ArrayList<>();
            directories = new ArrayList<>();
            setDirectories();
            setFiles();
        }
    }

    public LSCommand(String[] commands, String pathToFile) {
        this(pathToFile);
        if (commands.length > 1){
            switch (commands[1]){
                case "-l":
                    System.out.println("Directories:");
                    listDirectoriesTopDown();
                    System.out.println("Files:");
                    listFilesTopDown();
                    break;
                case "-f":
                    System.out.println("Files: ");
                    printFiles();
                    break;
                case "-lf":
                    System.out.println("Files:");
                    listFilesTopDown();
                    break;
                case "-d":
                    System.out.print("Directories: ");
                    printDirectories();
                    break;
                case "-ld":
                    System.out.println("Directories:");
                    listDirectoriesTopDown();
                    break;
                case "-help":
                    getHelp();
                    break;
                default:
                    System.out.println("Flag not recognized. Try -help");
            }
        }else {
            System.out.print("Directories: ");
            if (getDirectories().size() > 0){
                printDirectories();
            } else{
                System.out.println("No directories in this directory");
            }
            System.out.print("Files: ");
            if (files.size() > 0){
                printFiles();
            }else {
                System.out.println("No files in this directory");
            }
        }

    }

    private void getHelp() {
        helpString = "-l lists all directories and files in the current folder \n-f show all files in the current folder" +
                "\n-fl lists all files in the current folder\n-d show all directories in the current folder\n" +
                "-ld lists all directories in the current folder";
        System.out.println(helpString);
    }

    public void setDirectories (){
        directories.addAll(Arrays.asList(file.listFiles((dir, name) -> new File(dir, name).isDirectory())));
    }

    public void setFiles () {
        files.addAll(Arrays.asList(file.listFiles((current, name) -> new File(current, name).isFile())));
    }

    public ArrayList<File> getFiles() {
        if (files == null){
            throw new NullPointerException();
        }else {
        return files;
        }
    }

    public ArrayList<File> getDirectories() {
        if (directories == null){
            throw new NullPointerException();
        }else{
            return directories;
        }
    }

    private void printFiles () {
        files.forEach(file1 -> System.out.print(file1.getName() + " "));
    }

    private void printDirectories () {
        directories.forEach(directories1 -> System.out.print(directories1.getName() + " "));
    }

    private void listFilesTopDown() {
        files.forEach(file1 -> System.out.println(file1.getName()));
    }

    private void listDirectoriesTopDown() {
        directories.forEach(directories1 -> System.out.println(directories1.getName()));
    }
}
