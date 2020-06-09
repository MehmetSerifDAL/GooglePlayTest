
package conventer;

import dao.GameDevDao;
import entity.GameDev;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
@FacesConverter (value = "sarkiciConverter" )

public class GameDevConventer implements Converter{

    private GameDevDao gameDev;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return this.getSarkiciDao().findGameDev(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        GameDev gameDev = (GameDev)arg2;
        return gameDev.getGameDev_id().toString();
    }

    public GameDevDao getSarkiciDao() {
        if(this.gameDev == null)
            this.gameDev = new GameDevDao();
        return gameDev;
    }
    
}
