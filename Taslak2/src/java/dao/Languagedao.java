/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import entity.Language;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cyber Micro
 */
public class Languagedao extends Connector {

      public List<Language> findAll(int page_Num,int sarki_Sayisi,String aranacak) {
        List<Language> dlist = new ArrayList<>();
        page_Num=(page_Num -1) * sarki_Sayisi;
        String query = "select * from language";
        try {
            PreparedStatement  pst;
            if(aranacak != null){
                query +=" where name like ? order by language_id asc limit ?,?";
            pst=this.getCon().prepareStatement(query);
            pst.setString(1, "%"+aranacak+"%");
            pst.setInt(2, page_Num);
            pst.setInt(3, sarki_Sayisi); 
            }
            else{
             query += " order by language_id asc limit ?,?";
            pst=this.getCon().prepareStatement(query);
            pst.setInt(1, page_Num);
            pst.setInt(2, sarki_Sayisi);
            }
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Language language = new Language();
                language.setDil_id(rs.getLong("language_id"));
                language.setDil_name(rs.getString("name"));
                language.setLast_update(rs.getDate("last_update"));
                dlist.add(language);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dlist;
    }

    public int creat(Language language) {
       
           int i = 0;
        try {
            PreparedStatement pst = this.getCon().prepareStatement("insert into language (name) values(?)");
            pst.setString(1, language.getDil_name());
             i = pst.executeUpdate();
        } catch (Exception e) {
        }
        return i;
    }

    public void update(Language language) {
        try {
            PreparedStatement pst = this.getCon().prepareStatement("update language set name=? where language_id=?");
            pst.setString(1, language.getDil_name());
            pst.setLong(2,language.getDil_id());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Language language) {
         try {
            PreparedStatement pst = this.getCon().prepareStatement("delete from language where language_id=?");
            pst.setLong(1,language.getDil_id());
            pst.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
      public Language find_Id(Long id) {
        Language l = null;

        try {
            PreparedStatement pst = this.getCon().prepareStatement("select * from language where language_id=?");
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            l = new Language();
            l.setDil_id(rs.getLong("language_id"));
            l.setDil_name(rs.getString("name"));
            l.setLast_update(rs.getDate("last_update"));
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return l;
    }

      public int findCount(String aranacak) {
        int count = 0;
        String query = "select count(language_id) as language_count from language";
        try {
            PreparedStatement st;
            if(aranacak != null){
            query +=" where name like ?";
            st=this.getCon().prepareStatement(query);
            st.setString(1, "%"+aranacak+"%");
            }
            else
            st = this.getCon().prepareStatement(query);            
            ResultSet rs = st.executeQuery();
           rs.next(); 
               count = rs.getInt("language_count");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }
      

}
