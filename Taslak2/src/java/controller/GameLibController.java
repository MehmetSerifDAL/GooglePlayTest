package controller;


import controller.PaginitonController;
import dao.GameLibDao;
import entity.Gamelib;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
  
public class GameLibController extends PaginitonController implements Serializable {

    private List<Gamelib> albumList;

    private GameLibDao albumDao;

    private Gamelib album;

    public GameLibController() {
        setDao(this.getGamelibDao());
        setSayfa_Adet(2);
        setSayfa_Size(8);
    }
    
    
    public void updateform(Gamelib album) {
        this.album = album;
    }
    public void clearForm() {
        this.album = new Gamelib();
    }
    public void confirmDelete(Gamelib album) {
        this.album= album;
        
    }
    public List<Gamelib> getGamelibList() {
        this.albumList = this.getGamelibDao().findAll(getPage(),getSayfa_Size(),getSearchTerimi());
        return albumList;
    }
    public List<Gamelib> allGamelibList() {
        return this.getGamelibDao().findAll(1,this.getGamelibDao().findCount(getSearchTerimi()),getSearchTerimi());
    }
    public void setGamelibList(List<Gamelib> albumList) {
        this.albumList = albumList;
    }
    public GameLibDao getGamelibDao() {
        if (albumDao == null) {
            this.albumDao = new GameLibDao();
        }

        return albumDao;
    }

    public void setGamelibDao(GameLibDao albumDao) {
        this.albumDao = albumDao;
    }

    public Gamelib getGamelib() {
        if (album == null) {
            this.album = new Gamelib();
        }
        return album;
    }

    public void setGamelib(Gamelib album) {
        this.album = album;
    }

    public void create() {
        this.getGamelibDao().create(this.getGamelib());
        this.album = new Gamelib();

    }
    public void delete() {

        this.getGamelibDao().delete(this.getGamelib());
        this.album = new Gamelib();
    }

    public void update() {
        this.getGamelibDao().update(this.getGamelib());
        this.album = new Gamelib();

    }

}
