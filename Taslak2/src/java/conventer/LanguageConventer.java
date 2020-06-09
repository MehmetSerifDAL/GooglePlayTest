/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conventer;
import dao.Languagedao;
import entity.Language;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Cyber Micro
 */
public class LanguageConventer implements Converter{

    private Languagedao dildao;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return this.getDildao().find_Id(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        Language dil = (Language) arg2;
        return dil.getDil_id().toString();
    }

    public Languagedao getDildao() {
        if(this.dildao == null)
            this.dildao = new Languagedao();
        return dildao;
    }

  
}
