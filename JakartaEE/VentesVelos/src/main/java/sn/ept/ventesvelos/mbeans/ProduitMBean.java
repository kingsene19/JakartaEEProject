package sn.ept.ventesvelos.mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.ventesvelos.entites.ArticleCommande;
import sn.ept.ventesvelos.entites.Produit;
import sn.ept.ventesvelos.entites.Stock;
import sn.ept.ventesvelos.facades.ArticleCommandeFacade;
import sn.ept.ventesvelos.facades.ProduitFacade;
import sn.ept.ventesvelos.facades.StockFacade;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Named("produitMBean")
@ViewScoped
public class ProduitMBean implements Serializable {

    @EJB
    private ProduitFacade produitFacade;

    private List<Produit> produits;
    private Produit selectedProduit;
    private List<Produit> selectedProduits;

    @PostConstruct
    public void init() {
        if (this.produits == null) {
            this.produits = produitFacade.findAll();
        }
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public Produit getSelectedProduit() {
        return selectedProduit;
    }

    public void setSelectedProduit(Produit selectedProduit) {
        this.selectedProduit = selectedProduit;
    }

    public List<Produit> getSelectedProduits() {
        return selectedProduits;
    }

    public void setSelectedProduits(List<Produit> selectedProduits) {
        this.selectedProduits = selectedProduits;
    }

    public void openNew() {
        this.selectedProduit = new Produit();
    }

    public void saveProduit() {
        if (this.selectedProduit.getId() == null ) {
            produitFacade.create(this.selectedProduit);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit Créé"));
        } else {
            produitFacade.edit(this.selectedProduit);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit Modifié"));
        }
        this.selectedProduit = null;
        PrimeFaces.current().executeScript("PF('manageProduitDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-produits");
    }

    public void deleteProduit() {
        produitFacade.remove(this.selectedProduit);
        if (hasSelectedProduits()) {
            this.selectedProduits.remove(this.selectedProduit);
        }
        this.selectedProduit = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit Supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-produits");
    }

    public boolean hasSelectedProduits() {
        return this.selectedProduits!=null && !this.selectedProduits.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedProduits()) {
            int size  = this.selectedProduits.size();
            return size > 1 ? size + " produits selectionnés" : "1 produit selectionné";
        }
        return "Supprimer";
    }

    public void deleteSelectedProduits() {
        for (int i=0; i<this.selectedProduits.size(); i++) {
            produitFacade.remove(this.selectedProduits.get(i));
        }
        this.selectedProduits = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produits Supprimés"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-produits");
        PrimeFaces.current().executeScript("PF('dtProduits').clearFilters()");
    }
}
