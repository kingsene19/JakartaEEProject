package sn.ept.ventesvelos.mbeans.converters;

import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sn.ept.ventesvelos.entites.Produit;
import sn.ept.ventesvelos.facades.ProduitFacade;


@FacesConverter(value = "produitConverter", managed = true)
public class ProduitConverter implements Converter {

    @EJB
    private ProduitFacade produitFacade;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Produit) {
            Produit produit = (Produit) value;
            return String.valueOf(produit.getId());
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            try {
                Integer id = Integer.parseInt(value);
                return produitFacade.find(id);
            } catch (NumberFormatException e) {
                // Handle conversion exception
            }
        }
        return null;
    }
}
