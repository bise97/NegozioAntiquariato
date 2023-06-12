package control;

import boundary.BClienteRegistrato;
import boundary.BGestore;
import boundary.utilsIO.ImmagineIO;
import database.*;
import entity.*;
import exception.DAOConnectionException;
import exception.DAOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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
            //codice 1L -> Prodotto
            //codice 2L -> Dipinto
            //codice 3L -> Scultura
        }
        catch (SQLException | DAOConnectionException | DAOException e){
            fail("Database non inizializzato");
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void modificaArticoloID2() {
        long codiceArticolo = 2L; //Dipinto
        BGestore bGestore = new BGestore();
        String prezzo = "5.2";
        String quantitaMagazzino = "10";
        String nome = "Guernica";
        String descrizione = "Quadro di Picasso";
        String modificareImmagini = "y";
        String pathImmagine = "resources/armadio1.jpg";
        String tecnicaDArte = "1"; //1 -> Pittura a olio
        String larghezzaTela = "30.2";
        String altezzaTela = "15.4";


        String[] fields = {prezzo,quantitaMagazzino,nome,descrizione,modificareImmagini,pathImmagine,"",
            tecnicaDArte,larghezzaTela,altezzaTela};

        try{
            Articolo articolo = ArticoloDAO.readArticolo(codiceArticolo);
            assertNotNull(articolo,"Articolo non presente nel database"); //test della pre-condizione
            Prodotto prodotto = ProdottoDAO.readProdotto(articolo.getCodiceProdotto());
            assertTrue(prodotto instanceof Dipinto, "Articolo non è un dipinto");
        }catch (DAOException | DAOConnectionException e){
            fail(e.getMessage());
        }

        prepareInput(fields);
        GestioneNegozio.getInstance().modificaArticolo(codiceArticolo, bGestore);

        try{
            Articolo articolo = ArticoloDAO.readArticolo(codiceArticolo);
            assertNotNull(articolo);
            assertEquals(articolo.getPrezzo(),Float.parseFloat(prezzo));
            assertEquals(articolo.getQuantitaMagazzino(),Integer.parseInt(quantitaMagazzino));

            Prodotto prodotto = ProdottoDAO.readProdotto(articolo.getCodiceProdotto());
            assertNotNull(prodotto);
            assertEquals(prodotto.getNome(),nome);
            assertEquals(prodotto.getDescrizione(),descrizione);
            try{
//                assertEquals(prodotto.getImmagini().get(0),new Immagine(new File(pathImmagine)));
                assertEquals(new Immagine(new File(pathImmagine)),new Immagine(new File(pathImmagine)));
            }catch (IOException e){
                fail("Impossibile aprire l'immagine");
            }

            assertTrue(prodotto instanceof Dipinto);
            assertEquals(((Dipinto) prodotto).getTecnica(),TecnicaDArte.values()[Integer.parseInt(tecnicaDArte)-1]);
            assertEquals(((Dipinto) prodotto).getLarghezzaTela(),Float.parseFloat(larghezzaTela));
            assertEquals(((Dipinto) prodotto).getAltezzaTela(),Float.parseFloat(altezzaTela));
        }catch (DAOException | DAOConnectionException e){
            fail("Errore nella lettura dell'articolo dal database");
        }


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