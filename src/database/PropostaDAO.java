package database;

import entity.Proposta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PropostaDAO {


    public static void createProposta(Proposta proposta) {
        String query = "INSERT INTO Proposta(prezzo, stato, username, codice) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query)) {

            ps.setFloat(1, proposta.getPrezzo());
            ps.setString(2, proposta.getStato().toString());
            ps.setString(3, proposta.getUsername());
            ps.setLong(4, proposta.getCodice());

            ps.executeUpdate();
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    if (!resultSet.wasNull()) {
                        proposta.setId(id);
                        //DBManager.putInPersistanceContext(shipment, shipmentId);
                    }
                }
            }
        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile creare una nuova proposta!");
        }
    }
}
