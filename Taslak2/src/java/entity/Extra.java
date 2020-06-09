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
public class Extra {
      private Long id;
    private Game game;
    private Country ulke;
    private float game_puan;
    private float game_sure;
    private String game_sirketi;
    private Date last_update;

    @Override
    public String toString() {
        return "Extra{" + "id=" + id + ", game=" + game + ", ulke=" + ulke + ", game_puan=" + game_puan + ", game_sure=" + game_sure + ", game_sirketi=" + game_sirketi + ", last_update=" + last_update + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Country getUlke() {
        return ulke;
    }

    public void setUlke(Country ulke) {
        this.ulke = ulke;
    }

    public float getGame_puan() {
        return game_puan;
    }

    public void setGame_puan(float game_puan) {
        this.game_puan = game_puan;
    }

    public float getGame_sure() {
        return game_sure;
    }

    public void setGame_sure(float game_sure) {
        this.game_sure = game_sure;
    }

    public String getGame_sirketi() {
        return game_sirketi;
    }

    public void setGame_sirketi(String game_sirketi) {
        this.game_sirketi = game_sirketi;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public Extra() {
    }

    public Extra(Long id, Game game, Country ulke, float game_puan, float game_sure, String game_sirketi, Date last_update) {
        this.id = id;
        this.game = game;
        this.ulke = ulke;
        this.game_puan = game_puan;
        this.game_sure = game_sure;
        this.game_sirketi = game_sirketi;
        this.last_update = last_update;
    }
    
}
