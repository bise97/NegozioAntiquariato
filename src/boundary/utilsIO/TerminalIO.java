package boundary.utilsIO;

import java.util.Scanner;

public class TerminalIO {
    private static Scanner scanner = new Scanner(System.in);

    public static String askUser(String print){
        System.out.println(print);
        return scanner.nextLine();
    }
}
