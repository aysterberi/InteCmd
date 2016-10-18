package intecmd.commands;

import java.io.File;


public class MkdirCommand {

    private File dir;
    private CurrentDirectory currentDirectory = new CurrentDirectory();



    public MkdirCommand(String[] directory) {
        this.dir = new File(currentDirectory.toString());
        if (directory.length > 1) {
            switch (directory[1]) {
                case "-p":
                    this.dir = new File(getParentLine(directory));
                    createDirectory();
                    break;
                default:
                    this.dir = new File(getRegularLine(directory));
                    createDirectory();
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


    public String getRegularLine(String[] tokens) {
        String line = "";
        for (int i = 1; i < tokens.length; i++) {
            if (i == tokens.length - 1)
                line += tokens[i];
            else
                line += tokens[i] + " ";
        }
        return currentDirectory.toString() + "\\" + line;

    }

    public String getParentLine(String[] tokens) {
        String line = "";
        for (int i = 1; i < tokens.length; i++) {
            if (i == tokens.length - 1)
                line += tokens[i];
            if (line.contains("-p"))
                line = line.replaceFirst("-p", "");
            else
                line += tokens[i] + "";

        }
        return "\\" + line;

    }

    public String toString() {
        return dir.getName();
    }
}



