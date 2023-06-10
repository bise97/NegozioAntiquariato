package test;

import database.DBSetup;

import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args){
        try{
            DBSetup.createTables();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }


    }
}
