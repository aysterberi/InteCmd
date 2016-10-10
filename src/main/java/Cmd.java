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
        while (true) {
            System.out.print(">> ");
            String[] tokens = this.tokenizeString(scanner.nextLine());
            switch (tokens[0]) {
                case "exit":
                    System.exit(1);
                case "echo":
                    for (int i = 1; i < tokens.length; i++)
                        System.out.print(tokens[i] + " ");
                    System.out.println();
                    break;
                default:
                    System.out.println("Unrecognized command");
                    break;
            }
        }
    }

    public void processInput(String mockInput) {

    }
}
