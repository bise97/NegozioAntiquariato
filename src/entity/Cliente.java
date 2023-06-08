package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Cliente {
    private String username;
    private String password;
    private String numTelefono;
    private CartaDiCredito cartaDiCredito;
    private ArrayList<Long> listaProposteCliente;

    public Cliente(String username, String password, String numTelefono, String numeroCarta, String nomeIntestatario, String cognomeIntestatario, String dataScadenza) {
        this.username = username;
        this.password = password;
        this.numTelefono = numTelefono;
        this.cartaDiCredito = new CartaDiCredito(numeroCarta, nomeIntestatario, cognomeIntestatario, dataScadenza, username);
    }

    @Override
    public String toString() {
        String print = "Username: "+username+
                "\nPassword: "+password+
                "\nTelefono: "+numTelefono+
                "\nCarta{\n"+cartaDiCredito.toString()+"}";
        return print;
    }

    public CartaDiCredito getCartaDiCredito() {
        return cartaDiCredito;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public void setCartaDiCredito(CartaDiCredito cartaDiCredito) {
        this.cartaDiCredito = cartaDiCredito;
    }

    public ArrayList<Long> getListaProposteCliente() {
        return listaProposteCliente;
    }

    public void setListaProposteCliente(ArrayList<Long> listaProposteCliente) {
        this.listaProposteCliente = listaProposteCliente;
    }
}
