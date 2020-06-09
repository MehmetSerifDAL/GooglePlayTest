/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cyber Micro
 */
public class CategoryDao extends Connector{
     public int create(Category category){
         int a=0;
        try {
            PreparedStatement pst = this.getCon().prepareStatement("insert into category(name) values(?)");
            pst.setString(1, category.getName());
            a = pst.executeUpdate();
        } catch (SQLException e) {
        }
        return a;
    }
     public List<Category> findAll(int page_Num,int game_Sayisi,String aranack){
        List<Category> cList = new ArrayList<>();
        page_Num=(page_Num -1) * game_Sayisi;
        try {            
            String query = "select * from category ";
                if(aranack == null)
                   query += "order by category_id asc limit ?,?";
                else
                   query += "where name like ? order by category_id asc limit ?,?";
            PreparedStatement pst = this.getCon().prepareStatement(query);
            if(aranack == null){
            pst.setInt(1, page_Num);
            pst.setInt(2, game_Sayisi);
            }
            else{
            pst.setString(1, "%"+aranack+"%");
            pst.setInt(2, page_Num);
            pst.setInt(3, game_Sayisi);
            }
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Category cat = new Category();
                cat.setId(rs.getLong("category_id"));
                cat.setName(rs.getString("name"));
                cat.setLast_date(rs.getDate("last_update"));
                cList.add(cat);
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cList;
    }
    

    public int delete(Category cat) {
        int a=0;
        try {
           PreparedStatement pst = this.getCon().prepareStatement("delete from category where category_id=?");
            pst.setLong(1,+cat.getId());
            a = pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public int update(Category cat) {
        int a=0;
        try {
            PreparedStatement pst = this.getCon().prepareStatement("update category set name=? where category_id=?");
            pst.setString(1, cat.getName());
            pst.setLong(2,cat.getId() );
            a = pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

     public Category findCategory(Long category_id){
        Category cat = null;
        try {
            PreparedStatement pst;
            String query = "select * from category where category_id= ?" ;
            pst = this.getCon().prepareStatement(query);
            pst.setLong(1, category_id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            cat = new Category();
            cat.setId(rs.getLong("category_id"));
            cat.setName(rs.getString("name"));
            cat.setLast_date(rs.getDate("last_update"));
            
    }  catch (SQLException ex) {
            System.out.println(ex.getMessage());
       }
        return cat;
    }        
    public List<Category> getGame_category(Long game_id){
        List<Category> cList =new ArrayList<>();
        try {
           PreparedStatement pst ;
           String query = "select * from game_category where game_id = ?";
           pst= this.getCon().prepareStatement(query);
           pst.setLong(1, game_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                cList.add(this.findCategory(rs.getLong("category_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cList;
                
    }
    
    
    public int findCount(String aranacak) {
        int count = 0;
        String query = "select count(category_id) as category_count from category";
        PreparedStatement st ;
        try {
            if(aranacak == null)
           st = this.getCon().prepareStatement(query);
            else{
            st = this.getCon().prepareStatement(query+" where name like ? "); 
            st.setString(1, "%"+aranacak+"%");
            }
            ResultSet rs = st.executeQuery();
           rs.next(); 
               count = rs.getInt("category_count");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

}
