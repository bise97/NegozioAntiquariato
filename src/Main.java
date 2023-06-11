import boundary.BCliente;
import boundary.BGestore;
import database.DBSetup;
import exception.DAOConnectionException;
import exception.DAOException;

import java.sql.SQLException;

import static boundary.utilsIO.TerminalIO.askUser;

public class Main {
    public static void main(String[] args) {
        boolean f = true;
        String option;

        try{
            DBSetup.initialize();
        }catch(SQLException | DAOException | DAOConnectionException e){
            System.out.println(e.getMessage());
            return;
        }

        while(f){

            option = askUser(
                    """

                            Entra nel sistema come:\s
                            1. Gestore Negozio
                            2. Cliente
                            3. Esci\s""");

            switch (option) {
                case "1" -> {
                    BGestore bG = new BGestore();
                    bG.run();
                }
                case "2" -> {
                    BCliente bC = new BCliente();
                    bC.login();
                }
                case "3" -> f = false;
                default -> System.out.println("Opzione non disponibile!");
            }
        }

    }
}