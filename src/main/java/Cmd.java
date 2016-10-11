import java.util.Scanner;

public class Cmd {

    private Scanner scanner;

    public Cmd() {
        scanner = new Scanner(System.in);
    }

    public String[] tokenizeString(String line) {
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
                default:
                    System.out.println("Unrecognized command");
                    break;
            }
        }
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
