/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conventer;

import dao.CountryDao;
import entity.Country;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Cyber Micro
 */
public class CountryConventer implements Converter{
    private CountryDao udao;
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return this.getUdao().findulkeBy_id(Long.valueOf(value));
        
    }
    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        Country ulke = (Country) arg2;
        return ulke.getUlke_id().toString();
        
    }
    public CountryDao getUdao() {
        if(this.udao == null)
            this.udao = new CountryDao();
        return udao;
    }
}
