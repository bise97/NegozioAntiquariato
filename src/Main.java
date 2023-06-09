import test.ProdottoDAOTester;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try{
            DBSetup.createTables();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        ProdottoDAOTester prodottoDAOTester = new ProdottoDAOTester();
        prodottoDAOTester.runTest();

        System.out.println();







//        Cliente cliente1 = new Cliente("bise","ciao","3333333333","366365354","Biagio","Salzillo","2025-01-12");
//        ClienteDAO.createCliente(cliente1);
        //System.out.println(cliente1);
//        Proposta proposta = new Proposta(20,cliente1.getUsername(), p.getCodice());
//        System.out.println(proposta);
//        PropostaDAO.createProposta(proposta);


//        Cliente cliente1 = new Cliente("bise","ciao","3333333333","366365354","Biagio","Salzillo","2025-01-12");
//        Cliente cliente2 = new Cliente("gae", "hello", "80000000","48828329920230","Gaetano","martedi","2026-03-31");
//        ClienteDAO.createCliente(cliente1);
//        ClienteDAO.createCliente(cliente2);
//        System.out.println(ClienteDAO.readCliente("bise"));
        //TODO login

    }
}