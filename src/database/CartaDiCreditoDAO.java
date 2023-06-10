package database;

import entity.CartaDiCredito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartaDiCreditoDAO {
    static void createCartaDiCredito(CartaDiCredito carta){

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
                System.out.println(e.getMessage());
            } finally {
                DBManager.closeConnection();
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static CartaDiCredito readCartaDiCredito(String numeroCarta){
        CartaDiCredito carta = PersistanceContext.getInstance().getFromPersistanceContext(CartaDiCredito.class,numeroCarta);
        if(carta != null) return carta;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM CartaDiCredito WHERE numeroCarta = ?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setString(1,numeroCarta);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    carta = deserializeCurrentRecord(resultSet);
                    PersistanceContext.getInstance().putInPersistanceContext(carta,carta.getNumeroCarta());
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
        return carta;
    }

    private static CartaDiCredito deserializeCurrentRecord(ResultSet rs) throws SQLException {
        String numeroCarta = rs.getString("numeroCarta");
        String username = ClienteDAO.readUsernameCarta(numeroCarta);
        return new CartaDiCredito(numeroCarta,rs.getString("nomeIntestatario"), rs.getString("cognomeIntestatario"),rs.getString("dataScadenza"),username);
    }
}
