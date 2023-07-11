package sn.ept.ventesvelos.mbeans.converters;

import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sn.ept.ventesvelos.entites.Employe;
import sn.ept.ventesvelos.facades.EmployeFacade;

@FacesConverter(value="employeConverter", managed = true)
public class EmployeConverter implements Converter {

    @EJB
    private EmployeFacade employeFacade;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Employe) {
            Employe employe = (Employe) value;
            return String.valueOf(employe.getId());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            try {
                Integer id = Integer.parseInt(value);
                return employeFacade.find(id);
            } catch (NumberFormatException e) {
                // Handle conversion exception
            }
        }
        return null;
    }
}
