package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private final static String DB_PATH = "jdbc:h2:NegozioAntiquariato";
    private final static String DB_USERNAME = "";
    private final static String DB_PASSWORD = "";
    private static Connection connection = null;

    public static Connection getConnection()  throws SQLException {
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(DB_PATH,DB_USERNAME,DB_PASSWORD);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException{
        if(connection!=null){
            connection.close();
        }
    }
}
