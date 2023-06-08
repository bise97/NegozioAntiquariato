package control;

import boundary.BClienteRegistrato;
import database.ArticoloDAO;
import database.ProdottoDAO;
import database.PropostaDAO;
import entity.*;
import exception.OperationException;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class GestioneNegozio {

    private static final String SCULTURA = "SCULTURA";
    private static final String DIPINTO = "DIPINTO";
    private static GestioneNegozio gestioneNegozio = null;
    protected GestioneNegozio(){
        super();
    }
    public static GestioneNegozio getInstance(){
        if(gestioneNegozio == null){
            gestioneNegozio = new GestioneNegozio();
        }
        return gestioneNegozio;
    }
    private Articolo ricercaArticolo(long codiceArticolo) throws OperationException{
        Articolo a = ArticoloDAO.readArticolo(codiceArticolo);
        if(a == null){
            throw new OperationException("Articolo non trovato.");
        }
        return a;
    }

    public void modificaArticolo(long codiceArticolo){
        Articolo articolo = null;
        try{
            articolo = ricercaArticolo(codiceArticolo);
        }
        catch(OperationException e){
            System.out.println(e.getMessage());
        }


    }

    public void inserisciProposta(String username, String tipo, float prezzoProposto, BClienteRegistrato bR){
        Prodotto prodotto = null;
        switch (tipo){
            case SCULTURA:
                 prodotto = inserisciScultura(bR);
            case DIPINTO:
                prodotto = inserisciDipinto(bR);
            default:
                prodotto = inserisciProdotto(bR);
        }

        Proposta proposta = new Proposta(prezzoProposto, username, prodotto.getCodice());
        PropostaDAO.createProposta(proposta);
        //lista proposte cliente
    }

    public Scultura inserisciScultura(BClienteRegistrato bR){
        String nome;
        String descrizione;
        ArrayList<File> pathImmagini;
        float peso;
        float altezza;

        ArrayList<Object> lista = bR.inserisciScultura();
        nome = (String) lista.get(0);
        descrizione = (String) lista.get(1);
        pathImmagini = (ArrayList<File>) lista.get(2);
        peso = (float) lista.get(3);
        altezza = (float) lista.get(4);

        Scultura scultura = new Scultura(nome, descrizione,pathImmagini, peso, altezza);
        ProdottoDAO.createProdotto(scultura);
        return scultura;
    }

    public Dipinto inserisciDipinto(BClienteRegistrato bR){
        String nome;
        String descrizione;
        ArrayList<File> pathImmagini;
        TecnicaDArte tecnica;
        float larghezzaTela;
        float altezzaTela;

        ArrayList<Object> lista = bR.inserisciDipinto();
        nome = (String) lista.get(0);
        descrizione = (String) lista.get(1);
        pathImmagini = (ArrayList<File>) lista.get(2);
        tecnica = (TecnicaDArte) lista.get(3);
        larghezzaTela = (float) lista.get(4);
        altezzaTela = (float) lista.get(5);

        Dipinto dipinto = new Dipinto(nome, descrizione,pathImmagini,altezzaTela,larghezzaTela,tecnica);
        ProdottoDAO.createProdotto(dipinto);
        return dipinto;
    }

    public Prodotto inserisciProdotto(BClienteRegistrato bR){
        String nome;
        String descrizione;
        ArrayList<File> pathImmagini;

        ArrayList<Object> lista = bR.inserisciDipinto();
        nome = (String) lista.get(0);
        descrizione = (String) lista.get(1);
        pathImmagini = (ArrayList<File>) lista.get(2);

        Prodotto prodotto = new Prodotto(nome, descrizione,pathImmagini);
        ProdottoDAO.createProdotto(prodotto);
        return prodotto;
    }

    public boolean login(String username, String password) {

        boolean f = false;

        if (username.equals("bise") && password.equals("pass1")) {
            f = true;
        }
        return f;
    }
}
