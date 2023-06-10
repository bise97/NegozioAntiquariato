package test;

import database.ArticoloDAO;
import database.ProdottoDAO;
import entity.*;
import exception.DAOConnectionException;
import exception.DAOException;
import exception.OperationException;

import java.io.File;
import java.util.*;

public class ArticoloDAOTester {

    public static ArrayList<Articolo> createTestCase(){
        ArrayList<Articolo> testCases = new ArrayList<>();

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

        try{
            testCases.add(new Articolo(10.5f,10, prodotto.getCodice()));
            testCases.add(new Articolo(4.3f,6,scultura.getCodice()));
            testCases.add(new Articolo(5.6f,2, dipinto.getCodice()));
        }catch(OperationException e){
            System.out.println(e.getMessage());
        }

        return testCases;
    }

    public void testCreate(ArrayList<Articolo> testCases){
        try {
        System.out.println("----Test create----");
            for(Articolo articolo : testCases){

                ArticoloDAO.createArticolo(articolo);
            }
        }catch (DAOException | DAOConnectionException e) {
                System.out.println(e.getMessage());
        }
    }

    private void testReadAll(){
        try {
            System.out.println("----Test readAll----");
            ArrayList<Articolo> articoli = ArticoloDAO.readAll();
            for (Articolo articolo : articoli) {
                System.out.println(articolo);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void testRead(ArrayList<Articolo> testCases){
        try{
            System.out.println("----Test read----");
            for(Articolo testCase : testCases){
                Articolo a = ArticoloDAO.readArticolo(testCase.getCodiceProdotto());
                System.out.println(a);
            }
        }catch (DAOException | DAOConnectionException e){
            System.out.println(e.getMessage());
        }

    }

    private void testUpdate(ArrayList<Articolo> testCases){
        try {
            System.out.println("----Test update----");
            Random random = new Random();
            for (Articolo testCase : testCases) {
                testCase.setPrezzo(random.nextFloat(0, 20));
                testCase.setQuantitaMagazzino(random.nextInt(0, 20));
                ArticoloDAO.updateArticolo(testCase);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void testDelete(ArrayList<Articolo> testCases){
        try {
            System.out.println("----Test delete----");
            for (Articolo testCase : testCases) {
                ArticoloDAO.deleteArticolo(testCase.getCodiceProdotto());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void runTest(){
        ArrayList<Articolo> testCases = createTestCase();
        System.out.println("Test cases:");
        for(Articolo articolo : testCases){
            System.out.println(articolo);
        }

        testCreate(testCases);
        testRead(testCases);
        testUpdate(testCases);
        testReadAll();
        testDelete(testCases);
        testReadAll();
    }
}
