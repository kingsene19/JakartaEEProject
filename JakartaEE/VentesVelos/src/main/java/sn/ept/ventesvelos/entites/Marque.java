package sn.ept.ventesvelos.entites;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.io.Serializable;
import java.util.Collection;

@XmlRootElement(name="marque")
@Entity
@Table(name = "marque")
@NamedQueries({
        @NamedQuery(name = "Marque.findAll", query = "SELECT m FROM Marque m"),
        @NamedQuery(name = "Marque.findById", query = "SELECT m FROM Marque m WHERE m.id = :id"),
        @NamedQuery(name = "Marque.findByNom", query = "SELECT m FROM Marque m WHERE m.nom = :nom")})
@SequenceGenerator(name = "marque_seq", sequenceName = "marque_seq", allocationSize = 1, initialValue = 10)
public class Marque implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marque_seq")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOM")
    private String nom;

    @JsonbTransient
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "marqueId", orphanRemoval = true)
    @CascadeOnDelete
    private Collection<Produit> produitCollection;

    public Marque() {
    }

    public Marque(Integer id) {
        this.id = id;
    }

    public Marque(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Collection<Produit> getProduitCollection() {
        return produitCollection;
    }

    public void setProduitCollection(Collection<Produit> produitCollection) {
        this.produitCollection = produitCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Marque)) {
            return false;
        }
        Marque other = (Marque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Marque{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
