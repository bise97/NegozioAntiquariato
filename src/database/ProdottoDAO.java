package database;

import entity.Dipinto;
import entity.Immagine;
import entity.Prodotto;
import entity.Scultura;

import java.sql.*;
import java.util.ArrayList;

public class ProdottoDAO {

    public static void createProdotto(Prodotto prodotto){
        String query = "INSERT INTO Prodotto  (nome, descrizione, tipo) VALUES (?,?,?);";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query)) {

            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            if(prodotto instanceof Dipinto){
                ps.setString(3, "DIPINTO");
            }else if(prodotto instanceof Scultura) {
                    ps.setString(3, "SCULTURA");
                }else {
                    ps.setNull(3, Types.VARCHAR);
            }

            ps.executeUpdate();
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next() == true) {
                    Long codice = readLong(resultSet, "SCOPE_IDENTITY()");
                    if (codice != null) {
                        prodotto.setCodice(codice);
                        for(Immagine img : prodotto.getImmagini()){
                            ImmagineDAO.createImmagine(img);
                        }
                        //DBManager.putInPersistanceContext(shipment, shipmentId);
                    }
                }

            }
        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile aggiornare il prodotto!");
        }



        if(prodotto instanceof Dipinto){
            updateDipinto((Dipinto) prodotto);
        }else if(prodotto instanceof Scultura){
            updateScultura((Scultura) prodotto);
        }
    }


    public static Prodotto readProdotto(long codice){
        Prodotto prodotto = null;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Immagine WHERE codice=?;";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,codice);
                ResultSet resultSet = preparedStatement.executeQuery(query);
                if(resultSet.next()){
                    prodotto = deserializeCurrentRecord(resultSet);
                }else {
                    //TODO  alzare eccezione se l'immagine non è presente nel db
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
            finally {
                DBManager.closeConnection();
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return prodotto;
    }

    public static void updateProdotto(Prodotto prodotto){

        String query = "UPDATE Prodotto SET nome=?, descrizione=? WHERE codice=?;";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query)) {

            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setLong(3, prodotto.getCodice());
            ps.executeUpdate();

            for(Immagine img : prodotto.getImmagini()){
                ImmagineDAO.updateImmagine(img);
            }

        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile aggiornare il prodotto!");
        }

        if(prodotto instanceof Dipinto){
            updateDipinto((Dipinto) prodotto);
        }else if(prodotto instanceof Scultura){
            updateScultura((Scultura) prodotto);
        }
    }

    private static void updateDipinto(Dipinto dipinto){
        String query = "UPDATE Prodotto SET tecnicaDArte=?, larghezzaTela=?, altezzaTela=? WHERE codice=?;";

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
        String query = "UPDATE Prodotto SET pesoScultura=?, altezzaScultura=? WHERE codice=?;";

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

    private static Prodotto deserializeCurrentRecord(ResultSet rs) throws SQLException{
        ArrayList<Immagine> immagini = ImmagineDAO.readImmaginiProdotto(rs.getLong("codice"));
        Prodotto prodotto = new Prodotto(immagini, rs.getString("nome"), rs.getString("descrizione"));
        return prodotto;
    }
    private static Long readLong(ResultSet resultSet, String columnLabel) throws SQLException {
        long value = resultSet.getLong(columnLabel);
        if (resultSet.wasNull()) {
            return null;
        }
        return value;
    }
}
