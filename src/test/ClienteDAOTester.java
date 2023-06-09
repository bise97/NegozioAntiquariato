package test;

import database.ClienteDAO;
import database.ProdottoDAO;
import entity.Cliente;

public class ClienteDAOTester {

    public void runTest(){

        Cliente cliente1 = new Cliente("bise","ciao","3333333333","366365354","Biagio","Salzillo","2025-01-12");
        Cliente cliente2 = new Cliente("gae", "hello", "80000000","48828329920230","Gaetano","martedi","2026-03-31");

        System.out.println("Oggetti:");
        System.out.println(cliente1);
        System.out.println(cliente2);

        ClienteDAO.createCliente(cliente1);
        ClienteDAO.createCliente(cliente2);

        System.out.println("Test read singola:");
        System.out.println(ClienteDAO.readCliente(cliente1.getUsername()));
        System.out.println(ClienteDAO.readCliente(cliente2.getUsername()));


    }
}
