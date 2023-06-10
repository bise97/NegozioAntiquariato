package boundary;

import control.GestioneNegozio;
import java.util.Scanner;

public class BCliente {


    public void login() {
        String user;
        String pass;
        GestioneNegozio gN = GestioneNegozio.getInstance();
        boolean repeatLogin = false;

        do {
            System.out.println("LOGIN");
            user = askUser("username: ");
            pass = askUser("password: ");
            if(!gN.login(user, pass)){
                repeatLogin = true;
                System.out.println("Username e password errati. Riprovare.");
            }
            else{
                repeatLogin = false;
            }
        }
        while(repeatLogin);

            System.out.println("LOGIN OK ");
            BClienteRegistrato bClienteRegistrato = new BClienteRegistrato(user);
            bClienteRegistrato.run();
    }

    private String askUser(String print){
        Scanner scanner = new Scanner(System.in);
        System.out.println(print);
        return scanner.nextLine();
    }
}
