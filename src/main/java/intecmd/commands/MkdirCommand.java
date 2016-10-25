package intecmd.commands;

import intecmd.CurrentDirectory;

import java.io.File;


public class MkdirCommand {

    private File dir;
    private CurrentDirectory currentDirectory = new CurrentDirectory();



    public MkdirCommand(String[] directory) {
        if (directory.length > 1) {
            switch (directory[1]) {
                case "-p":
                    multipleParentDirectories(directory);
                    break;
                default:
                    multipleDirectories(directory);
                    break;
            }
        }
    }


    public MkdirCommand (String path){
        this.dir = new File(path);
        createDirectory();
    }


    public void createDirectory() {
        if (dir.exists() && dir.isDirectory()) {
            System.out.println("Directory already exists!");
        } else {
            System.out.println("Directory do not exists, creating now...");
            boolean success = dir.mkdirs();
            if (success) {
                System.out.printf("Successfully created new directory: %s%n", dir);
            } else {
                System.out.printf("Error, unable to create new directory: %s%n", dir);

            }
        }
    }


    public File getDirectory() {
        if (dir.getName().isEmpty()) {
                throw new NullPointerException();
        } else {
            return dir;
        }
    }

    public boolean getLineWithoutBackslash(String[] line){
        String s = getLine(line).trim();
        return !s.contains("\\");
    }


    private void multipleDirectories(String[] line){
        if (getLineWithoutBackslash(line)){
            String[]tokens = getLine(line).split(" ");
            for (String directory : tokens) {
                this.dir = new File(currentDirectory.toString() + "\\" + directory);
                createDirectory();
            }
        }
    }

    private void multipleParentDirectories(String[] line){
        String[]tokens = getParentLine(line).trim().split(" ");
        for (String directory : tokens) {
            this.dir = new File("\\" + directory);
            createDirectory();

        }
    }


    public String getLine(String[] tokens) {
        String line = "";
        for (int i = 1; i < tokens.length; i++) {
            if (i == tokens.length - 1)
                line += tokens[i];
            else
                line += tokens[i] + " ";

        }
        return line;
    }


    public String getParentLine(String[] tokens) {
        String line = "";
        for (int i = 1; i < tokens.length; i++) {
            if (i == tokens.length - 1)
                line += tokens[i];
            else
                line += tokens[i] + " ";

        }
        return line.replace("-p", "");

    }


    public String toString() {
        return dir.getName();
    }
}



