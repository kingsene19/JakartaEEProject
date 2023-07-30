package com.example.bikeshopmobile.entites;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ArticleCommande implements Serializable {
    private ArticleCommandePK articleCommandePK;
    private int quantite;
    private BigDecimal prixDepart;
    private BigDecimal remise;
    private Produit produitId;
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
