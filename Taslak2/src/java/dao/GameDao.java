/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Category;
import entity.Game;
import entity.GameDocument;
import entity.GameDev;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cyber Micro
 */
public class GameDao extends Connector {

    private Languagedao languageDao;
    private GameLibDao gameLibDao;
    private GameDevDao gameDevDao;
    private CategoryDao categoryDao;
    private GameDocumentdao gameDocDao;
    
    public List<Game> findAll(int page_Num,int game_Sayisi,String aranacak,Long cat_id) {// pagination(sayfalama) için kullaniliyor
        List<Game> sList = new ArrayList<>();
        page_Num=(page_Num -1) * game_Sayisi;
        try {
            PreparedStatement pst;
            String query ="select * from game";   
            ResultSet rs = null;
            if(aranacak !=null && cat_id == null){
            query += " where game_id in(select file_id from game_document where file_id in(select game_id from game where tmp_name like ? )) order by game_id asc limit ?,? ";
            pst = this.getCon().prepareStatement(query);
            pst.setString(1, "%"+aranacak+"%");
            pst.setInt(2, page_Num);
            pst.setInt(3, game_Sayisi);
             rs = pst.executeQuery();    
            }
            
            else if(aranacak ==null && cat_id != null){
            query += " where game_id in(select game_id from game_category where category_id = ?) order by game_id asc limit ?,? ";
            pst = this.getCon().prepareStatement(query);
            pst.setLong(1, cat_id);
            pst.setInt(2, page_Num);
            pst.setInt(3, game_Sayisi);
             rs = pst.executeQuery();    
            }
            else if(aranacak !=null && cat_id != null){
            query += " where game_id in(select file_id from game_document where file_id in(select game_id from game where tmp_name like ? ))"
                    + " and game_id in( select game_id from game_category where category_id = ?) order by game_id asc limit ?,? ";
            pst = this.getCon().prepareStatement(query);
            pst.setString(1, "%"+aranacak+"%");
            pst.setLong(2, cat_id);
            pst.setInt(3, page_Num);
            pst.setInt(4, game_Sayisi);
            rs = pst.executeQuery();    
            }
            
            else if(aranacak ==null && cat_id == null){
            query += " order by game_id asc limit ?,? ";
            pst = this.getCon().prepareStatement(query);
            pst.setInt(1, page_Num);
            pst.setInt(2, game_Sayisi);
            rs = pst.executeQuery();    
            }                                           
            while (rs.next()) {
                Game game = new Game();
                game.setGame_id(rs.getLong("game_id"));
                game.setTmp_name(rs.getString("tmp_name"));
                game.setDescription(rs.getString("description"));
                game.setYil(rs.getInt("yil"));
                game.setLast_update(rs.getDate("last_update"));
                game.setGameLan(this.getLanguagedao().find_Id(rs.getLong("dil_id")));
                game.setGamelib(this.getGameLibDao().find_gamelibId(rs.getLong("album_id")));
                game.setGameCategory(this.getCategoryDao().getGame_category(rs.getLong("game_id")));
                game.setGameGameDev(this.getGameDevDao().getGameGameDev(rs.getLong("game_id")));
                game.setGameDoc(this.getGameDocDao().find(rs.getLong("game_id")));
                sList.add(game);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sList;
    }    
    public int findCount(String aranacak,Long id) {
        int count = 0;
        try {
            String query = "select count(game_id) as game_count from game ";
            PreparedStatement st;
            ResultSet rs = null ;
            if(aranacak != null && id == null){
              query +="where tmp_name like ? ";
              st = this.getCon().prepareStatement(query);
              st.setString(1, "%"+aranacak+"%");  
              rs = st.executeQuery();
            }
            else if(aranacak == null && id != null){
              query +="where game_id in( select game_id from game_category where category_id = ?) ";
              st = this.getCon().prepareStatement(query);
              st.setLong(1, id);      
              rs = st.executeQuery();
            }
            else if(aranacak != null && id != null){
              query +="where tmp_name like ? and game_id in( select game_id from game_category where category_id = ?)";
              st = this.getCon().prepareStatement(query);
              st.setString(1, "%"+aranacak+"%");
              st.setLong(2, id);
              rs = st.executeQuery();
            }
            else if(aranacak == null && id == null){
                st = this.getCon().prepareStatement(query);
                rs = st.executeQuery();
            }
                rs.next(); 
               count = rs.getInt("game_count");                                                
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }
    public int create(GameDocument game) {
        int i = 0;
        try {
            PreparedStatement pst ;
            String query = "insert into game(game_id,tmp_name) values(?,?)";
            pst= this.getCon().prepareStatement(query);
            pst.setLong(1, game.getDoc_id());
            pst.setString(2, game.getFileName());
            i = pst.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public int update(Game game) {
        int i = 0;
        try {
            String query = "update game set tmp_name=?, description=?, yil=?, dil_id=?, album_id=? where game_id=?";
            PreparedStatement pst = this.getCon().prepareStatement(query);
            pst.setString(1, game.getTmp_name());
            pst.setString(2, game.getDescription());
            pst.setInt(3 , game.getYil());
             pst.setLong(4, game.getGameLan().getDil_id());
            pst.setLong(5, game.getGamelib().getGamelib_id());
            pst.setLong(6, game.getGame_id());
          i =  pst.executeUpdate();
          
          pst = this.getCon().prepareStatement("delete from game_category where game_id=?");
                pst.setLong(1, game.getGame_id());
                pst.executeUpdate();
            
            for (Category category : game.getGameCategory()) {// bu işi genede game_category classlarni olustrup orada yapabilirdik..
                pst = this.getCon().prepareStatement("insert into game_category(game_id,category_id) values(?,?)");
                pst.setLong(1, game.getGame_id());
                pst.setLong(2, category.getId());
                pst.executeUpdate();
            }
            
            pst = this.getCon().prepareStatement("delete from game_gameDev where game_id=?");
                pst.setLong(1, game.getGame_id());
                pst.executeUpdate();
            
            for (GameDev gameDev : game.getGameGameDev()) {// bu işi genede game_category classlarni olustrup orada yapabilirdik..
                pst = this.getCon().prepareStatement("insert into game_gamedev(game_id,gameDev_id) values(?,?)");
                pst.setLong(1, game.getGame_id());
                pst.setLong(2, gameDev.getGameDev_id());
                pst.executeUpdate();
            }
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public int delete(Game game) {
        PreparedStatement pst;
        int i = 0;
        try{
        pst = this.getCon().prepareStatement("delete from game_category where game_id=?");
                pst.setLong(1, game.getGame_id());
                pst.executeUpdate();
        
        pst = this.getCon().prepareStatement("delete from game_gameDev where game_id=?");
                pst.setLong(1, game.getGame_id());
                pst.executeUpdate();
                
                pst = this.getCon().prepareStatement("delete from game where game_id=?");
                pst.setLong(1, game.getGame_id());
                i = pst.executeUpdate();
        } catch(Exception e ){
            System.out.println(e.getMessage());
        }
        return i;
    }

    public Game findGame(long game_id) {

        Game game = null;
        try {
            PreparedStatement pst = this.getCon().prepareStatement("select * from game where game_id=?");
            pst.setLong(1, game_id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            game = new Game();
            game.setGame_id(rs.getLong("game_id"));
            game.setTmp_name(rs.getString("tmp_name"));
            game.setDescription(rs.getString("description"));
            game.setYil(rs.getInt("yil"));
            game.setGamelib(this.getGameLibDao().find_gamelibId(rs.getLong("gameLib_id")));
            game.setGameLan(this.getLanguagedao().find_Id(rs.getLong("dil_id")));
             game.setGameCategory(this.getCategoryDao().getGame_category(rs.getLong("game_id")));
             game.setGameGameDev(this.getGameDevDao().getGameGameDev(rs.getLong("game_id")));
             game.setGameDoc(this.getGameDocDao().find(rs.getLong("game_id")));
            game.setLast_update(rs.getDate("last_update"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return game;
    }
    public GameLibDao getGameLibDao() {
        if (this.gameLibDao == null) {
            this.gameLibDao = new GameLibDao();
        }
        return gameLibDao;
    }

    public Languagedao getLanguagedao() {
        if (this.languageDao == null) {
            this.languageDao = new Languagedao();
        }
        return languageDao;
    }

    public GameDevDao getGameDevDao() {
        if(this.gameDevDao == null)
            this.gameDevDao = new GameDevDao();
        return gameDevDao;
    }

    public CategoryDao getCategoryDao() {
        if(this.categoryDao == null)
            this.categoryDao = new CategoryDao();
        return categoryDao;
    }

    public GameDocumentdao getGameDocDao() {
        if(this.gameDocDao == null)
            this.gameDocDao = new GameDocumentdao();
        return gameDocDao;
    }

}
