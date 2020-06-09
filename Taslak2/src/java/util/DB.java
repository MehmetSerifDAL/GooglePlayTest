/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Cyber Micro
 */
public class DB {
      private Connection connect;
    public Connection getConnect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
           this.connect = DriverManager.getConnection("jdbc:mysql://localhost/cyberdata", "root", "1234");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return this.connect;
    }
}
