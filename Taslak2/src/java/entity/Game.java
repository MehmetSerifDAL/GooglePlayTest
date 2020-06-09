/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Cyber Micro
 */
public class Game {
     private Long game_id;
    @NotEmpty(message="1.)Bu alan bos birakilamaz!")    
    private String tmp_name;
    private String description;
    
    private int yil = 2020;
    private Language GameLan;
    private Gamelib gamelib;
    private List<Category> GameCategory;
    private List<GameDev> GameGameDev;
    private GameDocument GameDoc;
    private Date last_update;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.game_id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (!Objects.equals(this.game_id, other.game_id)) {
            return false;
        }
        return true;
    }

    public Long getGame_id() {
        return game_id;
    }

    public void setGame_id(Long game_id) {
        this.game_id = game_id;
    }

    public String getTmp_name() {
        return tmp_name;
    }

    public void setTmp_name(String tmp_name) {
        this.tmp_name = tmp_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYil() {
        return yil;
    }

    public void setYil(int yil) {
        this.yil = yil;
    }

    public Language getGameLan() {
        return GameLan;
    }

    public void setGameLan(Language GameLan) {
        this.GameLan = GameLan;
    }

    public Gamelib getGamelib() {
        return gamelib;
    }

    public void setGamelib(Gamelib gamelib) {
        this.gamelib = gamelib;
    }

    public List<Category> getGameCategory() {
        return GameCategory;
    }

    public void setGameCategory(List<Category> GameCategory) {
        this.GameCategory = GameCategory;
    }

    public List<GameDev> getGameGameDev() {
        return GameGameDev;
    }

    public void setGameGameDev(List<GameDev> GameGameDev) {
        this.GameGameDev = GameGameDev;
    }

    public GameDocument getGameDoc() {
        return GameDoc;
    }

    public void setGameDoc(GameDocument GameDoc) {
        this.GameDoc = GameDoc;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public Game() {
    }

    public Game(Long game_id, String tmp_name, String description, Language GameLan, Gamelib gamelib, List<Category> GameCategory, List<GameDev> GameGameDev, GameDocument GameDoc, Date last_update) {
        this.game_id = game_id;
        this.tmp_name = tmp_name;
        this.description = description;
        this.GameLan = GameLan;
        this.gamelib = gamelib;
        this.GameCategory = GameCategory;
        this.GameGameDev = GameGameDev;
        this.GameDoc = GameDoc;
        this.last_update = last_update;
    }
    
    
}
