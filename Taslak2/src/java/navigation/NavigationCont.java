/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navigation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Cyber Micro
 */
//yonlendırme olarak burada bır sınıf atanmıstır.(Grup arkadaşlarıma)
@Named
@RequestScoped
public class NavigationCont {
     public String pageA( String page){
        return "/site/admin/"+page+"/"+page+"?faces-redirect=true";
    }
    
    public String pageK( String page){
        return "/site/user/"+page+"/"+page+"?faces-redirect=true";
    }
}
