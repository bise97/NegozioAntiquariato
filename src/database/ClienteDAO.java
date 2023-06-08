package database;

import entity.CartaDiCredito;
import entity.Cliente;
import entity.Immagine;

import javax.imageio.ImageIO;
import java.sql.*;

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

                ResultSet result = stmt.executeQuery(query);

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

    public static void createCliente(Cliente cliente){
        CartaDiCreditoDAO.createCartaDiCreditoDAO(cliente.getCartaDiCredito());
        try{
            Connection conn = DBManager.getConnection();
            String query = "INSERT INTO CLIENTE(USERNAME, PASSWORD, TELEFONO, NUMEROCARTA) VALUES (?,?,?,?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                preparedStatement.setString(1, cliente.getUsername());
                preparedStatement.setString(2, cliente.getPassword());
                preparedStatement.setString(3,cliente.getNumTelefono());
                preparedStatement.setString(4,cliente.getCartaDiCredito().getNumeroCarta());
                //TODO createCartaDiCredito()
                preparedStatement.executeUpdate();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                DBManager.closeConnection();
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
