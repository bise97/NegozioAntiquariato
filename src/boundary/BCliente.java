package boundary;

import control.GestioneNegozio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BCliente {


    public void login() {
        String user;
        String pass;
        GestioneNegozio gN = GestioneNegozio.getInstance();

        do {
            System.out.println("LOGIN:");
            user = askUser("username: ");
            pass = askUser("password: ");
        }
        while(!gN.login(user, pass));

            System.out.println("LOGIN: OK ");
            BClienteRegistrato bClienteRegistrato = new BClienteRegistrato(user);
            bClienteRegistrato.run();
    }

    private String askUser(String print){
        Scanner scanner = new Scanner(System.in);
        System.out.println(print);
        return scanner.nextLine();
    }
}
