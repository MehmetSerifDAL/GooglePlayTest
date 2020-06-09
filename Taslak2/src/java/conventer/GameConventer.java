/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conventer;

import dao.GameDao;
import entity.Game;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Cyber Micro
 */
public class GameConventer implements Converter{

    private GameDao gamedao;
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return this.getGameDao().findGame(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        Game sarki = (Game) arg2;
        return sarki.getGame_id().toString();
    }

    public GameDao getGameDao() {
        if(this.gamedao == null)
            this.gamedao = new GameDao();
        return gamedao;
    }
    
}
