package database;

import entity.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {


    private static Cliente deserializeCurrentRecord(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(rs.getString("username"),rs.getString("password"),rs.getString("telefono"),
                rs.getString("numeroCarta"), rs.getString("nomeIntestatario"),rs.getString("cognomeIntestatario"), rs.getString("dataScadenza"));
        return cliente;
    }
    public static Cliente readCliente(String username){
        Cliente cliente = null;
        try {
            Connection conn = DBManager.getConnection();

            String query = "SELECT Cliente.username, Cliente.password, Cliente.telefono," +
                    "CartaDiCredito.numeroCarta, CartaDiCredito.NomeIntestatario, CartaDiCredito.cognomeIntestatario, CartaDiCredito.dataScadenza" +
                    "FROM Cliente RIGHT JOIN CartaDiCredito ON Cliente.numeroCarta=CartaDiCredito.numeroCarta WHERE Cliente.username =?;";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, username);

                ResultSet result = stmt.executeQuery();

                if(result.next()) {
                    //cliente = new Cliente(username, result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7));
                    cliente = deserializeCurrentRecord(result);
                }
            }catch(SQLException e) {
                System.out.println("Errore lettura ClienteRegistrato"); //Gestire eccezione
            } finally {
                DBManager.closeConnection();
            }

        }catch(SQLException e) {
            System.out.println("Errore connessione database"); // gestire eccezione
        }

        return cliente;

    }
}
