/**
 *
 * @author Cyber Micro
 */
package controller;

import dao.GameDevDao;
import entity.GameDev;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class GameDevController extends PaginitonController implements Serializable{
    private GameDev gamedev;
    private GameDevDao gdao;
    private List<GameDev> glist;
    @Inject
    private CountryController ulkeList;
    @Inject
    private GameLibController gamelibKontrol;
    @Inject
    private PrintDocumentController gamedev_profile;

    public GameDevController() {
        setDao(this.getSdao());
        setSayfa_Adet(2);
        setSayfa_Size(8);
    }
    
    public void create(){
        this.getSdao().create(this.getGameDev());
        this.gamedev = new GameDev();
    }
     public List<GameDev> getSlist() {
         this.glist = this.getSdao().findAll(getPage(),getSayfa_Size(),getSearchTerimi());
        return glist;
    }
     public List<GameDev> allGameDev() {
        return this.getSdao().findAll(1,this.getSdao().findCount(getSearchTerimi()),getSearchTerimi());
    }
     
    public void updatefrom(GameDev gamedev){
       this.gamedev = gamedev;
       
    }
    public void update() {
        if(this.getGameDev_profile().getEror_mesage() == null){
        this.getSdao().update(this.getGameDev());
        if(this.getGameDev().getGameDev_resim().getDoc_id() == null)
        this.getGameDev_profile().upload(this.gamedev.getGameDev_id() + 1000000);
        else
          this.getGameDev_profile().updatePath(this.getGameDev());
        this.gamedev = new GameDev();
        this.getGameDev_profile().resim_reset();
        }        
    }
    public void confirmDelete(GameDev gamedev){
        this.gamedev = gamedev;
    }
    public void delete(){
       this.getSdao().delete(this.getGameDev());
        this.gamedev = new GameDev();
        this.getGameDev_profile().resim_reset();
    }
    public void clearForm() {
        this.getGameDev_profile().resim_reset();
        this.gamedev = new GameDev();
    }

    public GameDev getGameDev() {
        if(this.gamedev == null)
            this.gamedev = new GameDev();
        return gamedev;
    }

    public GameDevDao getSdao() {
        if(this.gdao == null)
            this.gdao = new GameDevDao();
        return gdao;
    }

    public CountryController getUlkeList() {
        return ulkeList;
    }

    public GameLibController getAlbumKontrol() {
        return gamelibKontrol;
    }

    public PrintDocumentController getGameDev_profile() {
        return gamedev_profile;
    }

    
    
    
}
