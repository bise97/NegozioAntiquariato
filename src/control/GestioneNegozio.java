package control;

import database.ArticoloDAO;
import entity.Articolo;
import exception.OperationException;

public class GestioneNegozio {
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

    public boolean login(String username, String password) {

        boolean f = false;

        if (username.equals("bise") && password.equals("pass1")) {
            f = true;
        }
        return f;
    }
}
