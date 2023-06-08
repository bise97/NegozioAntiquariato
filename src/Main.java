import boundary.BCliente;
import boundary.BClienteRegistrato;
import database.ClienteDAO;
import database.DBManager;
import entity.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        boolean f = true;
        String option;
        try{
            DBSetup.createTables();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        Cliente cliente1 = new Cliente("bise","ciao","3333333333","366365354","Biagio","Salzillo","12/01/2025");
        Cliente cliente2 = new Cliente("gae", "hello", "80000000","48828329920230","Gaetano","martedi","06/12/2026");
        ClienteDAO.createCliente(cliente1);
        ClienteDAO.createCliente(cliente2);
        //System.out.println(ClienteDAO.readCliente("bise"));
        //TODO login

    }
}