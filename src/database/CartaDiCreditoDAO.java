package database;

import entity.CartaDiCredito;
import exception.DAOConnectionException;
import exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartaDiCreditoDAO {
    static void createCartaDiCredito(CartaDiCredito carta) throws DAOException, DAOConnectionException {

        try{
            Connection conn = DBManager.getConnection();
            String query = "INSERT INTO CartaDiCredito(numeroCarta, nomeIntestatario, cognomeIntestatario, dataScadenza) VALUES (?,?,?,?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                preparedStatement.setString(1, carta.getNumeroCarta());
                preparedStatement.setString(2, carta.getNomeIntestatario());
                preparedStatement.setString(3, carta.getCognomeIntestatario());
                preparedStatement.setDate(4, carta.getDataScadenza());

                //TODO createCartaDiCredito()
                preparedStatement.executeUpdate();
                PersistanceContext.getInstance().putInPersistanceContext(carta,carta.getNumeroCarta());

            } catch (Exception e) {
                throw new DAOException("Errore scrittura carta di credito");
            } finally {
                DBManager.closeConnection();
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
    }

    public static CartaDiCredito readCartaDiCredito(String numeroCarta) throws DAOException,DAOConnectionException{
        CartaDiCredito carta = PersistanceContext.getInstance().getFromPersistanceContext(CartaDiCredito.class,numeroCarta);
        if(carta != null) return carta;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM CartaDiCredito WHERE numeroCarta = ?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setString(1,numeroCarta);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    try {
                        carta = deserializeCurrentRecord(resultSet);
                        PersistanceContext.getInstance().putInPersistanceContext(carta,carta.getNumeroCarta());
                    }catch (DAOConnectionException | DAOException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
            catch (SQLException e){
                throw new DAOException("Errore lettura carta di credito");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione carta di credito");
        }
        return carta;
    }

    private static CartaDiCredito deserializeCurrentRecord(ResultSet rs) throws SQLException, DAOException, DAOConnectionException {
        String numeroCarta = rs.getString("numeroCarta");
        String username = ClienteDAO.readUsernameCarta(numeroCarta);
        return new CartaDiCredito(numeroCarta,rs.getString("nomeIntestatario"), rs.getString("cognomeIntestatario"),rs.getString("dataScadenza"),username);
    }
}
