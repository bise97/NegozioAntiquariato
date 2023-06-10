package database;
import entity.Proposta;
import exception.DAOConnectionException;
import exception.DAOException;

import java.sql.*;
import java.util.ArrayList;

public class PropostaDAO {


    public static void createProposta(Proposta proposta) throws DAOException, DAOConnectionException {
        String query = "INSERT INTO Proposta(prezzo, stato, username, codiceProdotto) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DBManager.getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setFloat(1, proposta.getPrezzo());
                ps.setString(2, proposta.getStato().toString());
                ps.setString(3, proposta.getUsername());
                ps.setLong(4, proposta.getCodice());

                ps.executeUpdate();
                ResultSet resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    if (!resultSet.wasNull()) {
                        proposta.setId(id);
                        PersistanceContext.getInstance().putInPersistanceContext(proposta, proposta.getId());
                    }
                }
            } catch (SQLException e) {
                System.out.println("Errore creazione proposta");
            }
        }catch (SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
    }

    public static Proposta readProposta(long id) throws DAOException,DAOConnectionException{
        Proposta proposta = PersistanceContext.getInstance().getFromPersistanceContext(Proposta.class,id);
        if(proposta != null) return proposta;

        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Proposta WHERE id = ?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    proposta = deserializeCurrentRecord(resultSet);
                    PersistanceContext.getInstance().putInPersistanceContext(proposta,proposta.getId());
                }
            }
            catch (SQLException e){
                throw new DAOException("Errore lettura proposta " +id);
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
        return proposta;
    }

    public static ArrayList<Proposta> readAll() throws DAOException,DAOConnectionException{
        ArrayList<Proposta> proposte = new ArrayList<>();
        try {
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Proposta";

            try (Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()) {
                    Proposta proposta = deserializeCurrentRecord(resultSet);
                    proposte.add(proposta);
                    PersistanceContext.getInstance().putInPersistanceContext(proposta,proposta.getId());
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura di tutte le proposte");
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
        return proposte;
    }

    public static ArrayList<Long> readIdProposteOfCliente(String username) throws DAOException,DAOConnectionException{
        ArrayList<Long> listaProposteCliente = new ArrayList<>();
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Proposta WHERE username = ?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setString(1,username);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    listaProposteCliente.add(resultSet.getLong("id"));
                }
            }
            catch (SQLException e){
                throw new DAOException("Errore lettura proposte del cliente con username " + username);
            }
        }
        catch(SQLException e){
            throw new DAOConnectionException("Errore connesione Database");
        }
        return listaProposteCliente;
    }

    private static Proposta deserializeCurrentRecord(ResultSet rs) throws SQLException {
        Proposta proposta = new Proposta(rs.getFloat("prezzo"),rs.getString("username"),rs.getLong("codiceProdotto"));
        proposta.setId(rs.getLong("id"));
        return proposta;
    }
}
