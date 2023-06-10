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
import exception.DAOConnectionException;
import exception.DAOException;
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
    private Articolo ricercaArticolo(long codiceArticolo) throws OperationException, DAOException, DAOConnectionException {
        Articolo a = null;
        a = ArticoloDAO.readArticolo(codiceArticolo);
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
        catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }

        try{
            bGestore.aggiornaCampiArticolo(articolo);
            Prodotto prodotto = ProdottoDAO.readProdotto(articolo.getCodiceProdotto());
            bGestore.aggiornaCampiProdotto(prodotto);

            ProdottoDAO.updateProdotto(prodotto);
            ArticoloDAO.updateArticolo(articolo);
        } catch (DAOConnectionException | DAOException e) {
            System.out.println(e.getMessage());;
        }
    }

    public void inserisciProposta(String username, String tipo, float prezzoProposto, BClienteRegistrato bR){
        Prodotto prodotto;
        Cliente cliente;
        ArrayList<Long> listaProposteCliente;

        try {
            prodotto = switch (tipo) {
                case SCULTURA -> inserisciScultura(bR);
                case DIPINTO -> inserisciDipinto(bR);
                default -> inserisciProdotto(bR);
            };

            Proposta proposta = new Proposta(prezzoProposto, username, prodotto.getCodice());
            PropostaDAO.createProposta(proposta);

            cliente = ClienteDAO.readCliente(username);
            listaProposteCliente = cliente.getListaProposteCliente();
            listaProposteCliente.add(proposta.getId());
            System.out.println("Proposta aggiunta: " + proposta);
            //TODO aggiungere controllo cache
        }catch (DAOException | DAOConnectionException e){
            System.out.println(e.getMessage());
        }

    }

    public Scultura inserisciScultura(BClienteRegistrato bR) throws DAOException, DAOConnectionException {
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

    public Dipinto inserisciDipinto(BClienteRegistrato bR) throws DAOException, DAOConnectionException {
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

    public Prodotto inserisciProdotto(BClienteRegistrato bR) throws DAOException, DAOConnectionException {
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

        Cliente cliente = null;
        try {
            cliente = ClienteDAO.readCliente(username);
            if(cliente != null) {
                if (cliente.getPassword().equals(password)) {
                    f = true;
                }
            }
        } catch (DAOException | DAOConnectionException e) {
            System.out.println(e.getMessage());
        }
        return f;
    }

    public void visualizzaArticoli(BGestore bGestore){
        try {
            ArrayList<Articolo> articoli = ArticoloDAO.readAll();
            ArrayList<Prodotto> prodotti = new ArrayList<>();
            for (Articolo articolo : articoli) {
                prodotti.add(ProdottoDAO.readProdotto(articolo.getCodiceProdotto()));
            }
            bGestore.visualizzaArticoli(articoli, prodotti);
        }catch (DAOException | DAOConnectionException e){
            System.out.println(e.getMessage());
        }
    }
    public void visualizzaProposteCliente(String username){
        try {
            ArrayList<Long> listaProposteCliente = PropostaDAO.readIdProposteOfCliente(username);

            for (Long id : listaProposteCliente) {
                try {
                    Proposta p = PropostaDAO.readProposta(id);
                    System.out.println(p + " --> " + ProdottoDAO.readProdotto(p.getCodice()).toString());
                } catch (DAOException | DAOConnectionException e) {
                    System.out.println(e.getMessage());
                }
            }
        }catch (DAOException | DAOConnectionException e){
            System.out.println(e.getMessage());
        }

    }
}
