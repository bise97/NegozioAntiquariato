package control;

import boundary.BGestore;
import database.ArticoloDAO;
import database.DBSetup;
import entity.Articolo;
import exception.DAOConnectionException;
import exception.DAOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GestioneNegozioTest {

    @BeforeAll
    static void beforeAll() {
        try{
            DBSetup.initialize();
        }
        catch (SQLException e){
            fail("Database non inizializzato");
        }

    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void modificaArticolo() {
        long codiceArticolo = 1L;
        BGestore bGestore = new BGestore();
        String prezzo = "0.5\n";
        String quantitaMagazzino = "10\n";
        String nome = "Cane\n";
        String descrizione = "Sierra\n";
        String vuoiModificareImmagini = "n\n";


        Articolo articolo = null;
        try{
            articolo = ArticoloDAO.readArticolo(codiceArticolo);
        }catch (DAOException | DAOConnectionException e){
            fail("Errore nella lettura dell'articolo dal database");
        }

        assertNotEquals(articolo,null); //test della pre-condizione

        GestioneNegozio.getInstance().modificaArticolo(codiceArticolo, bGestore);

        articolo = null;
        try{
            articolo = ArticoloDAO.readArticolo(codiceArticolo);
        }catch (DAOException | DAOConnectionException e){
            fail("Errore nella lettura dell'articolo dal database");
        }

        assertNotEquals(articolo,null);
        assertEquals(articolo.getPrezzo(),Integer.parseInt(prezzo));
        assertEquals(articolo.getQuantitaMagazzino(),Integer.parseInt(quantitaMagazzino));
    }

    @Test
    void inserisciProposta() {
    }

    @Test
    void inserisciScultura() {
    }

    @Test
    void inserisciDipinto() {
    }

    @Test
    void inserisciProdotto() {
    }
}