package control;

import boundary.BGestore;
import boundary.BClienteRegistrato;
import boundary.utilsIO.TerminalIO;
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
    private static final String PRODOTTO = "PRODOTTO";
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
        Articolo a = ArticoloDAO.readArticolo(codiceArticolo);
        if(a == null){
            throw new OperationException("Articolo non trovato.");
        }
        return a;
    }

    public void modificaArticolo(long codiceArticolo, BGestore bGestore) throws DAOException, DAOConnectionException, OperationException {
        Articolo articolo = ricercaArticolo(codiceArticolo);

        bGestore.aggiornaCampiArticolo(articolo);
        Prodotto prodotto = ProdottoDAO.readProdotto(articolo.getCodiceProdotto());
        bGestore.aggiornaCampiProdotto(prodotto);
        ProdottoDAO.updateProdotto(prodotto);
        ArticoloDAO.updateArticolo(articolo);
    }

    public void inserisciProposta(String username, String tipo, float prezzoProposto, BClienteRegistrato bR) throws OperationException{
        Prodotto prodotto;
        Cliente cliente;
        ArrayList<Long> listaProposteCliente;

        if (prezzoProposto < 0) {
            throw new OperationException("Impossibile inserire la proposta, prezzo proposto non consentito.");
        }
        try {

            if (ClienteDAO.readCliente(username) == null){
                throw new OperationException("Username non registrato!");
            }
        }catch (DAOException | DAOConnectionException e){
            System.out.println(e.getMessage());
        }

        try {
                switch (tipo) {
                    case SCULTURA -> {
                        prodotto = inserisciScultura(bR);
                    }
                    case DIPINTO -> {
                        prodotto = inserisciDipinto(bR);
                    }
                    case PRODOTTO -> {
                        prodotto = inserisciProdotto(bR);
                    }
                    default -> {
                        throw new OperationException("Impossibile inserire la proposta, tipo non consentito");
                    }
            }

            Proposta proposta = new Proposta(prezzoProposto, username, prodotto.getCodice());
            PropostaDAO.createProposta(proposta);

            cliente = ClienteDAO.readCliente(username);
            listaProposteCliente = cliente.getListaProposteCliente();
            listaProposteCliente.add(proposta.getId());
            //TODO aggiungere controllo cache
        }catch (DAOException | DAOConnectionException e){
            System.out.println(e.getMessage());
        }

    }

    private Scultura inserisciScultura(BClienteRegistrato bR) throws DAOException, DAOConnectionException {
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

    private Dipinto inserisciDipinto(BClienteRegistrato bR) throws DAOException, DAOConnectionException {
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

    private Prodotto inserisciProdotto(BClienteRegistrato bR) throws DAOException, DAOConnectionException {
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

        Cliente cliente;
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

    public void visualizzaArticoli(BGestore bGestore) throws DAOException, DAOConnectionException {
        ArrayList<Articolo> articoli = ArticoloDAO.readAll();
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        for (Articolo articolo : articoli) {
            prodotti.add(ProdottoDAO.readProdotto(articolo.getCodiceProdotto()));
        }
        bGestore.visualizzaArticoli(articoli, prodotti);
    }
    public ArrayList<Proposta> visualizzaProposteCliente(String username){
        ArrayList<Proposta> proposte = new ArrayList<>();
        try {
            ArrayList<Long> listaProposteCliente = PropostaDAO.readIdProposteOfCliente(username);

            for (Long id : listaProposteCliente) {
                try {
                    Proposta p = PropostaDAO.readProposta(id);
                    proposte.add(p);
                } catch (DAOException | DAOConnectionException e) {
                    System.out.println(e.getMessage());
                }
            }
        }catch (DAOException | DAOConnectionException e){
            System.out.println(e.getMessage());
        }
        return proposte;
    }
}
