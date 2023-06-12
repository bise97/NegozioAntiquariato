package control;

import boundary.BClienteRegistrato;
import boundary.BGestore;
import database.*;
import entity.*;
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
        Articolo articolo = null;
        BGestore bGestore = new BGestore();
        String prezzo = "0.5";
        String quantitaMagazzino = "10";
        String nome = "Cane";
        String descrizione = "Sierra";
        String vuoiModificareImmagini = "n";

        String[] fields = {prezzo,quantitaMagazzino,nome,descrizione,vuoiModificareImmagini};

        try{
            articolo = ArticoloDAO.readArticolo(codiceArticolo);
        }catch (DAOException | DAOConnectionException e){
            fail("Errore nella lettura dell'articolo dal database");
        }

        assertNotEquals(articolo,null); //test della pre-condizione

        prepareInput(fields);
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
        String username = "biagio";
        Cliente cliente = null;
        BClienteRegistrato bClienteRegistrato = new BClienteRegistrato(username);
        String tipo = "PRODOTTO";
        float prezzoProposto = 28.2F;
        String nome = "Lampada";
        String descrizione = "Lampada nera con luce calda";
        String numeroImmagini = "1";
        String pathImmagine = "resources/lampada1.jpg";
        Proposta proposta = null;
        Prodotto prodotto = null;
        Integer lenghtImg = null;

        String[] fields = {nome,descrizione,numeroImmagini,pathImmagine};

        try{
            cliente = ClienteDAO.readCliente(username);
        }catch (DAOException | DAOConnectionException e){
            fail("Errore nella lettura del cliente dal database");
        }

        assertNotEquals(cliente,null); //test della pre-condizione

        prepareInput(fields);
        GestioneNegozio.getInstance().inserisciProposta(username,tipo,prezzoProposto,bClienteRegistrato);

        try{
            proposta = PropostaDAO.readProposta(1L);
            prodotto = ProdottoDAO.readProdotto(proposta.getCodice());
            lenghtImg = ImmagineDAO.readImmaginiProdotto(prodotto.getCodice()).size();
        }catch (DAOException | DAOConnectionException e){
            fail("Errore nella lettura delle proposte dal database");
        }

        assertNotEquals(proposta,null);
        assertNotEquals(prodotto, null);
        assertFalse(prodotto instanceof Dipinto || prodotto instanceof Scultura);
        assertEquals(proposta.getPrezzo(),prezzoProposto);
        assertEquals(proposta.getUsername(),username);
        assertEquals(prodotto.getNome(),nome);
        assertEquals(prodotto.getDescrizione(),descrizione);
        assertEquals(lenghtImg,Integer.parseInt(numeroImmagini));
    }


    private void prepareInput(String[] fields){
        String data = "";
        for(String field : fields){
            data += field;
            data += System.lineSeparator();
        }
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }
}