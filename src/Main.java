import boundary.BCliente;
import boundary.BClienteRegistrato;
import database.ClienteDAO;
import database.DBManager;
import database.ProdottoDAO;
import entity.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
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

        ArrayList<File> imgfiles = new ArrayList<>();
        imgfiles.add(new File("resources/lampada1.jpg"));
        imgfiles.add(new File("resources/lampada2.jpg"));
        imgfiles.add(new File("resources/lampada3.jpg"));
        imgfiles.add(new File("resources/lampada4.jpg"));
        Prodotto p = new Prodotto("lampada","lampada nera con luce calda",imgfiles);
        System.out.println(p);
        ProdottoDAO.createProdotto(p);


//        Cliente cliente1 = new Cliente("bise","ciao","3333333333","366365354","Biagio","Salzillo","2025-01-12");
//        Cliente cliente2 = new Cliente("gae", "hello", "80000000","48828329920230","Gaetano","martedi","2026-03-31");
//        ClienteDAO.createCliente(cliente1);
//        ClienteDAO.createCliente(cliente2);
//        System.out.println(ClienteDAO.readCliente("bise"));
        //TODO login

    }
}