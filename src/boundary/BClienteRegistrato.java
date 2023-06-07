package boundary;

import entity.Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BClienteRegistrato {
    private static BClienteRegistrato bCR = null;
    private String username;

    static public BClienteRegistrato getInstance(){
        if(bCR == null){
            bCR = new BClienteRegistrato();
        }
        return  bCR;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
