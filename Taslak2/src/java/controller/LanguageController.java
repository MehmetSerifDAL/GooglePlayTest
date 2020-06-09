/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Languagedao;
import entity.Language;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
@Named
@SessionScoped
/**
 *
 * @author Cyber Micro
 */
public class LanguageController extends PaginitonController implements Serializable{
  private Languagedao dildao;
  private List<Language> dlist;
  private Language dil;

    public LanguageController() {
        setDao(this.getDildao());
        setSayfa_Adet(2);
        setSayfa_Size(8);
    }
  
  public void delete()
  {
      this.dildao.delete(this.dil);
      this.dil=new Language();
  }
  
  public void deleteConfirm(Language dil){
      this.dil=dil;
  }
  
  public void clearForm()
  {
      this.dil=new Language();
  }
  public void creat()
  {
      this.dildao.creat(dil);
      this.dil=new Language();
  }
   public void updateForm(Language dil){
       this.dil=dil;
   }
   public void update(){
       this.dildao.update(this.dil);
       this.dil=new Language();
   }
    public Languagedao getDildao() {
        if(this.dildao==null)
            this.dildao=new Languagedao();
        return dildao;
    }

    public void setDildao(Languagedao dildao) {
        this.dildao = dildao;
    }

    public List<Language> getDlist() {
        this.dlist=this.getDildao().findAll(getPage(),getSayfa_Size(),getSearchTerimi());
        return dlist;
    }
    public List<Language> allDil() {
        return this.getDildao().findAll(1,this.getDildao().findCount(getSearchTerimi()),getSearchTerimi());
    }
    public void setDlist(List<Language> dlist) {
        this.dlist = dlist;
    }
    
    public Language getDil() {
        if(this.dil==null){
            this.dil=new Language();
        }
        return dil;
    }

    public void setDil(Language dil) {
        this.dil = dil;
    }
  
}
