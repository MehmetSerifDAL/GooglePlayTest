/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDataGameLibDao;
import entity.Game;
import entity.UserDataGameLib;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Cyber Micro
 */
@Named
@SessionScoped
public class UserDataGameLibController extends PaginitonController implements Serializable {
    private  UserDataGameLib kullaniciList;
    private List<UserDataGameLib> kullGameList;
    private UserDataGameLibDao kullGameDao;
        
    @Inject
    private NewSignController uye;
    
    public UserDataGameLibController() {
        this.kullaniciList = new UserDataGameLib();        
        setSayfa_Adet(3);
        setSayfa_Size(8);
        setDao(this.getKullgameDao());
    }
    
    public void create(Game game){// kullanici tarafindaki sarkilarda ekleme islemi yapar
        Long kullanici_id = this.getUye().getUpdateToUser().getKullanici_id();
        this.kullaniciList.setKullanici_id(kullanici_id);
        this.kullaniciList.setGame(game);
        this.getKullgameDao().create(this.getKullaniciList());
    }
    public void confirmDelete(UserDataGameLib k){
        this.kullaniciList = k;
    }
    public void delete(){
        this.getKullgameDao().delete(this.getKullaniciList());
    }
    public List<UserDataGameLib> getKullGameList() {
        Long kullanici_id = this.getUye().getUpdateToUser().getKullanici_id();
        this.kullGameList = this.getKullgameDao().findAll(kullanici_id,getPage(),getSayfa_Size(),getSearchTerimi());
        return kullGameList;
    }
     public UserDataGameLib getKullaniciList() {            
        return kullaniciList;
    }
    public UserDataGameLibDao getKullgameDao() {
        if(this.kullGameDao == null)
            this.kullGameDao = new UserDataGameLibDao();
        return kullGameDao;
    }
    public NewSignController getUye() {
        return uye;
    }   
}
