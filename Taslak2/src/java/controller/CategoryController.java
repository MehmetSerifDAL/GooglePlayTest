/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Category;
import dao.CategoryDao;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped

public class CategoryController extends PaginitonController implements Serializable {

    private List<Category> clist;
    private Category category;
    private CategoryDao cdao;
    private int a ;

    public CategoryController() {
        setDao(this.getCdao());
        setSayfa_Adet(2);
        setSayfa_Size(8);
        this.a = -1;
    }        
    public void updateform(Category cat) {
        this.a=-1;
        this.category = cat;
    }

    public void clearForm() {
        this.category = new Category();
        this.a=-1;
    }

    public void update() {
        this.a=this.getCdao().update(this.getCategory());
        this.category = new Category();
    }

    public void confirmDelete(Category cat) {
        this.a=-1;
        this.category = cat;

    }

    public void delete() {
       this.a=this.getCdao().delete(this.getCategory());
        this.category = new Category();
    }

    public void create() {
       this.a= this.getCdao().create(this.getCategory());
        this.category = new Category();
    }

    public List<Category> getClist() {
        this.clist = this.getCdao().findAll(getPage(),getSayfa_Size(),getSearchTerimi());      
        return clist;
    }
    public List<Category> allClist() {
        return this.getCdao().findAll(1,this.getCdao().findCount(getSearchTerimi()),getSearchTerimi());
    }
    
    public String find(Long id){
        if(id != null)
        return this.cdao.findCategory(id).getName();
        else 
            return "All";
    }
     
    public CategoryDao getCdao() {        
        if (cdao == null) {
            this.cdao = new CategoryDao();
        }
        return cdao;
    }
    public Category getCategory() {       
        if (this.category == null) {
            this.category = new Category();
        }
        return category;
    }
    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

   
}
