/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Cyber Micro
 */
public class Language {
       private Long dil_id;
    private String dil_name;
    private Date last_update;

    @Override
    public String toString() {
        return "Dil:{" + "dil_id=" + dil_id + ", dil_name=" + dil_name + ", last_update=" + last_update + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.dil_id);
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
        final Language other = (Language) obj;
        if (!Objects.equals(this.dil_id, other.dil_id)) {
            return false;
        }
        return true;
    }

    public Long getDil_id() {
        return dil_id;
    }

    public void setDil_id(Long dil_id) {
        this.dil_id = dil_id;
    }

    public String getDil_name() {
        return dil_name;
    }

    public void setDil_name(String dil_name) {
        this.dil_name = dil_name;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public Language(Long dil_id, String dil_name, Date last_update) {
        this.dil_id = dil_id;
        this.dil_name = dil_name;
        this.last_update = last_update;
    }

    public Language() {
    }
}
