package sn.ept.ventesvelos.mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.ventesvelos.entites.*;
import sn.ept.ventesvelos.facades.*;

import java.io.Serializable;
import java.util.List;

@Named("magasinMBean")
@ViewScoped
public class MagasinMBean implements Serializable {

    @EJB
    private MagasinFacade magasinFacade;

    private List<Magasin> magasins;
    private Magasin selectedMagasin;
    private List<Magasin> selectedMagasins;

    @PostConstruct
    public void init() {
        if (this.magasins == null) {
            this.magasins = magasinFacade.findAll();
        }
    }

    public List<Magasin> getMagasins() {
        return magasins;
    }

    public void setMagasins(List<Magasin> magasins) {
        this.magasins = magasins;
    }

    public Magasin getSelectedMagasin() {
        return selectedMagasin;
    }

    public void setSelectedMagasin(Magasin selectedMagasin) {
        this.selectedMagasin = selectedMagasin;
    }

    public List<Magasin> getSelectedMagasins() {
        return selectedMagasins;
    }

    public void setSelectedMagasins(List<Magasin> selectedMagasins) {
        this.selectedMagasins = selectedMagasins;
    }

    public void openNew() {
        this.selectedMagasin = new Magasin();
        this.selectedMagasin.setAdresse(new Adresse());
    }

    public void saveMagasin() {
        if (this.selectedMagasin.getId() == null ) {
            magasinFacade.create(this.selectedMagasin);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Magasin Créé"));
        } else {
            magasinFacade.edit(this.selectedMagasin);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Magasin Modifié"));
        }
        this.selectedMagasin = null;
        PrimeFaces.current().executeScript("PF('manageMagasinDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-magasins");
    }

    public void deleteMagasin() {
        magasinFacade.remove(this.selectedMagasin);
        if (hasSelectedMagasins()) {
            this.selectedMagasins.remove(this.selectedMagasin);
        }
        this.selectedMagasin = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Magasin Supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-magasins");
    }

    public boolean hasSelectedMagasins() {
        return this.selectedMagasins!=null && !this.selectedMagasins.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedMagasins()) {
            int size  = this.selectedMagasins.size();
            return size > 1 ? size + " magasins selectionnés" : "1 magasin selectionné";
        }
        return "Supprimer";
    }

    public void deleteSelectedClients() {
        for (int i=0; i<this.selectedMagasins.size(); i++) {
            magasinFacade.remove(this.selectedMagasins.get(i));
        }
        this.selectedMagasins = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Magasins Supprimés"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-magasins");
        PrimeFaces.current().executeScript("PF('dtMagasins').clearFilters()");
    }
}
