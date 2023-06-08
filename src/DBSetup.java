import database.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
                "id LONG PRIMARY KEY," +
                "prezzo FLOAT NOT NULL," +
                "stato VARCHAR(255) NOT NULL," +
                "username VARCHAR(255) NOT NULL," +
                "codice LONG NOT NULL," +
                "FOREIGN KEY (username) REFERENCES Cliente(username)," +
                "FOREIGN KEY (codice) REFERENCES Prodotto(codice)" +
                ");";
        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
    }

    private static void createTableProdotto(Connection connection)throws SQLException{
        String query = "CREATE TABLE Prodotto(" +
                "codice LONG PRIMARY KEY," +
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
                "id LONG PRIMARY KEY," +
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
                "ProdottoCodice LONG PRIMARY KEY," +
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
    public static void createTables() throws SQLException {
        Connection connection = DBManager.getConnection();
        dropTables(connection);
        createTableCartaDiCredito(connection);
        createTableProdotto(connection);
        createTableCliente(connection);
        createTableImmagine(connection);
        createTableArticolo(connection);
        createTableProposta(connection);
        DBManager.closeConnection();
    }
}
