/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GameDao;
import entity.Game;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped

/**
 *
 * @author Cyber Micro
 */
public class GameController extends PaginitonController implements Serializable {
    
    private Game game;
    private List<Game> glist;
    private GameDao gdao;
    private int sonuc;
    @Inject
    private LanguageController dilControl;
    
    @Inject
    private GameLibController gamelibControl;
    
    @Inject
    private CategoryController categoryControl;
    
    @Inject
    private GameDevController gameDevKontrol;
    
     private int sayfaSize = 6;
    private int sayfaAdet = 3;
    
    public GameController() {
        setSayfa_Adet(sayfaAdet);
        setSayfa_Size(sayfaSize);
        setDao(this.getSdao());
        this.sonuc = -1;
    }    
    public Game getGame() {
        if (this.game == null) {
            this.game = new Game();
        }
        return game;
    }

    public List<Game> getSlist() {
        this.glist = this.getSdao().findAll(getPage(),getSayfa_Size(),getSearchTerimi(),getId());
        return glist;
    }
    
    public List<Game> allSong(){
        return this.getSdao().findAll(1, this.getSdao().findCount(getSearchTerimi(),getId()),getSearchTerimi(),null);
    }
    
    public void updateForm(Game game) {
        this.game = game;        
    }
    
    public void update() {
        this.sonuc = this.getSdao().update(this.getGame());
        this.game = new Game();
        
    }       
    public GameDao getSdao() {
        if (this.gdao == null) {
            this.gdao = new GameDao();
        }
        return gdao;
    }
    
    public int getSonuc() {
        return sonuc;
    }
    
    public void setSonuc(int sonuc) {
        this.sonuc = sonuc;
    }
    
    public LanguageController  getDilControl() {
        return dilControl;
    }
    
    public void setDilControl(LanguageController dilControl) {
        this.dilControl = dilControl;
    }
    
    public GameLibController getAlbumControl() {
        return gamelibControl;
    }
    
    public void setAlbumControl(GameLibController gamelibControl) {
        this.gamelibControl = gamelibControl;
    }

    public CategoryController getCategoryControl() {
        return categoryControl;
    }

    public GameDevController getGameDevKontrol() {
        return gameDevKontrol;
    }
    
}
