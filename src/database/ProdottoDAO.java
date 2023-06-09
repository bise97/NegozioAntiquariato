package database;

import entity.*;

import java.sql.*;
import java.util.ArrayList;

public class ProdottoDAO {

    public static void createProdotto(Prodotto prodotto){
        String query = "INSERT INTO Prodotto (nome, descrizione, tipo) VALUES (?,?,?)";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {

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
                if (resultSet.next()) {
                    long codice = resultSet.getLong("codice");
                    if (!resultSet.wasNull()) {
                        prodotto.setCodice(codice);
                        //DBManager.putInPersistanceContext(shipment, shipmentId);
                    }
                }
            }
        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile creare il prodotto!");
        }

        for(Immagine img : prodotto.getImmagini()){
            ImmagineDAO.createImmagine(img);
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
            String query = "SELECT * FROM Prodotto WHERE codice = ?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,codice);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    prodotto = deserializeCurrentRecord(resultSet);
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
        return prodotto;
    }

    public static ArrayList<Prodotto> readAll(){
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        try {
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Prodotto";

            try (Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()) {
                    prodotti.add(deserializeCurrentRecord(resultSet));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return prodotti;
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
            //TODO throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile aggiornare il prodotto!");
        }

        if(prodotto instanceof Dipinto){
            updateDipinto((Dipinto) prodotto);
        }else if(prodotto instanceof Scultura){
            updateScultura((Scultura) prodotto);
        }
    }

    private static void updateDipinto(Dipinto dipinto){
        String query = "UPDATE Prodotto SET tecnicaDArte=?, larghezzaTela=?, altezzaTela=?, tipo=? WHERE codice=?;";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query)) {

            ps.setString(1, dipinto.getTecnica().toString());
            ps.setFloat(2, dipinto.getLarghezzaTela());
            ps.setFloat(3, dipinto.getAltezzaTela());
            ps.setString(4,"DIPINTO");
            ps.setLong(5,dipinto.getCodice());

            ps.executeUpdate();

        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile aggiornare il dipinto!");
        }

    }

    private static void updateScultura(Scultura scultura){
        String query = "UPDATE Prodotto SET pesoScultura=?, altezzaScultura=?, tipo=? WHERE codice=?";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query)) {

            ps.setFloat(1, scultura.getPeso());
            ps.setFloat(2, scultura.getAltezza());
            ps.setString(3, "SCULTURA");
            ps.setLong(4, scultura.getCodice());
            ps.executeUpdate();

        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile aggiornare la scultura!");
        }
    }

    private static Prodotto deserializeCurrentRecord(ResultSet rs) throws SQLException{
        ArrayList<Immagine> immagini = ImmagineDAO.readImmaginiProdotto(rs.getLong("codice"));
        Prodotto prodotto = new Prodotto(rs.getLong("codice"), immagini, rs.getString("nome"), rs.getString("descrizione"));
        String tipo = rs.getString("tipo");
        if(tipo != null) {
            switch (tipo) {
                case "SCULTURA" -> prodotto = new Scultura(prodotto, rs.getFloat("pesoScultura"), rs.getFloat("altezzaScultura"));
                case "DIPINTO" -> prodotto = new Dipinto(prodotto, rs.getFloat("altezzaTela"), rs.getFloat("larghezzaTela"), TecnicaDArte.valueOf(rs.getString("tecnicaDArte")));
                default -> {
                }
            }
        }

        return prodotto;
    }
}