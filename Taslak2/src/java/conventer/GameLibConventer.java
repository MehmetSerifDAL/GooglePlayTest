/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conventer;
import entity.Gamelib;
import dao.GameLibDao;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Cyber Micro
 */@FacesConverter (value ="albumConverter")
public class GameLibConventer implements Converter{
      private  GameLibDao gamelibdao;
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return this.getGamelibDAo().find_gamelibId(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        Gamelib album = (Gamelib) arg2;
        return album.getGamelib_id().toString();
    }

    public GameLibDao getGamelibDAo() {
        if(this.gamelibdao == null)
            this.gamelibdao = new GameLibDao();
        return gamelibdao;
    }
}
