package database;

import entity.Immagine;
import javax.imageio.ImageIO;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;

public class ImmagineDAO {
    private static final String ID_COLUMN = "id";
    private static final String BLOB_COLUMN = "blob";
    private static final String PRODOTTO_COLUMN = "ProdottoCodice";

    private static Immagine deserializeRecordImmagine(ResultSet rs){
        Immagine img = null;
        try{
            Blob blob = rs.getBlob(BLOB_COLUMN);

            img = new Immagine(rs.getLong(ID_COLUMN),
                    ImageIO.read(blob.getBinaryStream()),
                    rs.getLong(PRODOTTO_COLUMN));

            blob.free();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return img;
    }

    public static void createImmagine(Immagine img){
        try{
            Connection conn = DBManager.getConnection();
            String query = "INSERT INTO Immagine("+BLOB_COLUMN+","+PRODOTTO_COLUMN+") VALUES (?,?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                Blob blob = conn.createBlob();
                OutputStream blobOutStream = blob.setBinaryStream(1);
                ImageIO.write(img.getImage(), "jpg", blobOutStream);
                blobOutStream.close();
                preparedStatement.setBlob(1, blob);
                preparedStatement.setLong(2, img.getCodiceProdotto());

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
                System.out.println(e.getMessage());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static Immagine readImmagine(long idImmagine){
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
                else {
                    //TODO  alzare eccezione se l'immagine non è presente nel db
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return img;
    }

    public static void updateImmagine(Immagine img){
        try{
            Connection conn = DBManager.getConnection();
            String query = "UPDATE Immagine SET " +
                    BLOB_COLUMN + " = ?, " +
                    PRODOTTO_COLUMN + " = ? WHERE " + ID_COLUMN + " = ?";
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                Blob imageBlob = conn.createBlob();
                OutputStream blobOutStream = imageBlob.setBinaryStream(1);
                ImageIO.write(img.getImage(), "jpg", blobOutStream);
                blobOutStream.close();
                preparedStatement.setBlob(1,imageBlob);
                preparedStatement.setLong(2,img.getCodiceProdotto());
                preparedStatement.setLong(3,img.getId());

                preparedStatement.executeUpdate();
                imageBlob.free();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteImmagine(long idImmagine){
        try{
            Connection conn = DBManager.getConnection();
            String query = "DELETE FROM Immagine WHERE " + ID_COLUMN + " = ?";
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,idImmagine);
                preparedStatement.executeUpdate();
                PersistanceContext.getInstance().removeFromPersistanceContext(Immagine.class,idImmagine);
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //TODO da testare deleteImmaginiOfProdotto
    public static void deleteImmaginiOfProdotto(long codiceProdotto){

        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT "+ID_COLUMN+" FROM Immagine WHERE " + PRODOTTO_COLUMN + " = ?";
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,codiceProdotto);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){

                    ImmagineDAO.deleteImmagine(resultSet.getLong(ID_COLUMN));
//                    PersistanceContext.getInstance().removeFromPersistanceContext(Immagine.class,);
                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Immagine> readImmaginiProdotto(long codice){
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
                    //TODO  alzare eccezione se l'immagine non è presente nel db
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return immagini;
    }

    public static ArrayList<Immagine> readAll(){
        ArrayList<Immagine> immagini = null;
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
                System.out.println(e.getMessage());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return immagini;
    }
    //TODO se modifico un oggetto attraverso la reference restituita da readArticolo si modifica anche l'istanza corripettiva in PErsistanceContext?
}
