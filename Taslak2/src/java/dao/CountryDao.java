/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cyber Micro
 */
public class CountryDao extends Connector{
     public void create(Country konum){
         
        try {
            /*Prepared Statement kullanılmış olması oluşacak olan gucenlık zafıyetını en aza ındırmektır*/
            PreparedStatement pst = this.getCon().prepareStatement("insert into Country(ulke) values(?)");
            pst.setString(1,konum.getUlke());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
   
    public List<Country> findAll(int page_Num,int oyun_sayısı,String aranacak){
        List<Country> uList = new ArrayList<>();
        page_Num=(page_Num -1) * oyun_sayısı;
        String query = "select * from Country";
        try {
            PreparedStatement pst;
            if(aranacak != null){
              query +=" where Country like ? order by id asc limit ?,?";
             pst =this.getCon().prepareStatement(query);
            pst.setString(1, "%"+aranacak+"%");
             pst.setInt(2, page_Num);
            pst.setInt(3, oyun_sayısı);
            }
            else{
             query+=" order by id asc limit ?,?";
            pst =this.getCon().prepareStatement(query);
            pst.setInt(1, page_Num);
            pst.setInt(2, oyun_sayısı);
            }            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Country us = new Country();
                us.setUlke_id(rs.getLong("id"));
                us.setUlke(rs.getString("ulke"));
                us.setLast_update(rs.getDate("last_update"));
                uList.add(us);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return uList;
    }
    
    public void delete(Country ulke) {
        try {
           
            PreparedStatement pst = this.getCon().prepareStatement("delete from Country where id=?");
            pst.setLong(1, ulke.getUlke_id());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Country ulke) {
         try {
             PreparedStatement pst = this.getCon().prepareStatement("update Country set Country=? where id=?");
             pst.setString(1, ulke.getUlke());
             pst.setLong(2,  ulke.getUlke_id());
             pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Country findulkeBy_id(Long id) {
          Country ulke = null;

        try {
            PreparedStatement pst = this.getCon().prepareStatement("select * from Country where id=?");
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            ulke = new Country();
            ulke.setUlke_id(rs.getLong("id"));
            ulke.setUlke(rs.getString("ulke"));
           ulke.setLast_update(rs.getDate("last_update"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ulke;
    }
    
    public int findCount(String aranacak) {
        int count = 0;
        String query = "select count(id) as ulke_count from ulke";
        try {
            PreparedStatement st ;
            if(aranacak !=null){
                query +=" where Country like ?";
            st = this.getCon().prepareStatement(query);     
            st.setString(1, "%"+aranacak+"%");
            }
            else
            st = this.getCon().prepareStatement(query);            
            ResultSet rs = st.executeQuery();            
           rs.next(); 
               count = rs.getInt("ulke_count");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }
}
