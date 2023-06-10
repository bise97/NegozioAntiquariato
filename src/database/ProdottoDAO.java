package database;

import entity.*;
import exception.DAOConnectionException;
import exception.DAOException;

import java.sql.*;
import java.util.ArrayList;

public class ProdottoDAO {

    public static void createProdotto(Prodotto prodotto) throws DAOException,DAOConnectionException{
        String query = "INSERT INTO Prodotto (nome, descrizione, tipo) VALUES (?,?,?)";

        try {
            Connection conn = DBManager.getConnection();
            try{
                PreparedStatement ps = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
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
                ResultSet resultSet = ps.getGeneratedKeys();
                    if (resultSet.next()) {
                        long codice = resultSet.getLong("codice");
                        if (!resultSet.wasNull()) {
                            prodotto.setCodice(codice);
                            PersistanceContext.getInstance().putInPersistanceContext(prodotto,prodotto.getCodice());
                        }
                    }
            } catch (SQLException e){
                throw new DAOException("Errore creazione prodotto");
            }
        } catch (SQLException e) {
            throw new DAOConnectionException("Errore connesione Database");
        }

        for(Immagine img : prodotto.getImmagini()){
            try {
                ImmagineDAO.createImmagine(img);
                //TODO da vedere
            } catch (DAOException | DAOConnectionException e) {
                throw new RuntimeException(e);
            }
        }

        if(prodotto instanceof Dipinto){
            updateDipinto((Dipinto) prodotto);
        }else if(prodotto instanceof Scultura){
            updateScultura((Scultura) prodotto);
        }
    }


    public static Prodotto readProdotto(long codice) throws DAOException,DAOConnectionException{
        Prodotto prodotto = PersistanceContext.getInstance().getFromPersistanceContext(Prodotto.class,codice);
        if(prodotto != null) return prodotto;

        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Prodotto WHERE codice = ?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,codice);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    try {
                        prodotto = deserializeCurrentRecord(resultSet);
                        PersistanceContext.getInstance().putInPersistanceContext(prodotto, prodotto.getCodice());
                    } catch (DAOException | DAOConnectionException e) {
                        System.out.println(e.getMessage());
                    }
                }
                    //TODO testare cosa succede se inserisco un prodotto, un dipinto o una scultura in PersistanceObject
            }
            catch (SQLException e){
                throw new DAOException("Errore lettura prodotto");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione database");
        }
        return prodotto;
    }

    public static ArrayList<Prodotto> readAll() throws  DAOException,DAOConnectionException{
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        try {
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Prodotto";

            try (Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()) {
                    try {
                        Prodotto prodotto = deserializeCurrentRecord(resultSet);
                        prodotti.add(prodotto);
                        PersistanceContext.getInstance().putInPersistanceContext(prodotto,prodotto.getCodice());
                    } catch (DAOException | DAOConnectionException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura di tutti i prodotti");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
        return prodotti;
    }

    public static void updateProdotto(Prodotto prodotto) throws DAOException,DAOConnectionException{

        String query = "UPDATE Prodotto SET nome=?, descrizione=? WHERE codice=?;";

        try {
            Connection conn = DBManager.getConnection();

            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, prodotto.getNome());
                ps.setString(2, prodotto.getDescrizione());
                ps.setLong(3, prodotto.getCodice());
                ps.executeUpdate();
            }catch (SQLException e){
                throw new DAOException("Errore update prodotto");
            }
        } catch (SQLException e) {
            throw new DAOConnectionException("Errore connesione Database");
        }

        try {
            ImmagineDAO.deleteImmaginiOfProdotto(prodotto.getCodice());
        } catch (DAOException | DAOConnectionException e) {
            System.out.println(e.getMessage());
        }

        for(Immagine img : prodotto.getImmagini()){
            try {
                ImmagineDAO.createImmagine(img);
            } catch (DAOException | DAOConnectionException e) {
                System.out.println(e.getMessage());
            }
        }

        if(prodotto instanceof Dipinto){
            updateDipinto((Dipinto) prodotto);
        }else if(prodotto instanceof Scultura){
            updateScultura((Scultura) prodotto);
        }
    }

    private static void updateDipinto(Dipinto dipinto) throws DAOException, DAOConnectionException{
        String query = "UPDATE Prodotto SET tecnicaDArte=?, larghezzaTela=?, altezzaTela=?, tipo=? WHERE codice=?;";

        try {
            Connection conn = DBManager.getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, dipinto.getTecnica().toString());
                ps.setFloat(2, dipinto.getLarghezzaTela());
                ps.setFloat(3, dipinto.getAltezzaTela());
                ps.setString(4, "DIPINTO");
                ps.setLong(5, dipinto.getCodice());

                ps.executeUpdate();

            } catch (SQLException e) {
                throw new DAOException("Errore update dipinto");
            }
        }catch (SQLException e){
            throw new DAOConnectionException("Errore connesione database");
        }

    }

    private static void updateScultura(Scultura scultura) throws DAOException, DAOConnectionException{
        String query = "UPDATE Prodotto SET pesoScultura=?, altezzaScultura=?, tipo=? WHERE codice=?";
        try {
            Connection conn = DBManager.getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setFloat(1, scultura.getPeso());
                ps.setFloat(2, scultura.getAltezza());
                ps.setString(3, "SCULTURA");
                ps.setLong(4, scultura.getCodice());
                ps.executeUpdate();

            } catch (SQLException e) {
                throw new DAOException("Errore aggiornare scultura");
            }
        }catch (SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
    }

    private static Prodotto deserializeCurrentRecord(ResultSet rs) throws SQLException, DAOException, DAOConnectionException {
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