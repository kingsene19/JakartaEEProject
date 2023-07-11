package sn.ept.ventesvelos.entites;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@XmlRootElement(name = "article")
@Entity
@Table(name = "article_commande")
@NamedQueries({
        @NamedQuery(name = "ArticleCommande.findAll", query = "SELECT a FROM ArticleCommande a"),
        @NamedQuery(name = "ArticleCommande.findByNumeroCommande", query = "SELECT a FROM ArticleCommande a WHERE a.articleCommandePK.numeroCommande = :numeroCommande"),
        @NamedQuery(name = "ArticleCommande.findByLigne", query = "SELECT a FROM ArticleCommande a WHERE a.articleCommandePK.ligne = :ligne"),
        @NamedQuery(name = "ArticleCommande.findByProduitId", query = "SELECT a FROM ArticleCommande a WHERE a.produitId = :produitId"),
        @NamedQuery(name = "ArticleCommande.findByQuantite", query = "SELECT a FROM ArticleCommande a WHERE a.quantite = :quantite"),
        @NamedQuery(name = "ArticleCommande.findByPrixDepart", query = "SELECT a FROM ArticleCommande a WHERE a.prixDepart = :prixDepart"),
        @NamedQuery(name = "ArticleCommande.findByRemise", query = "SELECT a FROM ArticleCommande a WHERE a.remise = :remise")})
public class ArticleCommande implements Serializable {
    @EmbeddedId
    private ArticleCommandePK articleCommandePK;
    @Basic(optional = false)
    @Column(name = "QUANTITE")
    private int quantite;
    @Basic(optional = false)
    @Column(name = "PRIX_DEPART")
    private BigDecimal prixDepart;
    @Basic(optional = false)
    @Column(name = "REMISE")
    private BigDecimal remise;

    @JoinColumn(name="PRODUIT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Produit produitId;

    @JoinColumn(name = "NUMERO_COMMANDE", referencedColumnName = "NUMERO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Commande commandeId;

    public ArticleCommande() {
    }

    public ArticleCommande(ArticleCommandePK articleCommandePK) {
        this.articleCommandePK = articleCommandePK;
    }

    public ArticleCommande(ArticleCommandePK articleCommandePK, int quantite, BigDecimal prixDepart, BigDecimal remise) {
        this.articleCommandePK = articleCommandePK;
        this.quantite = quantite;
        this.prixDepart = prixDepart;
        this.remise = remise;
    }

    public ArticleCommande(int commandeId, int ligne) {
        this.articleCommandePK = new ArticleCommandePK(commandeId, ligne);
    }

    public ArticleCommandePK getArticleCommandePK() {
        return articleCommandePK;
    }

    public void setArticleCommandePK(ArticleCommandePK articleCommandePK) {
        this.articleCommandePK = articleCommandePK;
    }


    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixDepart() {
        return prixDepart;
    }

    public void setPrixDepart(BigDecimal prixDepart) {
        this.prixDepart = prixDepart;
    }

    public BigDecimal getRemise() {
        return remise;
    }

    public void setRemise(BigDecimal remise) {
        this.remise = remise;
    }


    public Produit getProduitId() {
        return produitId;
    }

    public void setProduitId(Produit produitId) {
        this.produitId = produitId;
    }

    public Commande getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Commande commandeId) {
        this.commandeId = commandeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleCommande)) return false;
        ArticleCommande that = (ArticleCommande) o;
        return Objects.equals(getArticleCommandePK(), that.getArticleCommandePK());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticleCommandePK());
    }

    @Override
    public String toString() {
        return "ArticleCommande{" +
                "articleCommandePK=" + articleCommandePK +
                ", quantite=" + quantite +
                ", prixDepart=" + prixDepart +
                ", remise=" + remise +
                ", produitId=" + produitId +
                ", commandeId=" + commandeId +
                '}';
    }


}
