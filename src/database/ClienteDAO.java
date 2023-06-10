package database;
import entity.CartaDiCredito;
import entity.Cliente;
import java.sql.*;

import static database.PropostaDAO.readIdProposteOfCliente;

public class ClienteDAO {


    private static Cliente deserializeCurrentRecord(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        String numeroCarta = rs.getString("numeroCarta");
        CartaDiCredito carta = CartaDiCreditoDAO.readCartaDiCredito(numeroCarta);

        Cliente cliente = new Cliente(username ,rs.getString("password"),rs.getString("telefono"), numeroCarta, carta.getNomeIntestatario(), carta.getCognomeIntestatario(), carta.getDataScadenza().toString());
        cliente.setListaProposteCliente(readIdProposteOfCliente(username));

        return cliente;
    }
    public static Cliente readCliente(String username){
        Cliente cliente = PersistanceContext.getInstance().getFromPersistanceContext(Cliente.class,username);
        if(cliente != null) return cliente;
        try {
            Connection conn = DBManager.getConnection();

//            String query = "SELECT Cliente.username, Cliente.password, Cliente.telefono," +
//                    "CartaDiCredito.numeroCarta, CartaDiCredito.NomeIntestatario, CartaDiCredito.cognomeIntestatario, CartaDiCredito.dataScadenza" +
//                    " FROM Cliente RIGHT JOIN CartaDiCredito ON Cliente.numeroCarta=CartaDiCredito.numeroCarta WHERE Cliente.username = ?";

            String query = "SELECT * FROM Cliente WHERE username=?";
            try {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet result = stmt.executeQuery();

                if(result.next()) {
                    cliente = deserializeCurrentRecord(result);
                    PersistanceContext.getInstance().putInPersistanceContext(cliente,cliente.getUsername());
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
        CartaDiCreditoDAO.createCartaDiCredito(cliente.getCartaDiCredito());
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

    public static String readUsernameCarta(String numeroCarta){
        String username = null;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Cliente WHERE numeroCarta = ?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setString(1,numeroCarta);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    username = resultSet.getString("username");
                }else {
                    //TODO  alzare eccezione se il prodotto non è presente nel db
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return username;
    }

}
