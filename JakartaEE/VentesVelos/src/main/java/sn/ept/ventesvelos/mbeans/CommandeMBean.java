package sn.ept.ventesvelos.mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.ventesvelos.entites.ArticleCommande;
import sn.ept.ventesvelos.entites.Client;
import sn.ept.ventesvelos.entites.Commande;
import sn.ept.ventesvelos.facades.ArticleCommandeFacade;
import sn.ept.ventesvelos.facades.ClientFacade;
import sn.ept.ventesvelos.facades.CommandeFacade;

import java.io.Serializable;
import java.util.List;

@Named("commandeMBean")
@ViewScoped
public class CommandeMBean implements Serializable {

    @EJB
    private CommandeFacade commandeFacade;

    private List<Commande> commandes;
    private Commande selectedCommande;
    private List<Commande> selectedCommandes;

    @PostConstruct
    public void init() {
        if (this.commandes == null) {
            this.commandes = commandeFacade.findAll();
        }
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public Commande getSelectedCommande() {
        return selectedCommande;
    }

    public void setSelectedCommande(Commande selectedCommande) {
        this.selectedCommande = selectedCommande;
    }

    public List<Commande> getSelectedCommandes() {
        return selectedCommandes;
    }

    public void setSelectedCommandes(List<Commande> selectedCommandes) {
        this.selectedCommandes = selectedCommandes;
    }

    public void openNew() {
        this.selectedCommande = new Commande();
    }

    public void saveCommande() {
        if (this.selectedCommande.getNumero() == null ) {
            commandeFacade.create(this.selectedCommande);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Commande Créée"));
        } else {
            commandeFacade.edit(this.selectedCommande);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Commande Modifié"));
        }
        this.selectedCommande = null;
        PrimeFaces.current().executeScript("PF('manageCommandeDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-commandes");
    }

    public void deleteCommande() {
        commandeFacade.remove(this.selectedCommande);
        if (hasSelectedCommandes()) {
            this.selectedCommandes.remove(this.selectedCommande);
        }
        this.selectedCommande = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Commande Supprimée"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-commandes");
    }

    public boolean hasSelectedCommandes() {
        return this.selectedCommandes!=null && !this.selectedCommandes.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCommandes()) {
            int size  = this.selectedCommandes.size();
            return size > 1 ? size + " commandes selectionnées" : "1 commande selectionnée";
        }
        return "Supprimer";
    }

    public void deleteSelectedCommandes() {
        for (int i=0; i<this.selectedCommandes.size(); i++) {
            commandeFacade.remove(this.selectedCommandes.get(i));
        }
        this.selectedCommandes = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Commandes Supprimées"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-commandes");
        PrimeFaces.current().executeScript("PF('dtCommandes').clearFilters()");
    }
}
