package intecmd.commands;

import java.io.*;

import intecmd.CommandInterface;
import intecmd.CurrentDirectory;


public class GrepCommand implements CommandInterface {

    public String[] tokens;
    private CurrentDirectory currentDirectory = new CurrentDirectory();

    public GrepCommand(String[] tokens) {
        this.tokens = tokens;
        if (tokens.length > 3)
            System.out.println(tooManyArguments());
        if (tokens[1].equals("help"))
            System.out.println(help());
        else {
            System.out.println(executeSearch(tokens));
        }
    }

    public String tooManyArguments() {
        return "Too many arguments, try again";
    }

    public String executeSearch(String[] tokens) {
        if (tokens.length == 2)
            return "Too few arguments, try again";
        File[] files = fileFinder(currentDirectory.toString());
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader;

        if (!tokens[2].endsWith(".txt"))
            return "Unsupported file format";
        if (files == null || files.length == 0)
            return "No files with that name";

        for (File f : files) {
            try {
                bufferedReader = new BufferedReader(new FileReader(f.getName()));
                String line = bufferedReader.readLine();
                while (line != null) {
                    if (line.contains(tokens[1])) {
                        stringBuilder.append(tokens[1]);
                    }
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        if (stringBuilder.toString().isEmpty())
            return "error message";
        return stringBuilder.toString().replace("\"", "").replace("'", "");
    }

    public String help() {
        return "test help";
    }

    public File[] fileFinder(String directory) {
        File dir = new File(directory);
        if (!this.tokens[2].startsWith("*"))
            return dir.listFiles((dir1, name) -> name.equals(this.tokens[2]));
        return dir.listFiles((dir1, name) -> name.endsWith(".txt"));
    }
}
