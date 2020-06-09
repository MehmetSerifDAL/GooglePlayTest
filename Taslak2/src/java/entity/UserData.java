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
public class UserData {
   private Long id;
    private Long uye_Yetki;
    private String tel_no;
    private String name;
    private String last_name;
    private Date last_update;
    private PrintDocument resim;

    public PrintDocument getResim() {
        return resim;
    }

    public void setResim(PrintDocument resim) {
        this.resim = resim;
    }

    @Override
    public String toString() {
        return "UserData{" + "id=" + id + ", uye_Yetki=" + uye_Yetki + ", tel_no=" + tel_no + ", name=" + name + ", last_name=" + last_name + ", last_update=" + last_update + ", resim=" + resim + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final UserData other = (UserData) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public UserData() {
    }

    public UserData(Long id, Long uye_Yetki, String tel_no, String name, String last_name, Date last_update) {
        this.id = id;
        this.uye_Yetki = uye_Yetki;
        this.tel_no = tel_no;
        this.name = name;
        this.last_name = last_name;
        this.last_update = last_update;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUye_Yetki() {
        return uye_Yetki;
    }

    public void setUye_Yetki(Long uye_Yetki) {
        this.uye_Yetki = uye_Yetki;
    }

    public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
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

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
    
    
}
