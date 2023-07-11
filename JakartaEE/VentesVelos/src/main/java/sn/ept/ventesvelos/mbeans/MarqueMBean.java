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
import sn.ept.ventesvelos.entites.ArticleCommande;
import sn.ept.ventesvelos.entites.Marque;
import sn.ept.ventesvelos.entites.Produit;
import sn.ept.ventesvelos.entites.Stock;
import sn.ept.ventesvelos.facades.ArticleCommandeFacade;
import sn.ept.ventesvelos.facades.MarqueFacade;
import sn.ept.ventesvelos.facades.ProduitFacade;
import sn.ept.ventesvelos.facades.StockFacade;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named("marqueMBean")
@ViewScoped
public class MarqueMBean implements Serializable {

    @EJB
    private MarqueFacade marqueFacade;

    private List<Marque> marques;
    private Marque selectedMarque;
    private List<Marque> selectedMarques;

    @PostConstruct
    public void init() {
        if (this.marques == null) {
            this.marques = marqueFacade.findAll();
        }
    }

    public List<Marque> getMarques() {
        return marques;
    }

    public void setMarques(List<Marque> marques) {
        this.marques = marques;
    }

    public Marque getSelectedMarque() {
        return selectedMarque;
    }

    public void setSelectedMarque(Marque selectedMarque) {
        this.selectedMarque = selectedMarque;
    }

    public List<Marque> getSelectedMarques() {
        return selectedMarques;
    }

    public void setSelectedMarques(List<Marque> selectedMarques) {
        this.selectedMarques = selectedMarques;
    }

    public void openNew() {
        this.selectedMarque = new Marque();
    }

    public void saveMarque() throws IOException {
        if (this.selectedMarque.getId() == null ) {
            marqueFacade.create(this.selectedMarque);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Crée"));
        } else {
            marqueFacade.edit(this.selectedMarque);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Modifié"));
        }
        this.selectedMarque = null;
        PrimeFaces.current().executeScript("PF('manageMarqueDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-marques");
    }

    public void deleteMarque() throws IOException {
        marqueFacade.remove(this.selectedMarque);
        if (hasSelectedMarques()) {
            this.selectedMarques.remove(this.selectedMarque);
        }
        this.selectedMarque = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marque Supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-marques");
    }

    public boolean hasSelectedMarques() {
        return this.selectedMarques!=null && !this.selectedMarques.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedMarques()) {
            int size  = this.selectedMarques.size();
            return size > 1 ? size + " marques selectionnées" : "1 marque selectionnée";
        }
        return "Supprimer";
    }

    public void deleteSelectedMarques() throws IOException {
        for (int i=0; i<this.selectedMarques.size(); i++) {
            marqueFacade.remove(this.selectedMarques.get(i));
        }
        this.selectedMarques = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Marques Supprimées"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-marques");
        PrimeFaces.current().executeScript("PF('dtMarques').clearFilters()");
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
