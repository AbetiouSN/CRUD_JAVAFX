package com.employe.employe;

import java.sql.*;
public class DaoFactury {
    private static Connection connexion = null;

    private static void toCennect() {
        try {
            System.out.println("Download Driver !!");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Ok ");
            String url = "jdbc:mysql://localhost:3306/employe";
            connexion = DriverManager.getConnection(url, "root", "root");
            System.out.println("Connextion OK ");

        } catch (ClassNotFoundException e) {
            System.out.println("Download Driver Failed !!!!!!");

        } catch (SQLException e) {
            System.out.println("Erreur " + e.getMessage());
        }
    }
    public  static  Connection getConnection(){
        if (connexion == null)toCennect();
        return connexion;
    }
}
