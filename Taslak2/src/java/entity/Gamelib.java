/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Cyber Micro
 */
public class Gamelib {
    private Long Gamelib_id;
    @NotEmpty(message="Bu alan bos birakilamaz...")
    private String name;
    private Date gamelib_LastUpdate;

    public Gamelib(Long Gamelib_id, String name, Date gamelib_LastUpdate) {
        this.Gamelib_id = Gamelib_id;
        this.name = name;
        this.gamelib_LastUpdate = gamelib_LastUpdate;
    }

    public Gamelib() {
    }

    public Long getGamelib_id() {
        return Gamelib_id;
    }

    public void setGamelib_id(Long Gamelib_id) {
        this.Gamelib_id = Gamelib_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAlbum_LastUpdate() {
        return gamelib_LastUpdate;
    }

    public void setAlbum_LastUpdate(Date gamelib_LastUpdate) {
        this.gamelib_LastUpdate = gamelib_LastUpdate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.Gamelib_id);
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
        final Gamelib other = (Gamelib) obj;
        if (!Objects.equals(this.Gamelib_id, other.Gamelib_id)) {
            return false;
        }
        return true;
    }
    
    
}
