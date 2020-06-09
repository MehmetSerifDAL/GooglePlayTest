/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conventer;

import dao.CategoryDao;
import entity.Category;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Cyber Micro
 */
public class CategoryConventer implements Converter {
     private CategoryDao categoryDao;
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return this.getCategoryDao().findCategory(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        Category category = (Category) arg2;
        return category.getId().toString();
    }

    public CategoryDao getCategoryDao() {
        if(this.categoryDao == null)
            this.categoryDao = new CategoryDao();
        return categoryDao;
    }
}
