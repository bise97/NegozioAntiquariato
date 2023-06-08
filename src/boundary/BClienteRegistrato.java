package boundary;

import entity.Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BClienteRegistrato {
    private String username;

    public BClienteRegistrato(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
