package database;

import entity.*;
import exception.DAOConnectionException;
import exception.DAOException;
import exception.OperationException;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBSetup {
    private static void createTableCliente(Connection connection) throws SQLException{
        String query = "CREATE TABLE Cliente(" +
                "username VARCHAR(255) PRIMARY KEY," +
                "password VARCHAR(255) NOT NULL," +
                "telefono VARCHAR(255)," +
                "numeroCarta CHAR(16)," +
                "FOREIGN KEY (numeroCarta) REFERENCES CartaDiCredito(numeroCarta)" +
                ");";
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }
    private static void createTableCartaDiCredito(Connection connection)throws SQLException{
        String query = "CREATE TABLE CartaDiCredito(" +
                "numeroCarta CHAR(16) PRIMARY KEY," +
                "nomeIntestatario VARCHAR(255) NOT NULL," +
                "cognomeIntestatario VARCHAR(255) NOT NULL," +
                "dataScadenza DATE NOT NULL" +
                ");";
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }
    private static void createTableProposta(Connection connection)throws SQLException{
        String query = "CREATE TABLE Proposta(" +
                "id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "prezzo FLOAT NOT NULL," +
                "stato VARCHAR(255) NOT NULL," +
                "username VARCHAR(255) NOT NULL," +
                "codiceProdotto LONG NOT NULL," +
                "FOREIGN KEY (username) REFERENCES Cliente(username)," +
                "FOREIGN KEY (codiceProdotto) REFERENCES Prodotto(codice)" +
                ");";
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }

    private static void createTableProdotto(Connection connection)throws SQLException{
        String query = "CREATE TABLE Prodotto(" +
                "codice LONG NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "nome VARCHAR(255) NOT NULL," +
                "descrizione VARCHAR(255)," +
                "tipo VARCHAR(255)," +
                "tecnicaDArte VARCHAR(255)," +
                "larghezzaTela FLOAT," +
                "altezzaTela FLOAT," +
                "pesoScultura FLOAT," +
                "altezzaScultura FLOAT" +
                ");";
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }
    private static void createTableImmagine(Connection connection)throws SQLException{
        String query = "CREATE TABLE Immagine(" +
                "id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "blob BLOB NOT NULL," +
                "ProdottoCodice LONG NOT NULL," +
                "FOREIGN KEY (ProdottoCodice) REFERENCES Prodotto(codice)" +
                ");";
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }
    private static void createTableArticolo(Connection connection)throws SQLException{
        String query = "CREATE TABLE Articolo(" +
                "ProdottoCodice LONG NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "prezzo FLOAT NOT NULL," +
                "quantitaMagazzino INT NOT NULL," +
                "FOREIGN KEY (ProdottoCodice) REFERENCES Prodotto(codice)" +
                ");";
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }

    private static void dropTables(Connection connection) throws SQLException{
        String query = "DROP TABLE Proposta, Articolo, Immagine, Cliente, Prodotto, CartaDiCredito;";
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }
    public static void createTables(Connection connection) throws SQLException {
        createTableCartaDiCredito(connection);
        createTableProdotto(connection);
        createTableCliente(connection);
        createTableImmagine(connection);
        createTableArticolo(connection);
        createTableProposta(connection);
    }

    public static void fillTables() throws DAOException, DAOConnectionException {
        ArrayList<Prodotto> prodotti = generateProdotti();
        for (Prodotto p : prodotti) {
            ProdottoDAO.createProdotto(p);
        }
        ArrayList<Articolo> articoli = generateArticoli(prodotti);
        for (Articolo a : articoli) {
            ArticoloDAO.createArticolo(a);
        }
        ArrayList<Cliente> clienti = generateClienti();
        for (Cliente c : clienti) {
            ClienteDAO.createCliente(c);
        }

    }

    public static ArrayList<Prodotto> generateProdotti(){
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        ArrayList<File> imgfiles = new ArrayList<>();
        String[] imgPathsLampada = {"resources/lampada1.jpg","resources/lampada2.jpg","resources/lampada3.jpg","resources/lampada4.jpg"};
//        String[] imgPathsArmadio = {"resources/armadio1.jpg","resources/armadio2.jpg","resources/armadio3.jpg"};
        String[] imgPathsScultura = {"resources/scultura1.jpg","resources/scultura2.jpg"};
        String[] imgPathsDipinto = {"resources/dipinto1.jpg"};

        for(String path : imgPathsLampada){
            imgfiles.add(new File(path));
        }
        prodotti.add( new Prodotto("Lampada","Lampada nera con luce calda",imgfiles) );

//        imgfiles = new ArrayList<>();
//        for(String path : imgPathsArmadio){
//            imgfiles.add(new File(path));
//        }
//        prodotti.add( new Prodotto("Armadio","Armadio marrone",imgfiles) );

        imgfiles = new ArrayList<>();
        for(String path : imgPathsDipinto){
            imgfiles.add(new File(path));
        }
        prodotti.add( new Dipinto("Medusa","Medusa di Caravaggio",imgfiles,5.89f,3.12f, TecnicaDArte.ACQUERELLO) );

        imgfiles = new ArrayList<>();
        for(String path : imgPathsScultura){
            imgfiles.add(new File(path));
        }
        prodotti.add( new Scultura("Busto","Busto in marmo bianco",imgfiles,10.5f,3.6f) );

        return prodotti;
    }
    public static ArrayList<Articolo> generateArticoli(ArrayList<Prodotto> prodotti){
        ArrayList<Articolo> articoli = new ArrayList<>();
        float prezzo = 50.75f;
        int quantita = 3;
        for(Prodotto p : prodotti){
            try{
                articoli.add(new Articolo(prezzo,quantita,p.getCodice()));
            }
            catch (OperationException e){
                System.out.println(e.getMessage());
            }
            prezzo += 0.5f;
            quantita += 2;
        }
        return articoli;
    }

    public static ArrayList<Cliente> generateClienti(){
        ArrayList<Cliente> clienti = new ArrayList<>();
        clienti.add(new Cliente("biagio","biagio","3335544333",
                "0123456789ABCDEF","Biagio","Salzillo","2025-05-12"));
        clienti.add(new Cliente("gae","gae","3335544322",
                "FEDCBA9876543210","Gaetano","Martedi'","2026-07-13"));
        clienti.add(new Cliente("root","root","3335566822",
                "A1B2C3D4E5F6G7H8","Alfonso","Savastano","2023-10-20"));
        return clienti;
    }

    public static void initialize() throws SQLException, DAOException, DAOConnectionException {
        Connection connection = DBManager.getConnection();
        dropTables(connection);
        createTables(connection);
        fillTables();
        DBManager.closeConnection();
    }

}
