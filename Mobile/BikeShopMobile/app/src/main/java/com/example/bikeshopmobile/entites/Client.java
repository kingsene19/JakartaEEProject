package com.example.bikeshopmobile.entites;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

public class Client extends Personne {

    private Adresse adresse;
    private Collection<Commande> commandeCollection;


    public Client() {
    }

    public Client(Integer id) {
        super(id);
    }

    public Client(Integer id, String prenom, String nom, String email) {
        super(id, prenom, nom, email);
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Collection<Commande> getCommandeCollection() {
        return commandeCollection;
    }

    public void setCommandeCollection(Collection<Commande> commandeCollection) {
        this.commandeCollection = commandeCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + getId() +
                ", adresse=" + adresse +
                ", prenom='" + getPrenom() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", telephone='" + getTelephone() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
