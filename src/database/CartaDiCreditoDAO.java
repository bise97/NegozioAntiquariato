package database;

import entity.CartaDiCredito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CartaDiCreditoDAO {
    public static void createCartaDiCreditoDAO(CartaDiCredito carta){
        java.sql.Date sqlDate = new java.sql.Date(carta.getDataScadenza().getTime());

        try{
            Connection conn = DBManager.getConnection();
            String query = "INSERT INTO CartaDiCredito(numeroCarta, nomeIntestatario, cognomeIntestatario, dataScadenza) VALUES (?,?,?,?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                preparedStatement.setString(1, carta.getNumeroCarta());
                preparedStatement.setString(2, carta.getNomeIntestatario());
                preparedStatement.setString(3, carta.getCognomeIntestatario());
                preparedStatement.setDate(4, sqlDate);

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
