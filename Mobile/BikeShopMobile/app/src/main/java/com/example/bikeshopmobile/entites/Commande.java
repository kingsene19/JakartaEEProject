package com.example.bikeshopmobile.entites;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class Commande implements Serializable {

    private Integer numero;
    private short statut;
    private String dateCommande;
    private String dateLivraisonVoulue;
    private String dateLivraison;
    private Collection<ArticleCommande> articleCommandeCollection;
    private Client clientId;
    private Employe vendeurId;
    private Magasin magasinId;

    public Commande() {
    }

    public Commande(Integer numero) {
        this.numero = numero;
    }

    public Commande(Integer numero, short statut, String dateCommande, String dateLivraisonVoulue) {
        this.numero = numero;
        this.statut = statut;
        this.dateCommande = dateCommande;
        this.dateLivraisonVoulue = dateLivraisonVoulue;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public short getStatut() {
        return statut;
    }

    public void setStatut(short statut) {
        this.statut = statut;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getDateLivraisonVoulue() {
        return dateLivraisonVoulue;
    }

    public void setDateLivraisonVoulue(String dateLivraisonVoulue) {
        this.dateLivraisonVoulue = dateLivraisonVoulue;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Collection<ArticleCommande> getArticleCommandeCollection() {

        return articleCommandeCollection;
    }

    public void setArticleCommandeCollection(Collection<ArticleCommande> articleCommandeCollection) {
        this.articleCommandeCollection = articleCommandeCollection;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Employe getVendeurId() {
        return vendeurId;
    }

    public void setVendeurId(Employe vendeurId) {
        this.vendeurId = vendeurId;
    }

    public Magasin getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Magasin magasinId) {
        this.magasinId = magasinId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commande)) {
            return false;
        }
        Commande other = (Commande) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "Commande{" +
                "numero=" + numero +
                ", statut=" + statut +
                ", dateCommande=" + dateCommande +
                ", dateLivraisonVoulue=" + dateLivraisonVoulue +
                ", dateLivraison=" + dateLivraison +
                ", clientId=" + clientId +
                ", vendeurId=" + vendeurId +
                ", magasinId=" + magasinId +
                '}';
    }

}
