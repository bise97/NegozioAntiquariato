package database;
import entity.CartaDiCredito;
import entity.Cliente;
import exception.DAOConnectionException;
import exception.DAOException;

import java.sql.*;

import static database.PropostaDAO.readIdProposteOfCliente;

public class ClienteDAO {


    private static Cliente deserializeCurrentRecord(ResultSet rs) throws SQLException, DAOException, DAOConnectionException {
        String username = rs.getString("username");
        String numeroCarta = rs.getString("numeroCarta");
        CartaDiCredito carta = CartaDiCreditoDAO.readCartaDiCredito(numeroCarta);

        Cliente cliente = new Cliente(username ,rs.getString("password"),rs.getString("telefono"), numeroCarta, carta.getNomeIntestatario(), carta.getCognomeIntestatario(), carta.getDataScadenza().toString());
        cliente.setListaProposteCliente(readIdProposteOfCliente(username));

        return cliente;
    }
    public static Cliente readCliente(String username) throws DAOException,DAOConnectionException{
        Cliente cliente = PersistanceContext.getInstance().getFromPersistanceContext(Cliente.class,username);
        if(cliente != null) return cliente;
        try {
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Cliente WHERE username=?";
            try {
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet result = stmt.executeQuery();

                if(result.next()) {
                    try {
                        cliente = deserializeCurrentRecord(result);
                        PersistanceContext.getInstance().putInPersistanceContext(cliente,cliente.getUsername());
                    } catch (DAOException | DAOConnectionException e) {
                        throw new RuntimeException(e);
                    }

                }
            }catch(SQLException e) {
                throw new DAOException("Errore lettura Cliente");
            }
        }catch(SQLException e) {
            throw new DAOConnectionException("Errore connesione Database");
        }

        return cliente;

    }

    public static void createCliente(Cliente cliente) throws DAOException,DAOConnectionException{
        try {
            CartaDiCreditoDAO.createCartaDiCredito(cliente.getCartaDiCredito());
        } catch (DAOException | DAOConnectionException e) {
            System.out.println(e.getMessage());
            return;
        }
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

    public static String readUsernameCarta(String numeroCarta) throws DAOException,DAOConnectionException {
        String username = null;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Cliente WHERE numeroCarta = ?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setString(1,numeroCarta);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    username = resultSet.getString("username");
                }
            }
            catch (SQLException e){
                throw new DAOException("Errore lettura numero carta in Cliente (username: "+ username + ")");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connessione Database");
        }
        return username;
    }

}
