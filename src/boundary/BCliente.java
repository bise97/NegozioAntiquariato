package boundary;

import control.GestioneNegozio;
import static boundary.utilsIO.TerminalIO.askUser;

public class BCliente {


    public void login() {
        String user;
        String pass;
        GestioneNegozio gN = GestioneNegozio.getInstance();
        boolean repeatLogin;

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
}
