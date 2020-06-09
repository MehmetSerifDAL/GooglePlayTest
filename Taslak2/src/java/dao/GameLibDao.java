/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Gamelib;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cyber Micro
 */
public class GameLibDao extends Connector{
       public List<Gamelib> findAll(int page_Num,int oyun_sayisi,String aranack) {
        List<Gamelib> gamelibList = new ArrayList();
        page_Num=(page_Num -1) * oyun_sayisi;
        PreparedStatement pst;
        try {
            String query ="select * from gamelib";
            if(aranack == null){
                query +=" order by gamelib_id asc limit ?,?";
            pst = this.getCon().prepareStatement(query);
            pst.setInt(1, page_Num);
            pst.setInt(2, oyun_sayisi);
            }
            else{
                query +=" where gamelib like ? order by gamelib_id asc limit ?,?";
            pst = this.getCon().prepareStatement(query);
            pst.setString(1,"%"+aranack+"%");
            pst.setInt(2, page_Num);
            pst.setInt(3, oyun_sayisi);
            }
           
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Gamelib tmp = new Gamelib(/* rs.getLong("gamelib_id") ,rs.getString("gamelib_name") ,rs.getDate("last_update")*/);
                tmp.setGamelib_id(rs.getLong("gamelib_id"));
                tmp.setName(rs.getString("gamelib_name"));
                tmp.setAlbum_LastUpdate(rs.getDate("last_update"));

                gamelibList.add(tmp);
            }

        } catch (SQLException x) {
            System.out.println(x.getMessage());
        }

        return gamelibList;
    }

    public void create(Gamelib gamelib) {

        try {
            PreparedStatement pst = this.getCon().prepareStatement("insert into gamelib (gamelib_name) values(?)");
            pst.setString(1, gamelib.getName());
            pst.executeUpdate();
        } catch (SQLException x) {
            System.out.println(x.getMessage());
        }
    }

    public void delete(Gamelib gamelib) {

       try {
            PreparedStatement pst = this.getCon().prepareStatement("delete from gamelib where gamelib_id=?");
            pst.setLong(1, gamelib.getGamelib_id());
            pst.executeUpdate();
        } catch (SQLException x) {
            System.out.println(x.getMessage());
        }

    }

    public void update(Gamelib gamelib) {

       try {
            PreparedStatement pst = this.getCon().prepareStatement("update gamelib set gamelib_name=? where gamelib_id=?");
            pst.setString(1, gamelib.getName());
            pst.setLong(2, gamelib.getGamelib_id());
            pst.executeUpdate( );
        } catch (SQLException x) {
            System.out.println(x.getMessage());
        }

    }
    
    
    public Gamelib find_gamelibId(Long gamelib_id) {
         Gamelib gamelib = null;

        try {
            PreparedStatement pst = this.getCon().prepareStatement("select * from gamelib where gamelib_id = ? ");
            pst.setLong(1, gamelib_id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            gamelib = new Gamelib();
            gamelib.setGamelib_id(rs.getLong("gamelib_id"));
            gamelib.setName(rs.getString("gamelib_name"));
            gamelib.setAlbum_LastUpdate(rs.getDate("last_update"));
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return gamelib;
        
    }
    public List<Gamelib> getGameDevGamelib(Long gamedev_id){
          List<Gamelib> GameDevlist =new ArrayList<>();
        try {
           PreparedStatement pst ;
           String query = "select * from gamedev_gamelib where sarkici_id = ?";
           pst= this.getCon().prepareStatement(query);
           pst.setLong(1, gamedev_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                GameDevlist.add(this.find_gamelibId(rs.getLong("gamelib_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return GameDevlist;
    }
    
    
     public int findCount(String aranacak) {
        int count = 0;
        String query = "select count(gamelib_id) as gamelib_count from gamelib ";
        PreparedStatement st;
        try {
            if(aranacak == null)
            st = this.getCon().prepareStatement(query);
            else{
            st = this.getCon().prepareStatement(query+"where gamelib_name like ? "); 
            st.setString(1, "%"+aranacak+"%");
            }
            ResultSet rs = st.executeQuery();
           rs.next(); 
               count = rs.getInt("gamelib_count");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

}
