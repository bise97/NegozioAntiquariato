import boundary.BCliente;
import boundary.BGestore;
import database.ClienteDAO;
import database.DBSetup;
import entity.Cliente;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean f = true;
        String option;

        try{
            DBSetup.createTables();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        Cliente cliente1 = new Cliente("bise","ciao","3333333333","366365354","Biagio","Salzillo","2025-01-12");
        ClienteDAO.createCliente(cliente1);

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

    private static String askUser(String print){
        Scanner scanner = new Scanner(System.in);
        System.out.println(print);
        return scanner.nextLine();
    }
}