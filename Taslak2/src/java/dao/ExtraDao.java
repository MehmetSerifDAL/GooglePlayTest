package dao;

import entity.Extra;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExtraDao extends Connector {
   
    private GameDao gameDao;
    private CountryDao countryDao;
    public int create(Extra extra) {   
        int i = 0;
        try {
            String query = "insert into extra(game_id,game_puan,game_sure,ulke_id,game_sirketi) values(?,?,?,?,?)";
           PreparedStatement pst = this.getCon().prepareStatement(query);
           pst.setLong(1,extra.getGame().getGame_id());
           pst.setFloat(2, extra.getGame_puan());
           pst.setFloat(3, extra.getGame_sure());
           pst.setLong(4,extra.getUlke().getUlke_id());
           pst.setString(5, extra.getGame_sirketi());
           i= pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    public List<Extra> findAll(int page_Num,int extra_Sayisi,String aranacak) {
        List<Extra> exList = new ArrayList<>();
        page_Num=(page_Num -1) * extra_Sayisi;
        try {
            String query="select * from extra";  
            PreparedStatement pst;
            if(aranacak !=null){
                query +=" where sarki_id in(select game_id from game where tmp_name like ?) order by id asc limit ?,?";
                pst= this.getCon().prepareStatement(query);
                pst.setString(1, "%"+aranacak+"%");
                pst.setInt(2, page_Num);
                pst.setInt(3, extra_Sayisi);
            }
            else{
                query +=" order by id asc limit ?,?";
                pst= this.getCon().prepareStatement(query);
                pst.setInt(1, page_Num);
                pst.setInt(2, extra_Sayisi);
            }            
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Extra exstra = new Extra();
                exstra.setId(rs.getLong("id"));
                exstra.setGame(this.getGameDao().findGame(rs.getLong("sarki_id")));
                exstra.setGame_puan(rs.getFloat("game_puan"));
                exstra.setGame_sure(rs.getFloat("game_sure"));
                exstra.setUlke(this.getCountryDao().findulkeBy_id(rs.getLong("ulke_id")));
                exstra.setGame_sirketi(rs.getString("gamedev_sirketi"));
                exstra.setLast_update(rs.getDate("last_update"));
                exList.add(exstra);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exList;
    }
    

    public int delete(Extra exstra) {
        int i=0;
        try {
            PreparedStatement pst = this.getCon().prepareStatement("delete from extra where id=?");
            pst.setLong(1,exstra.getId());
            i= pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public int update(Extra exstra) {
        int i=0;
        try {
            PreparedStatement pst = this.getCon().prepareStatement("update extra set sarki_id=?, sarki_puan=?, sarki_sure=? ,ulke_id = ? , sarkici_sirketi=? where id =?");
            pst.setLong(1,exstra.getGame().getGame_id());
            pst.setFloat(2,exstra.getGame_puan());
            pst.setFloat(3,exstra.getGame_sure());
            pst.setLong(4,exstra.getUlke().getUlke_id());
            pst.setString(5,exstra.getGame_sirketi());
            pst.setLong(6,exstra.getId());
            i = pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    
    public Extra find(Long id){
        Extra ex = null;
        try{
        PreparedStatement pst ;
        String query = "select * from extra where sarki_id = ?";
        pst = this.getCon().prepareStatement(query);
        pst.setLong(1, id);        
        ResultSet rs = pst.executeQuery();
        rs.next();
        ex = new Extra();
        ex.setId(rs.getLong("id"));
        ex.setGame(this.getGameDao().findGame(rs.getLong("game_id")));
        ex.setGame_puan(rs.getFloat("game_puan"));
        ex.setGame_sure(rs.getFloat("game_sure"));
        ex.setGame_sirketi(rs.getString("gamedev_sirketi"));
        ex.setUlke(this.getCountryDao().findulkeBy_id(rs.getLong("ulke_id")));
        ex.setLast_update(rs.getDate("last_update"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return ex;
    }
    
    public int findCount(String aranacak) {
        int count = 0;
        try {
            String query = "select count(id) as extra_count from extra";  
            PreparedStatement pst;
            if(aranacak !=null){
                query +=" where game_id in(select game_id from sarki where tmp_name like ?)";
                pst = this.getCon().prepareStatement(query);
                pst.setString(1, "%"+aranacak+"%");
            }
            else
               pst = this.getCon().prepareStatement(query);
                
            ResultSet rs = pst.executeQuery();
           rs.next(); 
               count = rs.getInt("extra_count");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }
    public GameDao getGameDao() {
        if(this.gameDao == null)
            this.gameDao = new GameDao();
        return gameDao;
    }

    public CountryDao getCountryDao() {
        if(this.countryDao == null)
            this.countryDao = new CountryDao();
        return countryDao;
    }
    
}
