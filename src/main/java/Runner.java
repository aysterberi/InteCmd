
public class Runner {

    public static void main(String[] args) {
        Cmd cmd = new Cmd();

        while (true) {
            System.out.print(">> ");
            String[] str = cmd.tokenizeString(cmd.getScanner().nextLine());
            switch (str[0]) {
                case "exit":
                    System.exit(1);
                case "echo":
                    for (int i = 1; i < str.length; i++)
                        System.out.print(str[i] + " ");
                    System.out.println();
                    break;
                default:
                    System.out.println("Unrecognized command");
                    break;
            }
        }
    }
}
