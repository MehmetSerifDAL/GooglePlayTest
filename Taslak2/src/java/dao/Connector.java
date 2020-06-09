/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import util.DB;

/**
 *
 * @author Cyber Micro
 */
public class Connector {
        
    private DB connector;
    private Connection con;
    
    public DB getConnector() {
        if(connector == null)
            connector = new DB();
        return connector;
    }

    public Connection getCon() {
        if(con == null)
            con = this.getConnector().getConnect();
        return con;
    }
}
