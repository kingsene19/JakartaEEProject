package com.example.bikeshopmobile.entites;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

public class Employe extends Client {

    private short actif;
    private Employe managerId;
    private Magasin magasinId;
    private Collection<Employe> employeCollection;
    private Collection<Commande> venteCollection;

    public Employe() {
    }

    public Employe(Integer id) {
        super(id);
    }

    public Employe(Integer id, String prenom, String nom, String email, short actif) {
        super(id, prenom, nom, email);
        this.actif = actif;
    }

    public short getActif() {
        return actif;
    }

    public void setActif(short actif) {
        this.actif = actif;
    }

    public Collection<Employe> getEmployeCollection() {
        return employeCollection;
    }

    public void setEmployeCollection(Collection<Employe> employeCollection) {
        this.employeCollection = employeCollection;
    }

    public Employe getManagerId() {
        return managerId;
    }

    public void setManagerId(Employe managerId) {
        this.managerId = managerId;
    }

    public Magasin getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Magasin magasinId) {
        this.magasinId = magasinId;
    }

    public Collection<Commande> getVenteCollection() {
        return venteCollection;
    }

    public void setVenteCollection(Collection<Commande> venteCollection) {
        this.venteCollection = venteCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employe)) return false;
        if (!super.equals(o)) return false;
        Employe employe = (Employe) o;
        return Objects.equals(getId(), employe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + getId() +
                ", actif=" + actif +
                ", managerId=" + managerId +
                ", magasinId=" + magasinId +
                ", prenom='" + getPrenom() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", telephone='" + getTelephone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", adresse" + getAdresse() + '\'' +
                '}';
    }
}
