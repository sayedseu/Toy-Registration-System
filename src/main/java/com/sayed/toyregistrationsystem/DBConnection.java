/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sayed.toyregistrationsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sayed
 */
public class DBConnection {

    private static final DBConnection instance = new DBConnection();
    private static Connection connection = null;

    private DBConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_db", "root", "77331100");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Connection getConnection() {
        return connection;
    }
}
