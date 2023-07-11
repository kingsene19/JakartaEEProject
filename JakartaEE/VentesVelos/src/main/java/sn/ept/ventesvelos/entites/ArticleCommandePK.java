package sn.ept.ventesvelos.entites;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name = "article-pk")
@Embeddable
public class ArticleCommandePK implements Serializable {

    @Basic(optional = false)
    @Column(name="NUMERO_COMMANDE")
    private int numeroCommande;
    @Basic(optional = false)
    @Column(name = "LIGNE")
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
