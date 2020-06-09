/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Cyber Micro
 */
public class UserDataGameLib {
        private Long id;
    private Long kullanici_id;
    private Game game;
    private Date last_update;

    public UserDataGameLib() {
    }

    public UserDataGameLib(Long id, Long kullanici_id, Game game, Date last_update) {
        this.id = id;
        this.kullanici_id = kullanici_id;
        this.game = game;
        this.last_update = last_update;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKullanici_id() {
        return kullanici_id;
    }

    public void setKullanici_id(Long kullanici_id) {
        this.kullanici_id = kullanici_id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
    

}
