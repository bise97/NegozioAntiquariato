package database;

import entity.Dipinto;
import entity.Prodotto;
import entity.Scultura;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdottoDAO {

    public static void createProdotto(Prodotto prodotto){
        String query = "INSERT INTO Prodotto  (nome, descrizione) VALUES (?,?);";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query)) {

            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());

            ps.executeUpdate();
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next() == true) {
                    Long codice = readLong(resultSet, "SCOPE_IDENTITY()");
                    if (codice != null) {
                        prodotto.setCodice(codice);
                        //DBManager.putInPersistanceContext(shipment, shipmentId);
                    }
                }

                //CREATE IMG
            }

            if(prodotto instanceof Dipinto){
                updateDipinto((Dipinto) prodotto);
            }else if(prodotto instanceof Scultura){
                updateScultura((Scultura) prodotto);
            }

        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile aggiornare il prodotto!");
        }
    }


    //public static Prodotto readProdotto(long codice)

    public static void updateProdotto(Prodotto prodotto){

        String query = "UPDATE Prodotto SET nome=?, descrizione=? WHERE codice=?";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query)) {

            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setLong(3, prodotto.getCodice());
            ps.executeUpdate();

            if(prodotto instanceof Dipinto){
                updateDipinto((Dipinto) prodotto);
            }else if(prodotto instanceof Scultura){
                updateScultura((Scultura) prodotto);
            }

        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile aggiornare il prodotto!");
        }
    }

    private static void updateDipinto(Dipinto dipinto){
        String query = "UPDATE Prodotto SET tecnicaDArte=?, larghezzaTela=?, altezzaTela=? WHERE codice=?";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query)) {

            ps.setString(1, dipinto.getTecnica().toString());
            ps.setFloat(2, dipinto.getLarghezzaTela());
            ps.setFloat(3, dipinto.getAltezzaTela());
            ps.setLong(4,dipinto.getCodice());
            ps.executeUpdate();

        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile aggiornare il dipinto!");
        }

    }

    private static void updateScultura(Scultura scultura){
        String query = "UPDATE Prodotto SET pesoScultura=?, altezzaScultura=? WHERE codice=?";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query)) {

            ps.setFloat(1, scultura.getPeso());
            ps.setFloat(2, scultura.getAlezza());
            ps.setLong(3, scultura.getCodice());
            ps.executeUpdate();

        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile aggiornare la scultura!");
        }
    }

    private static Long readLong(ResultSet resultSet, String columnLabel) throws SQLException {
        long value = resultSet.getLong(columnLabel);
        if (resultSet.wasNull()) {
            return null;
        }
        return value;
    }
}
