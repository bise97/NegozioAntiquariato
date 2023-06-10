package database;

import entity.Articolo;
import exception.DAOConnectionException;
import exception.DAOException;
import exception.OperationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ArticoloDAO {
    private static final String PREZZO_COLUMN = "prezzo";
    private static final String QUANTITA_COLUMN = "quantitaMagazzino";
    private static final String CODICE_COLUMN = "ProdottoCodice";
    private static Articolo deserializeRecordArticolo(ResultSet rs){
        Articolo articolo = null;
        try{
            articolo = new Articolo(rs.getFloat("prezzo"),
                    rs.getInt("quantitaMagazzino"),
                    rs.getLong("ProdottoCodice"));
        }
        catch (SQLException | OperationException e){
            System.out.println(e.getMessage());
        }
        return articolo;
    }
    public static void createArticolo(Articolo articolo) throws DAOException, DAOConnectionException {
        try{
            Connection conn = DBManager.getConnection();
            String query = "INSERT INTO Articolo VALUES(?,?,?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setLong(1, articolo.getCodiceProdotto());
                preparedStatement.setFloat(2, articolo.getPrezzo());
                preparedStatement.setInt(3, articolo.getQuantitaMagazzino());
                preparedStatement.executeUpdate();
                PersistanceContext.getInstance().putInPersistanceContext(articolo,articolo.getCodiceProdotto());
            } catch (SQLException e) {
                throw new DAOException("Errore scrittura Articolo");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
    }

    public static Articolo readArticolo(long codiceArticolo){
        Articolo articolo = PersistanceContext.getInstance().getFromPersistanceContext(Articolo.class,codiceArticolo);
        if(articolo != null) return articolo;

    public static Articolo readArticolo(long codiceArticolo) throws DAOException, DAOConnectionException{
        Articolo articolo = null;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Articolo WHERE ProdottoCodice = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setLong(1, codiceArticolo);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    articolo = deserializeRecordArticolo(resultSet);
                    PersistanceContext.getInstance().putInPersistanceContext(articolo,articolo.getCodiceProdotto());
                } else {
                    //TODO alzare eccezione se l'articolo non è presente nel db
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura Articolo");
            }

        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
        return articolo;
    }

    public static void updateArticolo(Articolo articolo) throws DAOConnectionException, DAOException{
        try{
            Connection conn = DBManager.getConnection();
            String query = "UPDATE Articolo SET " +
                    PREZZO_COLUMN + " = ?, " +
                    QUANTITA_COLUMN + " = ? WHERE " + CODICE_COLUMN + " = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setFloat(1, articolo.getPrezzo());
                preparedStatement.setInt(2, articolo.getQuantitaMagazzino());
                preparedStatement.setLong(3, articolo.getCodiceProdotto());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException("Errore update articolo");
            }

        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione database");
        }
    }

    public static void deleteArticolo(long codiceArticolo) throws DAOException,DAOConnectionException{
        try{
            Connection conn = DBManager.getConnection();
            String query = "DELETE FROM Articolo WHERE " + CODICE_COLUMN + " = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setLong(1, codiceArticolo);
                preparedStatement.executeUpdate();
                PersistanceContext.getInstance().removeFromPersistanceContext(Articolo.class,codiceArticolo);
            } catch (SQLException e) {
                throw new DAOException("Errore cancellazione articolo");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione database");
        }
    }

    public static ArrayList<Articolo> readAll() throws DAOException,DAOConnectionException{
        ArrayList<Articolo> articoli = null;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Articolo";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                articoli = new ArrayList<>();
                while (resultSet.next()) {
                    Articolo a = deserializeRecordArticolo(resultSet);
                    PersistanceContext.getInstance().putInPersistanceContext(Articolo.class,a.getCodiceProdotto());
                    articoli.add(a);
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura di tutti gli articoli");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione database");
        }
        return articoli;
    }
}
