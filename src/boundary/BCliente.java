package boundary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BCliente {

    private static BCliente bC = null;


    public static BCliente getInstance() {
        if (bC == null) {
            bC = new BCliente();
        }
        return bC;
    }

    public void login() {
        String user;
        String pass;
        BGestore bG = BGestore.getInstance();
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("username: ");
            user = bufferRead.readLine();
            System.out.println("password: ");
            pass = bufferRead.readLine();
            if (bG.login(user, pass)) {
                System.out.println(" Connesso come " + user);
            } else {
                System.out.println("Username o password errata");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
