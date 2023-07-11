package sn.ept.ventesvelos.entites;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="personne")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "personne_seq", sequenceName = "personne_seq", allocationSize = 1, initialValue = 1458)
public class Personne implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personne_seq")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "PRENOM")
    private String prenom;
    @Basic(optional = false)
    @Column(name = "NOM")
    private String nom;
    @Column(name = "TELEPHONE")
    private String telephone;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;

    public Personne() {}

    public Personne(Integer id) {
        this.id = id;
    }

    public Personne(Integer id, String prenom, String nom, String email) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Personne)) return false;
        Personne personne = (Personne) o;
        return Objects.equals(getId(), personne.getId()) && Objects.equals(getPrenom(), personne.getPrenom()) && Objects.equals(getNom(), personne.getNom()) && Objects.equals(getTelephone(), personne.getTelephone()) && Objects.equals(getEmail(), personne.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrenom(), getNom(), getTelephone(), getEmail());
    }
}
