package database;

import entity.Articolo;
import java.sql.*;

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
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setLong(0,articolo.getCodiceProdotto());
            preparedStatement.setFloat(1,articolo.getPrezzo());
            preparedStatement.setInt(2,articolo.getQuantitaMagazzino());

            try{
                preparedStatement.executeUpdate();
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
            finally {
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
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setLong(0,codiceArticolo);
            try{
                ResultSet resultSet = preparedStatement.executeQuery(query);
                articolo = deserializeRecordArticolo(resultSet);
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
        return articolo;
    }

    public static void updateArticolo(Articolo articolo){
        try{
            Connection conn = DBManager.getConnection();
            String query = "UPDATE Articolo SET " +
                    PREZZO_COLUMN + " = ?, " +
                    QUANTITA_COLUMN + " = ?, WHERE " + CODICE_COLUMN + " = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setFloat(0,articolo.getPrezzo());
            preparedStatement.setInt(1,articolo.getQuantitaMagazzino());
            preparedStatement.setLong(2,articolo.getCodiceProdotto());
            try{
                preparedStatement.executeUpdate();
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
    }

    public static void deleteArticolo(long codiceArticolo){
        try{
            Connection conn = DBManager.getConnection();
            String query = "DELETE FROM Articolo WHERE " + CODICE_COLUMN + " = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setLong(0,codiceArticolo);
            try{
                preparedStatement.executeUpdate();
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
    }


}
