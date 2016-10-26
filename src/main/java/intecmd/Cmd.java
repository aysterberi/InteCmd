package intecmd;

import intecmd.commands.*;


import java.util.ArrayList;
import java.util.Scanner;

public class Cmd {

    private Scanner scanner;
    private CurrentDirectory currentDirectory = new CurrentDirectory();

    public Cmd() {
        scanner = new Scanner(System.in);
    }

    public String[] tokenizeString(String line) {
        line += " ";
        ArrayList<String> tokens = new ArrayList<>();
        boolean quoted = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\"' || c == ' ' && !quoted) {
                if (c == '\"')
                    quoted = !quoted;
                if (!quoted && stringBuilder.length() > 0) {
                    tokens.add(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                }
            } else
                stringBuilder.append(c);
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    public void processInput() {
        while (true) {
            System.out.println(currentDirectory);
            System.out.print(">> ");
            String[] tokens = this.tokenizeString(scanner.nextLine());
            commandSwitch(tokens);
        }
    }

    protected void commandSwitch(String[] tokens) {
        switch (tokens[0]) {
            case "exit":
                exitCommand();
                return; // return to satisfy IDE
            case "echo":
                System.out.println(echoCommand(tokens));
                break;
            case "help":
                helpCommand();
                break;
            case "ls":
                new LSCommand(tokens, currentDirectory.toString());
                break;
            case "cd":
                new ChangeDirectoryCommand(tokens);
                break;
            case "grep":
                new GrepCommand(tokens);
                break;
            case "wc":
                WCCommand(tokens);
                break;
            case "cat":
                catCommand(tokens);
                break;
            case "mkdir":
                new MkdirCommand(tokens);
                break;
            default:
                System.out.println("Unrecognized command");
                break;

        }
    }

    private void catCommand(String[] tokens) {
        ConcatenateCommand cat = new ConcatenateCommand();
        cat.in(tokens);
    }

    private void WCCommand(String[] tokens) {
        WordCountCommand wc = new WordCountCommand();
        wc.in(tokens);
        try {
            System.out.print(wc.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void helpCommand() {
        System.out.println("Available commands:");
        System.out.println("'exit' - Exits the application");
        System.out.println("'echo' - Whatever input comes after the command gets printed back");
        System.out.println("'ls' - Shows all directories and files in the current directory");
        System.out.println("'grep' - Searches for matches of a string in a file or files");
        System.out.println("'cat' - ");
        System.out.println("'mkdir' - Creates a new directory");
        System.out.println("'cd - Change directory'");
        System.out.println("'wc' - Counts words");
    }

    public void exitCommand() {
        System.exit(0);
    }

    public String echoCommand(String[] tokens) {
        String result = "";
        for (int i = 1; i < tokens.length; i++) {
            if (i == tokens.length - 1)
                result += tokens[i];
            else
                result += tokens[i] + " ";
        }
        return result;
    }
}
