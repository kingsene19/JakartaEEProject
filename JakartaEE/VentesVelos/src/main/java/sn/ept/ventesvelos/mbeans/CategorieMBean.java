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
import sn.ept.ventesvelos.entites.Categorie;
import sn.ept.ventesvelos.entites.Produit;
import sn.ept.ventesvelos.entites.Stock;
import sn.ept.ventesvelos.facades.ArticleCommandeFacade;
import sn.ept.ventesvelos.facades.CategorieFacade;
import sn.ept.ventesvelos.facades.ProduitFacade;
import sn.ept.ventesvelos.facades.StockFacade;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Named("categorieMBean")
@ViewScoped
public class CategorieMBean implements Serializable {

    @EJB
    private CategorieFacade categorieFacade;

    private List<Categorie> categories;
    private Categorie selectedCategorie;
    private List<Categorie> selectedCategories;

    @PostConstruct
    public void init() {
        if (this.categories == null) {
            this.categories = categorieFacade.findAll();
        }
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public Categorie getSelectedCategorie() {
        return selectedCategorie;
    }

    public void setSelectedCategorie(Categorie selectedCategorie) {
        this.selectedCategorie = selectedCategorie;
    }

    public List<Categorie> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<Categorie> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public void openNew() {
        this.selectedCategorie = new Categorie();
    }

    public void saveCategorie() throws IOException {
        if (this.selectedCategorie.getId() == null ) {
            categorieFacade.create(this.selectedCategorie);
        } else {
            categorieFacade.edit(this.selectedCategorie);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Catégorie Modifié"));
        }
        this.selectedCategorie = null;
        PrimeFaces.current().executeScript("PF('manageCategorieDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }

    public void deleteCategorie() throws IOException {
        categorieFacade.remove(this.selectedCategorie);
        if (hasSelectedCategories()) {
            this.selectedCategories.remove(this.selectedCategorie);
        }
        this.selectedCategorie = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Catégorie Supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }

    public boolean hasSelectedCategories() {
        return this.selectedCategories!=null && !this.selectedCategories.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCategories()) {
            int size  = this.selectedCategories.size();
            return size > 1 ? size + " catégories selectionnées" : "1 catégorie selectionnée";
        }
        return "Supprimer";
    }

    public void deleteSelectedCategories() throws IOException {
        for (int i=0; i<this.selectedCategories.size(); i++) {
            categorieFacade.remove(this.selectedCategories.get(i));
        }
        this.selectedCategories = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Catégories Supprimés"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
        PrimeFaces.current().executeScript("PF('dtCategories').clearFilters()");
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
