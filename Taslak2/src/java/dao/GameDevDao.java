/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import java.sql.Statement;
import entity.Gamelib;
import entity.GameDev;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Cyber Micro
 */
public class GameDevDao extends Connector {

     private CountryDao ulkeDao;
    private GameLibDao gamelibDao;
    private PrintDocumentDao resimDao;

    public void create(GameDev gameDev) {

        try {
            String query = "insert into gameDev(name,last_name,yas,ulke_id) values(?,?,?,?)";
            PreparedStatement pst = this.getCon().prepareStatement(query ,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, gameDev.getName());
            pst.setString(2, gameDev.getLast_name());
            pst.setLong(3,gameDev.getYas());
            pst.setLong(4, gameDev.getCountry().getUlke_id());
            pst.executeUpdate();
            
            Long gameDev_ID = null;
             ResultSet gk = pst.getGeneratedKeys();
            if (gk.next()) {
                gameDev_ID = gk.getLong(1);
            }
            
            for (Gamelib gamelib : gameDev.getGameDevGameLib()) {
                pst = this.getCon().prepareStatement("insert into gameDev_gamelib(gameDev_id,gamelib_id) values(?,?)");
                pst.setLong(1, gameDev_ID);
                pst.setLong(2, gamelib.getGamelib_id());
                pst.executeUpdate();
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<GameDev> findAll(int page_Num,int game_sayisi,String aranacak) {
        List<GameDev> sList = new ArrayList<>();
          page_Num=(page_Num -1) * game_sayisi;
          String query="select * from gameDev";
        try {            
            PreparedStatement pst;
            if(aranacak != null){
            query +=" where name like ? order by gameDev_id asc limit ?,?";
            pst = this.getCon().prepareStatement(query);
            pst.setString(1, "%"+aranacak+"%");
            pst.setInt(2, page_Num);
            pst.setInt(3, game_sayisi);
            }
            else{
            query +=" order by gameDev_id asc limit ?,?";
            pst = this.getCon().prepareStatement(query);
            pst.setInt(1, page_Num);
            pst.setInt(2, game_sayisi);
            }
            
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                GameDev gameDev = new GameDev();
                gameDev.setGameDev_id(rs.getLong("gameDev_id"));
                gameDev.setName(rs.getString("name"));
                gameDev.setLast_name(rs.getString("last_name"));
                gameDev.setYas(rs.getInt("yas"));
                gameDev.setLast_update(rs.getDate("last_update"));
                gameDev.setCountry(this.getCountryDao().findulkeBy_id(rs.getLong("ulke_id")));
                gameDev.setGameDevGameLib(this.getGameLibDao().getGameDevGamelib(rs.getLong("gameDev_id")));
                gameDev.setGameDev_resim(this.getResimDao().find(rs.getLong("gameDev_id") + 1000000));
                sList.add(gameDev);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sList;
    }
    
    public void delete(GameDev gameDev) {
        try {
            PreparedStatement pst = this.getCon().prepareStatement("delete from gameDev_gamelib where gameDev_id=?");
            pst.setLong(1, gameDev.getGameDev_id());
            pst.executeUpdate();
            
            pst = this.getCon().prepareStatement("delete from gameDev where gameDev_id=?");
            pst.setLong(1, gameDev.getGameDev_id());
            pst.executeUpdate();
            this.getResimDao().delete(gameDev.getGameDev_id());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(GameDev gameDev) {
        try {
            PreparedStatement pst = this.getCon().prepareStatement("update gameDev set name=?, last_name=?, yas=?, ulke_id=?  where gameDev_id=?");
            pst.setString(1, gameDev.getName());
            pst.setString(2,gameDev.getLast_name());
            pst.setInt(3,gameDev.getYas());
            pst.setLong(4,gameDev.getCountry().getUlke_id());
            pst.setLong(5,gameDev.getGameDev_id());
            pst.executeUpdate();
            
            pst = this.getCon().prepareStatement("delete from gameDev_gamelib where gameDev_id=?");
                pst.setLong(1, gameDev.getGameDev_id());
                pst.executeUpdate();
            
            for (Gamelib gamelib : gameDev.getGameDevGameLib()) {
                pst = this.getCon().prepareStatement("insert into gameDev_gamelib(gameDev_id,gamelib_id) values(?,?)");
                pst.setLong(1, gameDev.getGameDev_id());
                pst.setLong(2, gamelib.getGamelib_id());
                pst.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public int findCount(String aranacak) {
        int count = 0;
        String query = "select count(gameDev_id) as gameDev_count from gameDev";
        try {
            PreparedStatement pst;
            if(aranacak != null){
             query +=" where name like ? ";
              pst = this.getCon().prepareStatement(query);
              pst.setString(1, "%"+aranacak+"%");
            }
            else{
                pst = this.getCon().prepareStatement(query);
            }            
            ResultSet rs = pst.executeQuery();
           rs.next(); 
               count = rs.getInt("gameDev_count");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }
    
    public GameDev findGameDev(Long gameDev_ID) { 
        GameDev gameDev = null;
        try {
            PreparedStatement pst;
            String query = "select * from gameDev where gameDev_id= ?" ;
            pst = this.getCon().prepareStatement(query);
            pst.setLong(1 ,gameDev_ID);
            ResultSet rs = pst.executeQuery();
            rs.next();
            gameDev = new GameDev();
                gameDev.setGameDev_id(rs.getLong("gameDev_id"));
                gameDev.setName(rs.getString("name"));
                gameDev.setLast_name(rs.getString("last_name"));
                gameDev.setYas(rs.getInt("yas"));
                gameDev.setLast_update(rs.getDate("last_update"));
                gameDev.setCountry(this.getCountryDao().findulkeBy_id(rs.getLong("ulke_id")));
                 gameDev.setGameDevGameLib(this.getGameLibDao().getGameDevGamelib(rs.getLong("gameDev_id")));
                gameDev.setGameDev_resim(this.getResimDao().find(rs.getLong("gameDev_id") + 1000000));
            
    }  catch (SQLException ex) {
            System.out.println(ex.getMessage());
       }
        return gameDev;
    }

    List<GameDev> getGameGameDev(long game_id) {
         List<GameDev> slist =new ArrayList<>();
        try {
           PreparedStatement pst ;
           String query = "select * from game_gameDev where game_id = ?";
           pst= this.getCon().prepareStatement(query);
           pst.setLong(1, game_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                slist.add(this.findGameDev(rs.getLong("gameDev_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return slist;
    }

    
    public CountryDao getCountryDao() {
        if (this.ulkeDao == null) {
            this.ulkeDao = new CountryDao();
        }
        return ulkeDao;
    }
    public GameLibDao getGameLibDao() {
        if(this.gamelibDao == null)
            this.gamelibDao = new GameLibDao();
        return gamelibDao;
    }

    public PrintDocumentDao getResimDao() {
        if(this.resimDao == null)
            this.resimDao = new PrintDocumentDao();
        return resimDao;
    }

    
}
