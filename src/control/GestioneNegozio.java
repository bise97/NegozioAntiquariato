package control;

import boundary.BGestore;
import boundary.BClienteRegistrato;
import database.ArticoloDAO;
import database.ProdottoDAO;
import entity.Articolo;
import entity.Prodotto;
import database.ClienteDAO;
import database.PropostaDAO;
import entity.*;
import exception.OperationException;
import java.util.ArrayList;
import java.io.File;

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

    public void modificaArticolo(long codiceArticolo, BGestore bGestore){
        Articolo articolo;
        try{
            articolo = ricercaArticolo(codiceArticolo);
        }
        catch(OperationException e){
            System.out.println(e.getMessage());
            return;
        }

        bGestore.aggiornaCampiArticolo(articolo);
        Prodotto prodotto = ProdottoDAO.readProdotto(articolo.getCodiceProdotto());
        bGestore.aggiornaCampiProdotto(prodotto);

        ProdottoDAO.updateProdotto(prodotto);
        ArticoloDAO.updateArticolo(articolo);
    }

    public void inserisciProposta(String username, String tipo, float prezzoProposto, BClienteRegistrato bR){
        Prodotto prodotto = null;
        Cliente cliente = null;
        ArrayList<Long> listaProposteCliente = new ArrayList<>();

        switch (tipo){
            case SCULTURA:
                 prodotto = inserisciScultura(bR);
                 break;
            case DIPINTO:
                prodotto = inserisciDipinto(bR);
                break;
            default:
                prodotto = inserisciProdotto(bR);
        }

        Proposta proposta = new Proposta(prezzoProposto, username, prodotto.getCodice());
        PropostaDAO.createProposta(proposta);
        //TODO aggiungere controllo cache

        cliente = ClienteDAO.readCliente(username);
        listaProposteCliente = cliente.getListaProposteCliente();
        listaProposteCliente.add(proposta.getId());
    }

    public Scultura inserisciScultura(BClienteRegistrato bR){
        float peso;
        float altezza;

        Prodotto prodotto = inserisciProdotto(bR);
        ArrayList<Object> lista = bR.inserisciScultura();

        peso = (float) lista.get(0);
        altezza = (float) lista.get(1);

        Scultura scultura = new Scultura(prodotto, peso, altezza);
        ProdottoDAO.createProdotto(scultura);
        return scultura;
    }

    public Dipinto inserisciDipinto(BClienteRegistrato bR){

        TecnicaDArte tecnica;
        float larghezzaTela;
        float altezzaTela;

        Prodotto prodotto = inserisciProdotto(bR);
        ArrayList<Object> lista = bR.inserisciDipinto();

        tecnica = (TecnicaDArte) lista.get(0);
        larghezzaTela = (float) lista.get(1);
        altezzaTela = (float) lista.get(2);

        Dipinto dipinto = new Dipinto(prodotto,altezzaTela,larghezzaTela,tecnica);
        ProdottoDAO.createProdotto(dipinto);
        return dipinto;
    }

    public Prodotto inserisciProdotto(BClienteRegistrato bR){
        String nome;
        String descrizione;
        ArrayList<File> pathImmagini;

        ArrayList<Object> lista = bR.inserisciProdotto();

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
            //TODO aggiustare la condizione di verifica dell'username e password
            f = true;
        }
        return f;
    }
}
