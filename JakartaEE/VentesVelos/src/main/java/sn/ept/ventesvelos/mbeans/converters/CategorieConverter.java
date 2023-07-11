package sn.ept.ventesvelos.mbeans.converters;

import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sn.ept.ventesvelos.entites.Categorie;
import sn.ept.ventesvelos.facades.CategorieFacade;

@FacesConverter(value = "categorieConverter", managed = true)
public class CategorieConverter implements Converter {

    @EJB
    private CategorieFacade categorieFacade;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Categorie) {
            Categorie categorie = (Categorie) value;
            return String.valueOf(categorie.getId());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            try {
                Integer id = Integer.parseInt(value);
                return categorieFacade.find(id);
            } catch (NumberFormatException e) {
                // Handle conversion exception
            }
        }
        return null;
    }
}
