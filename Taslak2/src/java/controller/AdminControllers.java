/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDao;
import entity.User;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
@Named
@ViewScoped//Bizim için bu yeterli 

public class AdminControllers extends PaginitonController implements Serializable{
    private User admin;
    private List<User> adminList;
    private UserDao adminDao;
    private int sonuc; // database bize return edeceği sonucu gostermek icin
    
    @Inject
    private NewSignController kullanici;
    
    public AdminControllers() {     
        this.setDao(new UserDao());
        setSayfa_Size(6); 
        this.setYetki_saviye(1);
        this.sonuc = -1;
    }  
        public void updateForm(User kullanici){
        this.admin = kullanici;
        this.sonuc = -1;        
    }    
    public void clearForm(){
        this.admin = new User();
        this.sonuc = -1;
    }
    public void create(){
        this.sonuc = this.getAdminDao().create(this.getAdmin());
        this.admin = new User();        
    }
    public void update(){
       this.getKullanici().setUpdateToUser(this.getAdmin());       
        this.getKullanici().update2();
        this.sonuc = this.getKullanici().getSonuc();
       if(this.sonuc == 1){           
        this.admin = new User(); 
       }
       else
           this.sonuc = 0;
    }
    public String delete(){      
            this.getAdminDao().delete(this.getAdmin());         
             return "/logout.xhtml?faces-redirect=true";      
    }
    public void deleteFromSuper(User oneAdmin){
         this.getAdminDao().delete(oneAdmin);
    }
     public List<User> getAdminList() {
         this.adminList = this.getAdminDao().findAll(getPage(), getSayfa_Size(),1,getSearchTerimi());                 
         return adminList;
         
    }
     
    public User getAdmin() {
        if(this.admin == null)
            this.admin = new  User();
        return admin;
    }

    public UserDao getAdminDao() {
        if(this.adminDao == null)
            this.adminDao = new UserDao();
        return adminDao;
    }

    public int getSonuc() {
        return sonuc;
    }

    public void setSonuc(int sonuc) {
        this.sonuc = sonuc;
    }
    public NewSignController getKullanici() {
        return kullanici;
    }
}
