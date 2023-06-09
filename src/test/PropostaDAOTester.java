package test;

import database.ClienteDAO;
import database.ProdottoDAO;
import database.PropostaDAO;
import entity.*;

import java.io.File;
import java.util.ArrayList;

//TODO eliminare
public class PropostaDAOTester {

    public void runTest(){
        ArrayList<File> imgfiles = new ArrayList<>();
        imgfiles.add(new File("resources/lampada1.jpg"));
        imgfiles.add(new File("resources/lampada2.jpg"));
        imgfiles.add(new File("resources/lampada3.jpg"));
        imgfiles.add(new File("resources/lampada4.jpg"));
        Prodotto prodotto = new Prodotto("lampada","lampada nera con luce calda",imgfiles);
        Scultura scultura = new Scultura(prodotto,10.5f,3.6f);
        Dipinto dipinto = new Dipinto(prodotto,5.89f,3.12f, TecnicaDArte.ACQUERELLO);

        ProdottoDAO.createProdotto(prodotto);
        ProdottoDAO.createProdotto(scultura);
        ProdottoDAO.createProdotto(dipinto);
        System.out.println("PRODOTTI CREATI\n");

        Cliente cliente1 = new Cliente("bise","ciao","3333333333","366365354","Biagio","Salzillo","2025-01-12");
        Cliente cliente2 = new Cliente("gae", "hello", "80000000","48828329920230","Gaetano","martedi","2026-03-31");

        ClienteDAO.createCliente(cliente1);
        ClienteDAO.createCliente(cliente2);
        System.out.println("CLIENTI CREATI: "+cliente1.getUsername() + " " + cliente2.getUsername()+ "\n");

        Proposta propostaProdotto = new Proposta(10,cliente1.getUsername(),ProdottoDAO.readProdotto(prodotto.getCodice()).getCodice());
        Proposta propostaScultura = new Proposta(20, cliente1.getUsername(), ProdottoDAO.readProdotto(scultura.getCodice()).getCodice());
        Proposta propostaDipinto = new Proposta(15, cliente2.getUsername(), ProdottoDAO.readProdotto(dipinto.getCodice()).getCodice());

        System.out.println("Oggetti:");
        System.out.println(propostaProdotto);
        System.out.println(propostaDipinto);
        System.out.println(propostaScultura);

        PropostaDAO.createProposta(propostaProdotto);
        PropostaDAO.createProposta(propostaDipinto);
        PropostaDAO.createProposta(propostaScultura);

        System.out.println("Test readAll:");
        ArrayList<Proposta> proposte = PropostaDAO.readAll();
        for(Proposta p : proposte){
            System.out.println(p);
        }

        System.out.println("Test read singola:");
        System.out.println(PropostaDAO.readProposta(propostaProdotto.getId()));
        System.out.println(PropostaDAO.readProposta(propostaScultura.getId()));
        System.out.println(PropostaDAO.readProposta(propostaDipinto.getId()));


    }
}
