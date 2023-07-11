package sn.ept.ventesvelos.mbeans.converters;

import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sn.ept.ventesvelos.entites.Marque;
import sn.ept.ventesvelos.facades.MarqueFacade;

@FacesConverter(value="marqueConverter", managed = true)
public class MarqueConverter implements Converter {

    @EJB
    private MarqueFacade marqueFacade;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Marque) {
            Marque marque = (Marque) value;
            return String.valueOf(marque.getId());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            try {
                Integer id = Integer.parseInt(value);
                return marqueFacade.find(id);
            } catch (NumberFormatException e) {
                // Handle conversion exception
            }
        }
        return null;
    }
}
