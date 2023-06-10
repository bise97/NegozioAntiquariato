package database;

import entity.Cliente;
import java.sql.*;

public class ClienteDAO {


    private static Cliente deserializeCurrentRecord(ResultSet rs) throws SQLException {
        return new Cliente(rs.getString("username"),rs.getString("password"),rs.getString("telefono"),
                rs.getString("numeroCarta"), rs.getString("nomeIntestatario"),rs.getString("cognomeIntestatario"), rs.getString("dataScadenza"));
    }
    public static Cliente readCliente(String username){
        Cliente cliente = PersistanceContext.getInstance().getFromPersistanceContext(Cliente.class,username);
        if(cliente != null) return cliente;
        try {
            Connection conn = DBManager.getConnection();

            String query = "SELECT Cliente.username, Cliente.password, Cliente.telefono," +
                    "CartaDiCredito.numeroCarta, CartaDiCredito.NomeIntestatario, CartaDiCredito.cognomeIntestatario, CartaDiCredito.dataScadenza" +
                    " FROM Cliente RIGHT JOIN CartaDiCredito ON Cliente.numeroCarta=CartaDiCredito.numeroCarta WHERE Cliente.username = ?";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, username);

                ResultSet result = stmt.executeQuery();

                if(result.next()) {
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

                PersistanceContext.getInstance().putInPersistanceContext(cliente,cliente.getUsername());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
