package database;

import entity.Prodotto;
import entity.Proposta;

import java.sql.*;
import java.util.ArrayList;
import java.util.Stack;

public class PropostaDAO {


    public static void createProposta(Proposta proposta) {
        String query = "INSERT INTO Proposta(prezzo, stato, username, codice) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setFloat(1, proposta.getPrezzo());
            ps.setString(2, proposta.getStato().toString());
            ps.setString(3, proposta.getUsername());
            ps.setLong(4, proposta.getCodice());

            ps.executeUpdate();
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    if (!resultSet.wasNull()) {
                        proposta.setId(id);
                        //DBManager.putInPersistanceContext(shipment, shipmentId);
                    }
                }
            }
        } catch (SQLException e) {
            //throw new DAOException("Impossible to create a new shipment!", e);
            System.out.println("Impossibile creare una nuova proposta!");
        }
    }

    public static Proposta readProposta(long id){
        Proposta proposta = null;
        try{
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Proposta WHERE id = ?";

            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setLong(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    proposta = deserializeCurrentRecord(resultSet);
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
        return proposta;
    }

    public static ArrayList<Proposta> readAll(){
        ArrayList<Proposta> proposte = new ArrayList<>();
        try {
            Connection conn = DBManager.getConnection();
            String query = "SELECT * FROM Proposta";

            try (Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()) {
                    proposte.add(deserializeCurrentRecord(resultSet));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return proposte;
    }

    private static Proposta deserializeCurrentRecord(ResultSet rs) throws SQLException {
        Proposta proposta = new Proposta(rs.getFloat("prezzo"),rs.getString("username"),rs.getLong("codice"));
        proposta.setId(rs.getLong("id"));
        return proposta;
    }
}
