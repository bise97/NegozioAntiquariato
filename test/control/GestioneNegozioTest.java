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

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GestioneNegozioTest {

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
        try{
            DBSetup.initialize();
        }
        catch (SQLException | DAOConnectionException | DAOException e){
            fail("Database non inizializzato");
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void modificaArticolo() {
        long codiceArticolo = 1L;
        BGestore bGestore = new BGestore();
        String prezzo = "0.5";
        String quantitaMagazzino = "10";
        String nome = "Cane";
        String descrizione = "Sierra";
        String vuoiModificareImmagini = "n";

        String[] fields = {prezzo,quantitaMagazzino,nome,descrizione,vuoiModificareImmagini};
        String data = "";

        for(String field : fields){
            data += field;
            data += System.lineSeparator();
        }


        Articolo articolo = null;
        try{
            articolo = ArticoloDAO.readArticolo(codiceArticolo);
        }catch (DAOException | DAOConnectionException e){
            fail("Errore nella lettura dell'articolo dal database");
        }

        assertNotEquals(articolo,null); //test della pre-condizione

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        GestioneNegozio.getInstance().modificaArticolo(codiceArticolo, bGestore);

        articolo = null;
        try{
            articolo = ArticoloDAO.readArticolo(codiceArticolo);
        }catch (DAOException | DAOConnectionException e){
            fail("Errore nella lettura dell'articolo dal database");
        }

        assertNotEquals(articolo,null);
        assertEquals(articolo.getPrezzo(),Float.parseFloat(prezzo));
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