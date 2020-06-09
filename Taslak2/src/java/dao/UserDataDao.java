/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import entity.UserData;
import entity.User;

/**
 *
 * @author Cyber Micro
 */
public class UserDataDao extends Connector{

    private PrintDocumentDao printDao;    
    
    public UserData find(Long id) {
        UserData kullaniciBilgi = new UserData();
        try {
            String query = "select * from uyelik_yapan_bilgileri where id = ?";
            PreparedStatement pst = this.getCon().prepareStatement(query);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            kullaniciBilgi.setId(rs.getLong("id"));
            kullaniciBilgi.setUye_Yetki(rs.getLong("uye_id"));
            kullaniciBilgi.setTel_no(rs.getString("tel_no"));
            kullaniciBilgi.setName(rs.getString("name"));
            kullaniciBilgi.setLast_name(rs.getString("last_name"));            
            kullaniciBilgi.setLast_update(rs.getDate("last_update"));
            kullaniciBilgi.setResim(this.getResimDao().find(rs.getLong("id"))); // buraya dikkat comedıgım bır problem var */
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return kullaniciBilgi;
    }
    public Long create(User kullanici) {   
        Long id = null;
        try {
            String query = "insert into uyelik_yapan_bilgileri(uye_id,name,last_name,tel_no) values(?,?,?,?)";
            PreparedStatement pst = this.getCon().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            pst.setLong(1, kullanici.getYetki().getUye_Yetki());
            pst.setString(2, kullanici.getYetki().getName());
            pst.setString(3, kullanici.getYetki().getLast_name());
            pst.setString(4, kullanici.getYetki().getTel_no());
            pst.executeUpdate();            
            ResultSet gk = pst.getGeneratedKeys();
            if (gk.next()) {
                id = gk.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }        
        return id;
    }
    public void delete(Long id) {    
        int i = 0;
        try {
            String query = "delete from uyelik_yapan_bilgileri where id = ?";
            PreparedStatement pst = this.getCon().prepareStatement(query);
            pst.setLong(1, id);
           i =  pst.executeUpdate();
           if(i == 1)
               this.getResimDao().delete(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void update(User kullaniciBilgisi) {        
        try {
            String query = "update uyelik_yapan_bilgileri set name=? ,last_name= ?,tel_no=? where id = ? " ;
            PreparedStatement pst = this.getCon().prepareStatement(query);
            pst.setString(1, kullaniciBilgisi.getYetki().getName());
            pst.setString(2, kullaniciBilgisi.getYetki().getLast_name());
            pst.setString(3, kullaniciBilgisi.getYetki().getTel_no());
            pst.setLong(4, kullaniciBilgisi.getYetki().getId());
           pst.executeUpdate();            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public PrintDocumentDao getResimDao() {
        if(this.printDao == null)
            this.printDao = new PrintDocumentDao();
        return printDao;
    }    
}
