import java.util.Scanner;

public class Cmd {

    private Scanner scanner;

    public Cmd() {
        scanner = new Scanner(System.in);
    }

    public String[] tokenizeString(String line) {
        if (line.contains("'")) {
            String[] arr = line.split("'");
            String[] tokens = new String[line.split("'").length];
            String quote = "";
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].split(" ").length > 2) {
                    quote += "'" + arr[i] + "'";
                    tokens[i] = quote;
                } else
                    tokens[i] = arr[i].trim();
            }
            return tokens;
        }
        return line.split(" ");
    }

    public void processInput() {
        System.out.println("Type 'help' for a list of commands");
        while (true) {
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
                default:
                    System.out.println("Unrecognized command");
                    break;
            }
        }
    }

    public void helpCommand() {
        System.out.println("Available commands:");
        System.out.println("'exit' - Exits the application");
        System.out.println("'echo' - Whatever input comes after the command gets printed back");
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
