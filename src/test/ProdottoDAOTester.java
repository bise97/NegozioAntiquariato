package test;

import database.ProdottoDAO;
import entity.Dipinto;
import entity.Prodotto;
import entity.Scultura;
import entity.TecnicaDArte;
import java.io.File;
import java.util.ArrayList;
//TODO eliminare questa classe prima della consegna
public class ProdottoDAOTester {

    public void runTest(){
        ArrayList<File> imgfiles = new ArrayList<>();
        imgfiles.add(new File("resources/lampada1.jpg"));
        imgfiles.add(new File("resources/lampada2.jpg"));
        imgfiles.add(new File("resources/lampada3.jpg"));
        imgfiles.add(new File("resources/lampada4.jpg"));
        Prodotto prodotto = new Prodotto("lampada","lampada nera con luce calda",imgfiles);
        Scultura scultura = new Scultura(prodotto,10.5f,3.6f);
        Dipinto dipinto = new Dipinto(prodotto,5.89f,3.12f, TecnicaDArte.ACQUERELLO);
        System.out.println("Oggetti:");
        System.out.println(prodotto);
        System.out.println(scultura);
        System.out.println(dipinto);

        ProdottoDAO.createProdotto(prodotto);
        ProdottoDAO.createProdotto(scultura);
        ProdottoDAO.createProdotto(dipinto);

        System.out.println("Test readAll:");
        ArrayList<Prodotto> prodotti = ProdottoDAO.readAll();
        for(Prodotto prod : prodotti){
            System.out.println(prod);
        }

        System.out.println("Test read singola:");
        System.out.println(ProdottoDAO.readProdotto(prodotto.getCodice()));
        System.out.println(ProdottoDAO.readProdotto(scultura.getCodice()));
        System.out.println(ProdottoDAO.readProdotto(dipinto.getCodice()));

        System.out.println("Test update:");
        prodotto.setNome("lampadario");
//        prodotto.setCodice(10L);   //cosa succede se cambio il codice di un prodotto?
        dipinto.setTecnica(TecnicaDArte.PITTURA_A_TEMPERA);
        scultura.setPeso(100.4f);
        ProdottoDAO.updateProdotto(prodotto);
        ProdottoDAO.updateProdotto(dipinto);
        ProdottoDAO.updateProdotto(scultura);
        prodotti = ProdottoDAO.readAll();
        for(Prodotto prod : prodotti){
            System.out.println(prod);
        }

    }
}
