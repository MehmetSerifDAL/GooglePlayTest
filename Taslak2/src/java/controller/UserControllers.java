/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDao;
import entity.User;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import navigation.NavigationCont;

/**
 *
 * @author Cyber Micro
 */
@Named
@SessionScoped
public class UserControllers implements Serializable{
    private String usert;    // girilecek usertname
    private String pass;    // girilecek password
    private User kullanici;
    private UserDao kullaniciDao;
    
    private NavigationCont kullaniciNav;
    private String mesaj;// login sayfasina hatali mesaj return etmek icin
    
    public UserControllers() {        
        this.kullaniciNav = new NavigationCont();
    }
    
    public String login(){       
        if(this.getUser().equals(this.getUser().getUsername()) && this.getPass().equals(this.getUser().getPassword()) ){
            if(this.getUser().getYetki().getUye_Yetki() > 0){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("valid_admin",this.getUser());         
            return kullaniciNav.pageK("oyunlar");
            }
            else{
             FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("valid_usert",this.getUser());
             return kullaniciNav.pageK("oyunlar");
            }            
        }
        else{                       
            this.mesaj = "Geçersiz username veya password - Kullanıcı değilseniz lütfen üye olunuz..";
            return "/GooglePlay/login?faces-redirect=true";
        }
    }    
    public User getUser() {               
        this.kullanici = this.getUserDao().find(this.getUsert(),this.getPass()); 
            return kullanici;
        
    }
    
    public String getUsert() {
        return usert;
    }

    public void setUser(String usert) {
        this.usert = usert;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }    
    public UserDao getUserDao() {
        if(this.kullaniciDao == null)
            this.kullaniciDao = new UserDao();
        return kullaniciDao;
    }        
    public String getMesaj() {
        return mesaj;
    }
    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public NavigationCont getUserNav() {
        return kullaniciNav;
    }

    public void setUserNav(NavigationCont kullaniciNav) {
        this.kullaniciNav = kullaniciNav;
    }
    
}
