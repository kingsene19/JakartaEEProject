package sn.ept.ventesvelos.entites;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@XmlRootElement(name = "employe")
@Entity
@Table(name = "employe")
@NamedQueries({
        @NamedQuery(name = "Employe.findAll", query = "SELECT e FROM Employe e"),
        @NamedQuery(name = "Employe.findById", query = "SELECT e FROM Employe e WHERE e.id = :id"),
        @NamedQuery(name = "Employe.findByPrenom", query = "SELECT e FROM Employe e WHERE e.prenom = :prenom"),
        @NamedQuery(name = "Employe.findByNom", query = "SELECT e FROM Employe e WHERE e.nom = :nom"),
        @NamedQuery(name = "Employe.findByEmail", query = "SELECT e FROM Employe e WHERE e.email = :email"),
        @NamedQuery(name = "Employe.findByTelephone", query = "SELECT e FROM Employe e WHERE e.telephone = :telephone"),
        @NamedQuery(name = "Employe.findByActif", query = "SELECT e FROM Employe e WHERE e.actif = :actif")})
public class Employe extends Client {

    @Basic(optional = false)
    @Column(name = "ACTIF")
    private short actif;

    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "ID")
    @ManyToOne
    private Employe managerId;

    @JoinColumn(name = "MAGASIN_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Magasin magasinId;

    @JsonbTransient
    @OneToMany(mappedBy = "managerId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Employe> employeCollection;

    @JsonbTransient
    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "vendeurId", orphanRemoval = true)
    private Collection<Commande> commandeCollection;

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

    public Collection<Commande> getCommandeCollection() {
        return commandeCollection;
    }

    public void setCommandeCollection(Collection<Commande> commandeCollection) {
        this.commandeCollection = commandeCollection;
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
