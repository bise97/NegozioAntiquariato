package database;

import entity.Articolo;
import java.sql.*;
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
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return articolo;
    }
    public static void createArticolo(Articolo articolo){
        try{
            Connection conn = DBManager.getConnection();
            String query = "INSERT INTO Articolo VALUES(?,?,?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setLong(1, articolo.getCodiceProdotto());
                preparedStatement.setFloat(2, articolo.getPrezzo());
                preparedStatement.setInt(3, articolo.getQuantitaMagazzino());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                DBManager.closeConnection();
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static Articolo readArticolo(long codiceArticolo){
        Articolo articolo = null;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Articolo WHERE ProdottoCodice = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setLong(1, codiceArticolo);
                ResultSet resultSet = preparedStatement.executeQuery(query);
                if (resultSet.next()) {
                    articolo = deserializeRecordArticolo(resultSet);
                } else {
                    //TODO alzare eccezione se l'articolo non è presente nel db
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                DBManager.closeConnection();
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return articolo;
    }

    public static void updateArticolo(Articolo articolo){
        try{
            Connection conn = DBManager.getConnection();
            String query = "UPDATE Articolo SET " +
                    PREZZO_COLUMN + " = ?, " +
                    QUANTITA_COLUMN + " = ?, WHERE " + CODICE_COLUMN + " = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setFloat(1, articolo.getPrezzo());
                preparedStatement.setInt(2, articolo.getQuantitaMagazzino());
                preparedStatement.setLong(3, articolo.getCodiceProdotto());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                DBManager.closeConnection();
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteArticolo(long codiceArticolo){
        try{
            Connection conn = DBManager.getConnection();
            String query = "DELETE FROM Articolo WHERE " + CODICE_COLUMN + " = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setLong(1, codiceArticolo);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                DBManager.closeConnection();
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static HashMap<Long,Articolo> readAllArticoli(){
        HashMap<Long,Articolo> articoli = null;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Articolo";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                articoli = new HashMap<Long, Articolo>();
                while (resultSet.next()) {
                    Articolo a = deserializeRecordArticolo(resultSet);
                    articoli.put(a.getCodiceProdotto(), a);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                DBManager.closeConnection();
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return articoli;
    }
}
