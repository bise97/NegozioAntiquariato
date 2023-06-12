package boundary.utilsIO;

import java.util.Scanner;

public class TerminalIO {
    private static Scanner scanner = new Scanner(System.in);

    public static void resetScanner(){
        scanner = new Scanner(System.in);
    }

    public static String askUser(String print){
        System.out.println(print);
        return scanner.nextLine();
    }

    public static float askUserFloat(String print){
        String input;
        boolean notFloat = true;
        float result = -1;

        do{
            input = askUser(print);
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

    public static long askUserLong(String print){
        String input;
        boolean notFloat = true;
        long result = -1;

        do{
            input = askUser(print);
            try {
                result = Long.parseLong(input);
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
        boolean longerThan255 = true;

        do{
            input = askUser(print);

            if(input.length() > 255){
                System.out.println("Stringa lunga più di 255 caratteri!");
            }else{
                longerThan255 = false;
            }
        } while(longerThan255);

        return input;
    }
    public static int askUserInt(String print){
        String input;
        boolean notFloat = true;
        int result = -1;

        do{
            input = askUser(print);
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
