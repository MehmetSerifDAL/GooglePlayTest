/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.UserDataGameLib;
/**
 *
 * @author Cyber Micro
 */
public class UserDataGameLibDao extends Connector{
    
    private GameDao gameDaoo;
    
      public void create(UserDataGameLib game){
        try {
            String query = "insert into kullanici_game_tablo(kullanici_id,game_id)values(?,?)";
            PreparedStatement pst = this.getCon().prepareStatement(query);
            pst.setLong(1,game.getKullanici_id());
            pst.setLong(2, game.getGame().getGame_id());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<UserDataGameLib> findAll(Long kullanici_id,int page_Num ,int game_Sayisi,String aranacak){
        List<UserDataGameLib> kList = new ArrayList<>();
        page_Num=(page_Num -1) * game_Sayisi;
        try {
            String query = "select * from kullanici_game_tablo";
            PreparedStatement pst;
            if(aranacak !=null){
                query +=" where kullanici_id=? and game_id in(select game_id from game where tmp_name like ? )"
                        + "order by id asc limit ?,?";
                    pst = this.getCon().prepareStatement(query);                      
                    pst.setLong(1, kullanici_id);
                    pst.setString(2, "%"+aranacak+"%");
                    pst.setInt(3, page_Num);
                    pst.setInt(4, game_Sayisi); 
            }
            else{
                    query +=" where kullanici_id=? order by id asc limit ?,?";
                    pst = this.getCon().prepareStatement(query);           
                    pst.setLong(1, kullanici_id);
                    pst.setInt(2, page_Num);
                    pst.setInt(3, game_Sayisi);  
            }
                         
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                UserDataGameLib kullaniciList = new UserDataGameLib();
                kullaniciList.setId(rs.getLong("id"));
                kullaniciList.setKullanici_id(rs.getLong("kullanici_id"));
                kullaniciList.setGame(this.getGameDaoo().findGame(rs.getLong("game_id")));
                kullaniciList.setLast_update(rs.getDate("last_update"));
                kList.add(kullaniciList);
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return kList;
    }

    public void delete(UserDataGameLib game) {
        try {
            PreparedStatement pst = this.getCon().prepareStatement("delete from kullanici_game_tablo where id=?");
            pst.setLong(1, game.getId());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
     public int findCount(Long id,String aranacak) {
        int count = 0;
        try {PreparedStatement pst ;
            String query = "select count(id) as kullanici_game_list_count from kullanici_game_tablo where kullanici_id = ?";      
            if(aranacak !=null){
                query +=" and game_id in(select game_id from game where tmp_name like ?)";
                 pst = this.getCon().prepareStatement(query);  
                 pst.setLong(1, id);
                 pst.setString(2, "%"+aranacak+"%");
            }
            else{
               pst = this.getCon().prepareStatement(query);  
               pst.setLong(1, id); 
               }            
            ResultSet rs = pst.executeQuery();
           rs.next(); 
               count = rs.getInt("kullanici_game_list_count");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }
      public GameDao getGameDaoo() {
        if(this.gameDaoo == null)
            this.gameDaoo = new GameDao();
        return gameDaoo;
    }
}
