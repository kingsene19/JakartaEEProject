package sn.ept.ventesvelos.mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import sn.ept.ventesvelos.entites.Localisation;
import sn.ept.ventesvelos.facades.LocalisationFacade;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named("localisationMBean")
@ViewScoped
public class LocalisationMBean implements Serializable {

    @EJB
    private LocalisationFacade localisationFacade;

    private List<Localisation> localisations;
    private Localisation selectedLocalisation;
    private List<Localisation> selectedLocalisations;

    @PostConstruct
    public void init() {
        if (this.localisations == null) {
            this.localisations = localisationFacade.findAll();
        }
    }

    public List<Localisation> getLocalisations() {
        return localisations;
    }

    public void setLocalisations(List<Localisation> localisations) {
        this.localisations = localisations;
    }

    public Localisation getSelectedLocalisation() {
        return selectedLocalisation;
    }

    public void setSelectedLocalisation(Localisation selectedLocalisation) {
        this.selectedLocalisation = selectedLocalisation;
    }

    public List<Localisation> getSelectedLocalisations() {
        return selectedLocalisations;
    }

    public void setSelectedLocalisations(List<Localisation> selectedLocalisations) {
        this.selectedLocalisations = selectedLocalisations;
    }

    public void openNew() {
        this.selectedLocalisation = new Localisation();
    }

    public void saveLocalisation() throws IOException {
        if (this.selectedLocalisation.getId() == null ) {
            localisationFacade.create(this.selectedLocalisation);
        } else {
            localisationFacade.edit(this.selectedLocalisation);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Localisation Modifiée"));
        }
        this.selectedLocalisation = null;
        PrimeFaces.current().executeScript("PF('manageLocalisationDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-localisations");
    }

    public void deleteLocalisation() throws IOException {
        localisationFacade.remove(this.selectedLocalisation);
        if (hasSelectedLocalisations()) {
            this.selectedLocalisations.remove(this.selectedLocalisation);
        }
        this.selectedLocalisation = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Localisation Supprimée"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-localisations");
    }

    public boolean hasSelectedLocalisations() {
        return this.selectedLocalisations!=null && !this.selectedLocalisations.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedLocalisations()) {
            int size  = this.selectedLocalisations.size();
            return size > 1 ? size + " localisations selectionnées" : "1 localisation selectionnée";
        }
        return "Supprimer";
    }

    public void deleteSelectedLocalisations() throws IOException {
        for (int i=0; i<this.selectedLocalisations.size(); i++) {
            localisationFacade.remove(this.selectedLocalisations.get(i));
        }
        this.selectedLocalisations = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Localisations Supprimérs"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-localisations");
        PrimeFaces.current().executeScript("PF('dtLocalisations').clearFilters()");
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
