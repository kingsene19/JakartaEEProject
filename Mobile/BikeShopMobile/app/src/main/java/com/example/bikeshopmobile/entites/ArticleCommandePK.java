package com.example.bikeshopmobile.entites;

import java.io.Serializable;
import java.util.Objects;

public class ArticleCommandePK implements Serializable {


    private int numeroCommande;

    private int ligne;

    public ArticleCommandePK() {
    }

    public ArticleCommandePK(int numeroCommande, int ligne) {
        this.numeroCommande = numeroCommande;
        this.ligne = ligne;
    }

    public int getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(int numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleCommandePK)) return false;
        ArticleCommandePK that = (ArticleCommandePK) o;
        return getNumeroCommande() == that.getNumeroCommande() && getLigne() == that.getLigne();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroCommande(), getLigne());
    }

    @Override
    public String toString() {
        return "ArticleCommandePK{" +
                "commandeId=" + numeroCommande +
                ", ligne=" + ligne +
                '}';
    }
}
