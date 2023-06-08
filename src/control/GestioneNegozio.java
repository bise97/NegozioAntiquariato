package control;

import boundary.BGestore;
import database.ArticoloDAO;
import database.ProdottoDAO;
import entity.Articolo;
import entity.Prodotto;
import exception.OperationException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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

    public void modificaArticolo(long codiceArticolo, BGestore bGestore){
        Articolo articolo;
        try{
            articolo = ricercaArticolo(codiceArticolo);
        }
        catch(OperationException e){
            System.out.println(e.getMessage());
            return;
        }

//        ArrayList<Field> fields= new ArrayList<>(List.of(articolo.getClass().getDeclaredFields()));
//        Predicate<Field> predicate = new Predicate<Field>() {
//            @Override
//            public boolean test(Field field) {
//                if ( field.getName().equals("codiceProdotto") ) return true;
//                return false;
//            }
//        };
//        fields.removeIf(predicate);
//        ArrayList<Object> oldValue = articolo.get(fields);
//        bGestore.aggiornaCampi(fields,oldValue);

        bGestore.aggiornaCampiArticolo(articolo);
        Prodotto prodotto = ProdottoDAO.readProdotto(articolo.getCodiceProdotto());
        bGestore.aggiornaCampiProdotto(prodotto);

        ProdottoDAO.updateProdotto(prodotto);
        ArticoloDAO.updateArticolo(articolo);
    }

    public boolean login(String username, String password) {

        boolean f = false;

        if (username.equals("bise") && password.equals("pass1")) {
            f = true;
        }
        return f;
    }
}
