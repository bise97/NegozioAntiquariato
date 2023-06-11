package test;

import database.DBSetup;
import exception.DAOConnectionException;
import exception.DAOException;

import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args){
        try{
            DBSetup.initialize();
        }catch(SQLException | DAOException | DAOConnectionException e){
            System.out.println(e.getMessage());
            return;
        }


    }
}
