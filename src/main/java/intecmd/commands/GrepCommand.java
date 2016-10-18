package intecmd.commands;

import java.io.*;
import intecmd.CommandInterface;
import intecmd.CurrentDirectory;


public class GrepCommand implements CommandInterface {

    String[] tokens;
    private CurrentDirectory currentDirectory = new CurrentDirectory();

    public GrepCommand(String[] tokens) {
        this.tokens = tokens;
        if (tokens.length > 3)
            tooManyArguments();
        if (tokens[1].equals("help"))
            help();
        else
            executeSearch(tokens);
    }

    public String tooManyArguments() {
        return "Too many arguments, try again";
    }

    public String executeSearch(String[] tokens) {
        File[] files = fileFinder(currentDirectory.toString());
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader;

        if (files.length == 0)
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
        if(stringBuilder.toString().isEmpty())
            return "error message";
        return stringBuilder.toString().replace("\"", "").replace("'", "");
    }

    @Override
    public void in(String[] data) {

    }

    @Override
    public String out() {
        return null;
    }

    @Override
    public String help() {
        return "test help";
    }

    public File[] fileFinder(String directory) {
        File dir = new File(directory);
        return dir.listFiles((dir1, name) -> name.equals(this.tokens[2]));
    }
}
