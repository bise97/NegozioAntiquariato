
import boundary.BGestore;
import boundary.utilsIO.ImmagineIO;
import database.ProdottoDAO;
import test.ArticoloDAOTester;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try{
            DBSetup.createTables();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        ArticoloDAOTester articoloDAOTester = new ArticoloDAOTester();
        articoloDAOTester.testCreate(ArticoloDAOTester.createTestCase());


        BGestore bGestore = new BGestore();
        bGestore.run();

        //TODO login

    }
}