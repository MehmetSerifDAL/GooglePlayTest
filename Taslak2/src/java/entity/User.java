/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;

/**
 *
 * @author Cyber Micro
 */
public class User {
        private Long Kullanici_id;
    private String username;
    private String password;    
    private UserData yetki;// her kullanicinin kendi bilgilerini ve yetki seviyesini içerir

    @Override
    public String toString() {
        return "User{" + "Kullanici_id=" + Kullanici_id + ", username=" + username + ", password=" + password + ", yetki=" + yetki + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.Kullanici_id);
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
        final User other = (User) obj;
        return true;
    }

    public User() {
    }

    public User(Long Kullanici_id, String username, String password, UserData yetki) {
        this.Kullanici_id = Kullanici_id;
        this.username = username;
        this.password = password;
        this.yetki = yetki;
    }

    public Long getKullanici_id() {
        return Kullanici_id;
    }

    public void setKullanici_id(Long Kullanici_id) {
        this.Kullanici_id = Kullanici_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserData getYetki() {
        return yetki;
    }

    public void setYetki(UserData yetki) {
        this.yetki = yetki;
    }
    
}
