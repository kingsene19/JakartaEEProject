package sn.ept.ventesvelos.mbeans;


import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named("redirectionBean")
@RequestScoped
public class RedirectionBean implements Serializable {

    public RedirectionBean() {}

    public void redirectToPage(String url) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/" + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}