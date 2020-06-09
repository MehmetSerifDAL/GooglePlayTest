/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entity.GameDocument;
import entity.Game;
/**
 *
 * @author Cyber Micro
 */
public class GameDocumentdao extends Connector{
    
    private GameDao gameLibDao;
    public List<GameDocument> findAll(int page_Num,int sarki_Sayisi,String aranacak,Long cat_id){   
        page_Num=(page_Num -1) * sarki_Sayisi;
        List<GameDocument> docList = new ArrayList<>();
        try {
            PreparedStatement pst;
            ResultSet rs = null;
            String query ="select * from game_document";                    
            if(aranacak !=null && cat_id == null){
            query += " where file_id in(select game_id from sarki where tmp_name like ? ) order by file_id asc limit ?,? ";
            pst = this.getCon().prepareStatement(query);
            pst.setString(1, "%"+aranacak+"%");
            pst.setInt(2, page_Num);
            pst.setInt(3, sarki_Sayisi);
            rs = pst.executeQuery();
            }
            else if(aranacak ==null && cat_id != null){
            query += " where file_id in(select game_id from game_category where category_id = ? ) order by file_id asc limit ?,? ";
            pst = this.getCon().prepareStatement(query);
            pst.setLong(1, cat_id);
            pst.setInt(2, page_Num);
            pst.setInt(3, sarki_Sayisi);
            rs = pst.executeQuery();
            }
            else if(aranacak !=null && cat_id != null){
            query += " where file_id in(select game_id from sarki where tmp_name like ? )"
                    + " and file_id in( select game_id from game_category where category_id = ?) order by file_id asc limit ?,? ";
            pst = this.getCon().prepareStatement(query);
            pst.setString(1, "%"+aranacak+"%");
            pst.setLong(2, cat_id);
            pst.setInt(3, page_Num);
            pst.setInt(4, sarki_Sayisi);
            rs = pst.executeQuery();
            }
            else{
            query += " order by file_id asc limit ?,? ";
            pst = this.getCon().prepareStatement(query);
            pst.setInt(1, page_Num);
            pst.setInt(2, sarki_Sayisi);
            rs = pst.executeQuery();
            }                        
            while(rs.next()){
                GameDocument docment = new GameDocument();
                docment.setDoc_id(rs.getLong("file_id"));
                docment.setFileName(rs.getString("file_name"));
                docment.setFile_size(rs.getLong("file_size"));
                docment.setFilePath(rs.getString("file_path"));
                docment.setFileType(rs.getString("file_type"));
                docment.setGame(this.getGameDao().findGame(rs.getLong("file_id")));
                docList.add(docment);                
            }            
        } catch (Exception e) {
        }        
        return docList;
    }
    public int insert(GameDocument docment){
        int i = 0;
        try {
            PreparedStatement pst;
            String query ="insert into game_document(file_name,file_size,file_path,file_type) values(?,?,?,?)";
            pst = this.getCon().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, docment.getFileName());
            pst.setLong(2, docment.getFile_size());
            pst.setString(3, docment.getFilePath());
            pst.setString(4, docment.getFileType());
           i = pst.executeUpdate();
            
            Long gameId = null;
            ResultSet gk = pst.getGeneratedKeys();
            if (gk.next())
                gameId = gk.getLong(1);            
            docment.setDoc_id(gameId);
             this.getGameDao().create(docment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    public int update(GameDocument docment){
        int i = 0;
             Game s = docment.getGame();
             s.setTmp_name(docment.getFileName());
        try {            
            PreparedStatement pst;
            String query ="update game_document set file_name = ?,file_size = ?,file_path = ? , file_type = ? where file_id = ?";
            pst = this.getCon().prepareStatement(query); 
             pst.setString(1, docment.getFileName());
             pst.setLong(2, docment.getFile_size());
            pst.setString(3, docment.getFilePath());
            pst.setString(4, docment.getFileType());
            pst.setLong(5, docment.getDoc_id());
           i = pst.executeUpdate();            
           this.getGameDao().update(s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    
    public int delete(GameDocument docment){
        int i = 0;
        try {
            PreparedStatement pst;
            String query ="delete from game_document where file_id = ?";
            pst = this.getCon().prepareStatement(query);
            pst.setLong(1, docment.getDoc_id());
           i = pst.executeUpdate();            
           this.getGameDao().delete(docment.getGame());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    
        public int findCount(String aranacak,Long cat_id){           
        return this.getGameDao().findCount(aranacak,cat_id);
    }
    public GameDao getGameDao() {
        if(this.gameLibDao == null)
            this.gameLibDao = new GameDao();
        return gameLibDao;
    }

    public GameDocument find(long id) {
        GameDocument docment = null;
         try {
            PreparedStatement pst;
            String query ="select * from game_document where file_id = ?";
            pst = this.getCon().prepareStatement(query);
            pst.setLong(1,id);                       
            ResultSet rs = pst.executeQuery();
            rs.next();
                 docment = new GameDocument();
                docment.setDoc_id(rs.getLong("file_id"));
                docment.setFileName(rs.getString("file_name"));
                docment.setFile_size(rs.getLong("file_size"));
                docment.setFilePath(rs.getString("file_path"));
                docment.setFileType(rs.getString("file_type"));
                                                      
        } catch (Exception e) {
        }        
        return docment;
        
    }
}
