package intecmd;

import intecmd.commands.ChangeDirectory;
import intecmd.commands.Concatenate;
import intecmd.commands.LSCommand;
import intecmd.commands.WordCount;

import java.util.ArrayList;
import java.util.Arrays;
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
            if(c == '\"' || c == ' ' && !quoted) {
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
            switch (tokens[0]) {
                case "exit":
                    exitCommand();
                case "echo":
                    System.out.println(echoCommand(tokens));
                    break;
                case "help":
                    helpCommand();
                    break;
                case "ls":
                    new LSCommand(tokens, currentDirectory);
                    break;
                case "cd":
                    new ChangeDirectory(tokens);
                    break;
	            case "wc":
	            	WCCommand(tokens);
		            break;
                case "cat":
                    catCommand(tokens);
                    break;
                default:
                    System.out.println("Unrecognized command");
                    break;
            }
        }
    }
    private void catCommand(String[] tokens) {
        Concatenate cat = new Concatenate();
        cat.in(tokens);
    }
	private void WCCommand(String[] tokens) {
		WordCount wc = new WordCount();
		wc.in(tokens);
		try {
			System.out.print(wc.call());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void lsCommand() {
		// . is OS-independent (UNIX/ Windows) alias for "current directory" from the OS.
		LSCommand ls = new LSCommand(".");
		System.out.println(Arrays.toString(ls.getDirectories().toArray()));
		System.out.println(Arrays.toString(ls.getFiles().toArray()));
	}

    public void helpCommand() {
        System.out.println("Available commands:");
        System.out.println("'exit' - Exits the application");
        System.out.println("'echo' - Whatever input comes after the command gets printed back");
        System.out.println("'ls' - Shows all directories and files in the current directory");
        System.out.println();
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
