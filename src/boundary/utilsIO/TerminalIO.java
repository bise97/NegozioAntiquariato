package boundary.utilsIO;

import java.util.Scanner;

public class TerminalIO {
    public static String askUser(String print){
        Scanner scanner = new Scanner(System.in);
        System.out.println(print);
        return scanner.nextLine();
    }
}
