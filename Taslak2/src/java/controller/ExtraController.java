/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ExtraDao;
import entity.Extra;
import entity.Game;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped//Bizim için bu yeterli 
public class ExtraController extends PaginitonController implements Serializable{
    
    private Extra extra;
    private Extra ozet; // kullanici tarafinda her gamenin tüm bilgilerini yazdirmak için
    private List<Extra> exList;
    private ExtraDao extraDao;
    private int sonuc;

    @Inject
    private GameController gameContriller;
    
    @Inject
    private CountryController ulkeList;

    public ExtraController() {
        setDao(this.getExtraDao());
        setSayfa_Adet(2);
        setSayfa_Size(8);
    }
    
    public void updateform(Extra extra){
        this.extra = extra;
        this.sonuc = -1;
    }
    public void confirmDelete(Extra extra){
        this.extra = extra;
        this.sonuc = -1;
    }
     public void clearForm() {
        this.extra = new Extra();
        this.sonuc = -1;
    }
    public void delete(){
        this.sonuc = this.getExtraDao().delete(this.getExtra());
        this.extra = new Extra();
    }
    public void create(){
        this.sonuc = this.getExtraDao().create(this.getExtra());
        this.extra = new Extra();
    }
    public void update(){
       this.sonuc = this.getExtraDao().update(this.getExtra());
        this.extra = new Extra();
    }
    public Extra getExtra() {
        if(this.extra == null)
            this.extra = new Extra();
        return extra;
    }
    
    public void gameOzeti(Game game){
        Extra bilgi = this.getExtraDao().find(game.getGame_id());
        this.ozet = bilgi;
    }
    public Extra gameOzeti(){
        return ozet;
    }
    public List<Extra> getExList() {
        this.exList = this.getExtraDao().findAll(getPage(),getSayfa_Size(),getSearchTerimi());
        return exList;
    }
    public List<Extra> allExtra(){
        return this.getExtraDao().findAll(1,this.getExtraDao().findCount(getSearchTerimi()),getSearchTerimi());
    }
    public void setExList(List<Extra> exList) {
        this.exList = exList;
    }

    
    public ExtraDao getExtraDao() {
        if(this.extraDao == null)
            this.extraDao = new ExtraDao();
        return extraDao;
    }

    public GameController getGameContriller() {
        return gameContriller;
    }

    public void setGameContriller(GameController gameContriller) {
        this.gameContriller = gameContriller;
    }

    public int getSonuc() {
        return sonuc;
    }

    public void setSonuc(int sonuc) {
        this.sonuc = sonuc;
    }

    
    public CountryController getUlkeList() {
        return ulkeList;
    }
    
    
}
