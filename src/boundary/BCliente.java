package boundary;

import control.GestioneNegozio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BCliente {


    public void login() {
        String user;
        String pass;
        GestioneNegozio gN = GestioneNegozio.getInstance();
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("username: ");
            user = bufferRead.readLine();
            System.out.println("password: ");
            pass = bufferRead.readLine();
            if (gN.login(user, pass)) {
                System.out.println(" Connesso come " + user);
            } else {
                System.out.println("Username o password errata");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
