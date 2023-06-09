import boundary.BClienteRegistrato;
import database.ClienteDAO;
import entity.Cliente;
import test.CartaDiCreditoDAOTester;
import test.ClienteDAOTester;
import test.ProdottoDAOTester;
import test.PropostaDAOTester;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try{
            DBSetup.createTables();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        Cliente cliente1 = new Cliente("bise","ciao","3333333333","366365354","Biagio","Salzillo","2025-01-12");
        ClienteDAO.createCliente(cliente1);

        BClienteRegistrato bClienteRegistrato = new BClienteRegistrato(cliente1.getUsername());
        bClienteRegistrato.run();



        //TODO login

    }
}