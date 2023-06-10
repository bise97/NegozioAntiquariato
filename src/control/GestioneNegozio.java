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
        Prodotto prodotto;
        Cliente cliente;
        ArrayList<Long> listaProposteCliente;

        prodotto = switch (tipo) {
            case SCULTURA -> inserisciScultura(bR);
            case DIPINTO -> inserisciDipinto(bR);
            default -> inserisciProdotto(bR);
        };

        Proposta proposta = new Proposta(prezzoProposto, username, prodotto.getCodice());
        PropostaDAO.createProposta(proposta);
        //TODO aggiungere controllo cache

        cliente = ClienteDAO.readCliente(username);
        listaProposteCliente = cliente.getListaProposteCliente();
        listaProposteCliente.add(proposta.getId());
        System.out.println("Proposta aggiunta: " + proposta);
    }

    public Scultura inserisciScultura(BClienteRegistrato bR){
        String nome;
        String descrizione;
        ArrayList<File> pathImmagini;
        float peso;
        float altezza;

        ArrayList<Object> listaP= bR.inserisciProdotto();
        ArrayList<Object> listaS = bR.inserisciScultura();

        nome = (String) listaP.get(0);
        descrizione = (String) listaP.get(1);
        pathImmagini = (ArrayList<File>) listaP.get(2);
        peso = (float) listaS.get(0);
        altezza = (float) listaS.get(1);

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

        ArrayList<Object> listaP= bR.inserisciProdotto();
        ArrayList<Object> listaD = bR.inserisciDipinto();

        nome = (String) listaP.get(0);
        descrizione = (String) listaP.get(1);
        pathImmagini = (ArrayList<File>) listaP.get(2);
        tecnica = (TecnicaDArte) listaD.get(0);
        larghezzaTela = (float) listaD.get(1);
        altezzaTela = (float) listaD.get(2);

        Dipinto dipinto = new Dipinto(nome, descrizione,pathImmagini,altezzaTela,larghezzaTela,tecnica);
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

        Cliente cliente = ClienteDAO.readCliente(username);
        if(cliente != null) {
            if (cliente.getPassword().equals(password)) {
                f = true;
            }
        }

        return f;
    }

    public void visualizzaArticoli(BGestore bGestore){
        ArrayList<Articolo> articoli = ArticoloDAO.readAll();
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        for(Articolo articolo : articoli){
            prodotti.add(ProdottoDAO.readProdotto(articolo.getCodiceProdotto()));
        }
        bGestore.visualizzaArticoli(articoli,prodotti);
    }
    public void visualizzaProposteCliente(String username){
        ArrayList<Long> listaProposteCliente = PropostaDAO.readIdProposteOfCliente(username);

        for (Long id : listaProposteCliente) {
            Proposta p = PropostaDAO.readProposta(id);
            System.out.println(p + " --> " + ProdottoDAO.readProdotto(p.getCodice()).toString());
        }

    }
}
