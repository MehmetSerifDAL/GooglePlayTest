/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.*;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Cyber Micro
 */

public class PaginitonController {
    // page : 1 baslar
    //count : tüm nesnelerin sayisi                   

    private int page_count; // ceil(count / sayfa_size); olusacak sayfa sayisi
    private int page = 1;
    private int sayfa_Adet; //page count^'u peryodik olarak bolersek peryod uzunlugunu gosteriyor  
    private int sayfa_Size; //her sayfa içinde kactane nesne gozukecek
    private Object dao;//gelen nesneyi instaceof ile sorgulayarak dogru dao nesneye cast ederek o dao sınıf icindeki findCount metodunu cağırmak icin
    private String searchTerimi = null;  //arama yapilacaği zaman girilecek arama metni tutar       
    private int yetki_saviye = 1;

    private Long id = null;

    @Inject
    NewSignController user;

    UserDao kullaniciDao;

    public int sayfaNumarasi() {
        /*
        bu metod sayfa numaralarini belirlemek icin kullaniliyor sayfa peryodik bir yapida örneğin : [0,5,10,...,25,..]
        ilerleyip yada geridönecek , yapilmasi gereken ilk adim ben hangi peryota geldiğimi bilmektir
        sonra o periyot degerini sayfa_Adet ile carpip sayfa numarasi hangi periyotta olduğu bilinmis olur
        son adim jsf pagination sayfalarda o periyodun 1. sayfasini(periyot + 1) ,2.sayfayi (periyot + 2 ),...... belirlemiş oluruz
         */
        int x = (this.page - 1) / getSayfa_Adet();// periyodu belirliyor ona gore yonlendiremm sağlanmış oalcaktır
        return x *= getSayfa_Adet();
    }

    public boolean sayfa_Bos_Mu() {
        if (this.page <= this.getPage_count()) {
            return false;
        } else {
            return true;
        }
    }

    public void categoryA() {
        page = 1;
        this.setId(4L);
    }

    public void categoryB() {
        page = 1;
        this.setId(6L);
    }

    public void categoryC() {
        page = 1;
        this.setId(7L);
    }

    public void categoryD() {
        page = 1;
        this.setId(8L);
    }

    public void categoryE() {
        page = 1;
        this.setId(9L);
    }

    public void categoryF() {
        page = 1;
        this.setId(10L);
    }

    public void hepsi() {
        page = 1;
        this.setId(null);
        this.setSearchTerimi(null);
    }

    public void search() {
        page = 1;
    }

    public void clearSearch() {
        page = 1;
        this.setSearchTerimi(null);
        this.setId(null);
    }

    public void next() {
        if (page >= this.getPage_count()) {
            this.page = 1;// son sayfa yada bos bir sayfaya geldiysem next basilinca ilk sayfaya gitsin yani sayfalar arasında dongu olsun
        } else {
            page++;
        }
    }

    public void prev() {
        if (page == 1) {
            page = this.getPage_count();
        } else {
            if (this.getPage_count() != 0)// bir yerde hiç bir nesne yoksa negetif degerlere dusmesin
            {
                page--;
            }
        }
    }

    private void hangiSayfaya_Gidecegim(int _sayfaNum) {
        if (page % getSayfa_Adet() != 0 && this.getPage_count() != 0) {
            page -= this.page % getSayfa_Adet();
            page += _sayfaNum;
        } else {
            if (this.getPage_count() != 0)// bir yerde hiç bir nesne yoksa negetif degerlere dusmesin
            {
                this.page -= getSayfa_Adet();
            }
            page += _sayfaNum;
        }
    }

    public void first() {
        this.hangiSayfaya_Gidecegim(1);
    }

    public void second() {
        this.hangiSayfaya_Gidecegim(2);
    }

    public void third() {
        this.hangiSayfaya_Gidecegim(3);
    }

    public int getPage_count() {
        int count = 0;
        if (getDao() instanceof GameLibDao) {
            count = ((GameLibDao) getDao()).findCount(getSearchTerimi());
        } else if (getDao() instanceof CategoryDao) {
            count = ((CategoryDao) getDao()).findCount(this.getSearchTerimi());
         
        } else if (getDao() instanceof Languagedao) {
            count = ((Languagedao) getDao()).findCount(getSearchTerimi());
        } else if (getDao() instanceof ExtraDao) {
            count = ((ExtraDao) getDao()).findCount(getSearchTerimi());
        } else if (getDao() instanceof UserDao) {
            count = ((UserDao) getDao()).findCount(getSearchTerimi(), getYetki_saviye());
        } else if (getDao() instanceof UserDataGameLibDao) {
            count = ((UserDataGameLibDao) getDao()).findCount(this.getUser().getUpdateToUser().getKullanici_id(), getSearchTerimi());
        } else if (getDao() instanceof GameDocumentdao) {
            count = ((GameDocumentdao) getDao()).findCount(getSearchTerimi(), getId());
        } else if (getDao() instanceof GameDao) {
            count = ((GameDao) getDao()).findCount(getSearchTerimi(), getId());
        } else if (getDao() instanceof GameDevDao) {
            count = ((GameDevDao) getDao()).findCount(getSearchTerimi());
        } else if (getDao() instanceof CountryDao) {
            count = ((CountryDao) getDao()).findCount(getSearchTerimi());
        }

        this.page_count = (int) Math.ceil(count / (double) getSayfa_Size());
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getPage() {
        return page;
    }

    public int getSayfa_Adet() {
        return sayfa_Adet;
    }

    public void setSayfa_Adet(int sayfa_Adet) {
        this.sayfa_Adet = sayfa_Adet;
    }

    public Object getDao() {
        return dao;
    }

    public void setDao(Object dao) {
        this.dao = dao;
    }

    public void setSayfa_Size(int sayfa_Size) {
        this.sayfa_Size = sayfa_Size;
    }

    public int getSayfa_Size() {
        return sayfa_Size;
    }

    public UserDao getUserDao() {
        if (this.kullaniciDao == null) {
            this.kullaniciDao = new UserDao();
        }
        return kullaniciDao;
    }

    public NewSignController getUser() {
        return user;
    }

    public String getSearchTerimi() {
        return searchTerimi;
    }

    public void setSearchTerimi(String searchTerimi) {
        this.searchTerimi = searchTerimi;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setYetki_saviye(int yetki_saviye) {
        this.yetki_saviye = yetki_saviye;
    }

    public int getYetki_saviye() {
        return yetki_saviye;
    }

}
