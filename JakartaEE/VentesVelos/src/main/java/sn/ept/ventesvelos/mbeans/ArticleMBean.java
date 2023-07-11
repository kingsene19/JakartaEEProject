package sn.ept.ventesvelos.mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import sn.ept.ventesvelos.entites.ArticleCommande;
import sn.ept.ventesvelos.entites.ArticleCommandePK;
import sn.ept.ventesvelos.facades.ArticleCommandeFacade;
import java.io.Serializable;
import java.util.List;

@Named("articleMBean")
@ViewScoped
public class ArticleMBean implements Serializable {

    @EJB
    private ArticleCommandeFacade articleCommandeFacade;

    private List<ArticleCommande> articles;
    private ArticleCommande selectedArticle;
    private List<ArticleCommande> selectedArticles;

    @PostConstruct
    public void init() {
        if (this.articles == null) {
            this.articles = articleCommandeFacade.findAll();
        }
    }

    public List<ArticleCommande> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleCommande> articles) {
        this.articles = articles;
    }

    public ArticleCommande getSelectedArticle() {
        return selectedArticle;
    }

    public void setSelectedArticle(ArticleCommande selectedArticle) {
        this.selectedArticle = selectedArticle;
    }

    public List<ArticleCommande> getSelectedArticles() {
        return selectedArticles;
    }

    public void setSelectedArticles(List<ArticleCommande> selectedArticles) {
        this.selectedArticles = selectedArticles;
    }

    public void openNew() {
        this.selectedArticle = new ArticleCommande();
        this.selectedArticle.setArticleCommandePK(new ArticleCommandePK());
    }

    public void saveArticle() {
        ArticleCommandePK articlePK = new ArticleCommandePK(this.selectedArticle.getCommandeId().getNumero(), this.selectedArticle.getArticleCommandePK().getLigne());
        this.selectedArticle.setArticleCommandePK(articlePK);
        this.selectedArticle.setPrixDepart(this.selectedArticle.getProduitId().getPrixDepart());
        if (articleCommandeFacade.find(this.selectedArticle.getArticleCommandePK()) == null ) {
            articleCommandeFacade.create(this.selectedArticle);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Article Commande Créé"));
        } else {
            articleCommandeFacade.edit(this.selectedArticle);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Article Commande Modifié"));
        }
        this.selectedArticle = null;
        PrimeFaces.current().executeScript("PF('manageArticleDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-articles");
    }

    public void deleteArticle() {
        articleCommandeFacade.remove(this.selectedArticle);
        if (hasSelectedArticles()) {
            this.selectedArticles.remove(this.selectedArticle);
        }
        this.selectedArticle = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Article Commande Supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-articles");
    }

    public boolean hasSelectedArticles() {
        return this.selectedArticles!=null && !this.selectedArticles.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedArticles()) {
            int size  = this.selectedArticles.size();
            return size > 1 ? size + " articles selectionnés" : "1 article selectionné";
        }
        return "Supprimer";
    }

    public void deleteSelectedArticles() {
        for (int i=0; i<this.selectedArticles.size(); i++) {
            articleCommandeFacade.remove(this.selectedArticles.get(i));
        }
        this.selectedArticles = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Articles Supprimés"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-articles");
        PrimeFaces.current().executeScript("PF('dtArticles').clearFilters()");
    }
}
