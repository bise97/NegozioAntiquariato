package test;

import database.CartaDiCreditoDAO;
import database.ClienteDAO;
import entity.Cliente;

public class CartaDiCreditoDAOTester {

    public void runTest(){

        Cliente cliente1 = new Cliente("bise","ciao","3333333333","366365354","Biagio","Salzillo","2025-01-12");
        Cliente cliente2 = new Cliente("gae", "hello", "80000000","48828329920230","Gaetano","Martedi","2026-03-31");

        ClienteDAO.createCliente(cliente1);
        ClienteDAO.createCliente(cliente2);

        System.out.println("Test read singola:");
        System.out.println(CartaDiCreditoDAO.readCartaDiCredito(cliente1.getCartaDiCredito().getNumeroCarta()));
        System.out.println(CartaDiCreditoDAO.readCartaDiCredito(cliente2.getCartaDiCredito().getNumeroCarta()));
    }
}
