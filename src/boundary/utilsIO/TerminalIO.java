package boundary.utilsIO;

import java.util.Scanner;

public class TerminalIO {
    private static Scanner scanner = new Scanner(System.in);

    public static String askUser(String print){
        System.out.println(print);
        return scanner.nextLine();
    }

    public static float askUserFloat(String print){
        String input;
        boolean notFloat = true;
        float result = -1;

        do{
            System.out.println(print);
            input = scanner.nextLine();
            try {
                result = Float.parseFloat(input);
                if(result > 0) notFloat = false;
                else System.out.println("Valore inserito non corretto");
            }catch (NumberFormatException e){
                System.out.println("Valore inserito non corretto");
            }
        } while(notFloat);

        return result;
    }

    public static String askUserString255(String print){
        String input;
        boolean notFloat = true;

        do{
            System.out.println(print);
            input = scanner.nextLine();
            if(input.length() > 255){
                System.out.println("Stringa lunga più di 255 caratteri!");
            }else{
                notFloat = false;
            }
        } while(notFloat);

        return input;
    }
    public static int askUserInt(String print){
        String input;
        boolean notFloat = true;
        int result = -1;

        do{
            System.out.println(print);
            input = scanner.nextLine();
            try {
                result = Integer.parseInt(input);
                if(result >= 0) notFloat = false;
                else System.out.println("Valore inserito non corretto");
            }catch (NumberFormatException e){
                System.out.println("Valore inserito non corretto");
            }
        } while(notFloat);

        return result;
    }
}
