package sn.ept.ventesvelos.mbeans.converters;

import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sn.ept.ventesvelos.entites.Client;
import sn.ept.ventesvelos.facades.ClientFacade;


@FacesConverter(value="clientConverter", managed = true)
public class ClientConverter implements Converter {
    @EJB
    private ClientFacade clientFacade;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Client) {
            Client client = (Client) value;
            return String.valueOf(client.getId());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            try {
                Integer id = Integer.parseInt(value);
                return clientFacade.find(id);
            } catch (NumberFormatException e) {
                // Handle conversion exception
            }
        }
        return null;
    }
}
