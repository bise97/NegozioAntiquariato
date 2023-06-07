package database;

import entity.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    /*public Cliente readCliente(String username){
        Cliente cliente = null;
        try {
            Connection conn = DBManager.getConnection();

            String query = "SELECT * FROM CLIENTEREGISTRATO WHERE USERNAME=?;";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, username);

                ResultSet result = stmt.executeQuery();

                if(result.next()) {
                    cliente = new Cliente(result.getInt(1), result.getString(2), result.getString(3), email, result.getString(5));
                }
            }catch(SQLException e) {
                System.out.println("Errore lettura ClienteRegistrato"); //Gestire eccezione
            } finally {
                DBManager.closeConnection();
            }

        }catch(SQLException e) {
            System.out.println("Errore connessione database"); // gestire eccezione
        }



        return eC;


    }*/
}
