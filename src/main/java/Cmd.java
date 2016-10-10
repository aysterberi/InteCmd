import java.util.Scanner;

public class Cmd {

    private Scanner scanner;

    public Cmd() {
        scanner = new Scanner(System.in);
    }

    public String[] tokenizeString(String line) {
        return line.split(" ");
    }

    public Scanner getScanner() {
        return scanner;
    }
}
