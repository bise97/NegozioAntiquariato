import boundary.BCliente;
import database.DBManager;
import entity.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        //Cliente c = new Cliente("bise","pass1","3335624180");
        BCliente bC = BCliente.getInstance();
        bC.login();
    }
}