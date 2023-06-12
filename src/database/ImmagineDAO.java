package database;

import entity.Immagine;
import exception.DAOConnectionException;
import exception.DAOException;

import javax.imageio.ImageIO;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;

public class ImmagineDAO {
    private static final String ID_COLUMN = "id";
    private static final String BLOB_COLUMN = "blob";
    private static final String PRODOTTO_COLUMN = "ProdottoCodice";
    private static final String PATH = "path";

    private static Immagine deserializeRecordImmagine(ResultSet rs){
        Immagine img = null;
        try{
            Blob blob = rs.getBlob(BLOB_COLUMN);

            img = new Immagine(rs.getLong(ID_COLUMN),
                    ImageIO.read(blob.getBinaryStream()),
                    rs.getLong(PRODOTTO_COLUMN),
                    rs.getString(PATH));

            blob.free();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return img;
    }

    public static void createImmagine(Immagine img) throws DAOException, DAOConnectionException {
        try{
            Connection conn = DBManager.getConnection();
            String query = "INSERT INTO Immagine("+BLOB_COLUMN+","+PRODOTTO_COLUMN+ ","+ PATH +") VALUES (?,?,?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                Blob blob = conn.createBlob();
                OutputStream blobOutStream = blob.setBinaryStream(1);
                ImageIO.write(img.getImage(), "jpg", blobOutStream);
                blobOutStream.close();
                preparedStatement.setBlob(1, blob);
                preparedStatement.setLong(2, img.getCodiceProdotto());
                preparedStatement.setString(3,img.getPath());

                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    long id = rs.getLong(ID_COLUMN);
                    if(!rs.wasNull()){
                        img.setId(id);
                        PersistanceContext.getInstance().putInPersistanceContext(img,img.getId());
                    }
                }
                blob.free();
            } catch (Exception e) {
                throw new DAOException("Errore creazione Immagine");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connessione Database");
        }
    }

    public static Immagine readImmagine(long idImmagine) throws DAOException,DAOConnectionException{
        Immagine img = PersistanceContext.getInstance().getFromPersistanceContext(Immagine.class,idImmagine);
        if(img != null) return img;

        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Immagine WHERE "+ID_COLUMN+" = ?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,idImmagine);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    img = deserializeRecordImmagine(resultSet);
                    PersistanceContext.getInstance().putInPersistanceContext(img,img.getId());
                }
            }
            catch (SQLException e){
                throw new DAOException("Errore lettura immagine " + idImmagine);
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
        return img;
    }

    public static void updateImmagine(Immagine img) throws DAOException,DAOConnectionException{
        try{
            Connection conn = DBManager.getConnection();
            String query = "UPDATE Immagine SET " +
                    BLOB_COLUMN + " = ?, " +
                    PRODOTTO_COLUMN + " = ? WHERE " + ID_COLUMN + " = ?," + PATH + " =?" ;
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                Blob imageBlob = conn.createBlob();
                OutputStream blobOutStream = imageBlob.setBinaryStream(1);
                ImageIO.write(img.getImage(), "jpg", blobOutStream);
                blobOutStream.close();
                preparedStatement.setBlob(1,imageBlob);
                preparedStatement.setLong(2,img.getCodiceProdotto());
                preparedStatement.setLong(3,img.getId());
                preparedStatement.setString(4, img.getPath());

                preparedStatement.executeUpdate();
                imageBlob.free();
            }
            catch (Exception e){
                throw new DAOException("Errore update Immagine");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
    }

    public static void deleteImmagine(long idImmagine) throws DAOException,DAOConnectionException{
        try{
            Connection conn = DBManager.getConnection();
            String query = "DELETE FROM Immagine WHERE " + ID_COLUMN + " = ?";
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,idImmagine);
                preparedStatement.executeUpdate();
                PersistanceContext.getInstance().removeFromPersistanceContext(Immagine.class,idImmagine);
            }
            catch (SQLException e){
                throw new DAOException("Errore cancellazione Immagine " + idImmagine);
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
    }

    public static void deleteImmaginiOfProdotto(long codiceProdotto) throws DAOException,DAOConnectionException{

        deleteImmaginiOfProdottoFromPersistanceContext(codiceProdotto);

        try{
            Connection conn = DBManager.getConnection();
            String query = "DELETE FROM Immagine WHERE " + PRODOTTO_COLUMN + " = ?";
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,codiceProdotto);
                preparedStatement.executeUpdate();
            }
            catch (SQLException e){
                throw new DAOException("Errore cancellazione delle Immagini del Prodotto con Codice " + codiceProdotto);
            }

        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
    }

    public static void deleteImmaginiOfProdottoFromPersistanceContext(long codiceProdotto) throws DAOException,DAOConnectionException{
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT "+ID_COLUMN+" FROM Immagine WHERE " + PRODOTTO_COLUMN + " = ?";
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,codiceProdotto);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    PersistanceContext.getInstance().removeFromPersistanceContext(Immagine.class,resultSet.getLong(ID_COLUMN));
                }
            }
            catch (SQLException e){
                throw new DAOException("Errore cancellazione delle immagini del prodotto " + codiceProdotto + "dal persistance context");
            }

        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connessione Database");
        }
    }

    public static ArrayList<Immagine> readImmaginiProdotto(long codice) throws DAOException,DAOConnectionException{
        ArrayList<Immagine> immagini = new ArrayList<>();
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Immagine WHERE ProdottoCodice=?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,codice);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    Immagine img = deserializeRecordImmagine(resultSet);
                    immagini.add(img);
                    PersistanceContext.getInstance().putInPersistanceContext(img,img.getId());
                }
            }
            catch (SQLException e){
                throw new DAOException("Errore lettura delle immagini del prodotto con codice " + codice);
            }

        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connessione al Database");
        }
        return immagini;
    }

    public static ArrayList<Immagine> readAll() throws DAOException,DAOConnectionException{
        ArrayList<Immagine> immagini;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Immagine";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                immagini = new ArrayList<>();
                while (resultSet.next()) {
                    Immagine img = deserializeRecordImmagine(resultSet);
                    immagini.add(img);
                    PersistanceContext.getInstance().putInPersistanceContext(img,img.getId());
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura di tutte le immagini");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
        return immagini;
    }
}
