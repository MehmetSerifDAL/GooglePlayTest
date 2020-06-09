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
public class GameDev {
     private Long GameDev_id;
    @NotEmpty(message="Name alani bos birakilamaz...")
    private String name;
    @NotEmpty(message="Surname alani bos birakilamaz...")
    private String last_name;
    private int yas;
    private List<Gamelib> GameDevGameLib;
    private PrintDocument gameDev_resim;
    private Date last_update;
    private Country country;

    @Override
    public String toString() {
        return "GameDev{" + "GameDev_id=" + GameDev_id + ", name=" + name + ", last_name=" + last_name + ", yas=" + yas + ", GameDevGameLib=" + GameDevGameLib + ", gameDev_resim=" + gameDev_resim + ", last_update=" + last_update + ", country=" + country + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.GameDev_id);
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
        final GameDev other = (GameDev) obj;
        if (!Objects.equals(this.GameDev_id, other.GameDev_id)) {
            return false;
        }
        return true;
    }

    public GameDev(Long GameDev_id, String name, String last_name, int yas, List<Gamelib> GameDevGameLib, PrintDocument gameDev_resim, Date last_update, Country country) {
        this.GameDev_id = GameDev_id;
        this.name = name;
        this.last_name = last_name;
        this.yas = yas;
        this.GameDevGameLib = GameDevGameLib;
        this.gameDev_resim = gameDev_resim;
        this.last_update = last_update;
        this.country = country;
    }

    public GameDev() {
    }

    public Long getGameDev_id() {
        return GameDev_id;
    }

    public void setGameDev_id(Long GameDev_id) {
        this.GameDev_id = GameDev_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getYas() {
        return yas;
    }

    public void setYas(int yas) {
        this.yas = yas;
    }

    public List<Gamelib> getGameDevGameLib() {
        return GameDevGameLib;
    }

    public void setGameDevGameLib(List<Gamelib> GameDevGameLib) {
        this.GameDevGameLib = GameDevGameLib;
    }

    public PrintDocument getGameDev_resim() {
        return gameDev_resim;
    }

    public void setGameDev_resim(PrintDocument gameDev_resim) {
        this.gameDev_resim = gameDev_resim;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
 
}
