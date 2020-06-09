/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CountryDao;
import entity.Country;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped

public class CountryController extends PaginitonController implements Serializable{
    private Country ulkeSehir;
    private CountryDao udao;
    private List<Country> ulist;

    public CountryController() {
        setDao(this.getUdao());
        setSayfa_Adet(2);
        setSayfa_Size(8);
    }
    public void create(){
        this.getUdao().create(this.getUlkeSehir());
        this.ulkeSehir = new Country();
    }
    public void updatefrom(Country ulkeSehir){
       this.ulkeSehir = ulkeSehir;
    }
    public void update(){
        this.getUdao().update(this.getUlkeSehir());
        this.ulkeSehir = new Country();
    }
    public void confirmDelete(Country ulkeSehir){
        this.ulkeSehir = ulkeSehir;
    }
    public void delete(){
        this.getUdao().delete(this.getUlkeSehir());
        this.ulkeSehir = new Country();
    }
    public void clearForm() {
        this.ulkeSehir = new Country();
    }
    public List<Country> getUlist() {
        this.ulist = this.getUdao().findAll(getPage(),getSayfa_Size(),getSearchTerimi());
        return ulist;
    }
    public List<Country> allUlke() {
        return this.getUdao().findAll(1,this.getUdao().findCount(getSearchTerimi()),getSearchTerimi());
    }
    public Country getUlkeSehir() {
        if(ulkeSehir == null)
            this.ulkeSehir = new Country();
        return ulkeSehir;
    }

    public CountryDao getUdao() {
        if(this.udao == null)
            this.udao = new CountryDao();
        return udao;
    }    
}
