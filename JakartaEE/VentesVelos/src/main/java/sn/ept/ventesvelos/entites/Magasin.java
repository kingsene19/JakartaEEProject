package sn.ept.ventesvelos.entites;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.io.Serializable;
import java.util.Collection;

@XmlRootElement(name = "magasin")
@Entity
@Table(name = "magasin")
@NamedQueries({
        @NamedQuery(name = "Magasin.findAll", query = "SELECT m FROM Magasin m"),
        @NamedQuery(name = "Magasin.findById", query = "SELECT m FROM Magasin m WHERE m.id = :id"),
        @NamedQuery(name = "Magasin.findByNom", query = "SELECT m FROM Magasin m WHERE m.nom = :nom"),
        @NamedQuery(name = "Magasin.findByTelephone", query = "SELECT m FROM Magasin m WHERE m.telephone = :telephone"),
        @NamedQuery(name = "Magasin.findByEmail", query = "SELECT m FROM Magasin m WHERE m.email = :email"),
        @NamedQuery(name = "Magasin.findByAdresse", query = "SELECT m FROM Magasin m WHERE m.adresse = :adresse")})
@SequenceGenerator(name = "magasin_seq", sequenceName = "magasin_seq", allocationSize = 1, initialValue = 4)
public class Magasin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "magasin_seq")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOM")
    private String nom;
    @Column(name = "TELEPHONE")
    private String telephone;
    @Column(name = "EMAIL")
    private String email;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="adresse", column = @Column(name="ADRESSE")),
            @AttributeOverride(name="ville", column = @Column(name="VILLE")),
            @AttributeOverride(name="etat", column = @Column(name="ETAT")),
            @AttributeOverride(name="codeZip", column = @Column(name="CODE_ZIP"))
    })
    private Adresse adresse;

    @JsonbTransient
    @OneToMany(mappedBy = "magasinId", cascade = CascadeType.ALL, orphanRemoval = true)
    @CascadeOnDelete
    private Collection<Employe> employeCollection;

    @JsonbTransient
    @OneToMany(mappedBy = "magasinId", cascade = CascadeType.ALL, orphanRemoval = true)
    @CascadeOnDelete
    private Collection<Commande> commandeCollection;

    @JsonbTransient
    @OneToMany(mappedBy = "magasinId", cascade = CascadeType.ALL,orphanRemoval = true)
    @CascadeOnDelete
    private Collection<Stock> stockCollection;

    public Magasin() {
    }

    public Magasin(Integer id) {
        this.id = id;
    }

    public Magasin(Integer id, String nom) {
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Collection<Employe> getEmployeCollection() {
        return employeCollection;
    }

    public void setEmployeCollection(Collection<Employe> employeCollection) {
        this.employeCollection = employeCollection;
    }

    public Collection<Commande> getCommandeCollection() {
        return commandeCollection;
    }

    public void setCommandeCollection(Collection<Commande> commandeCollection) {
        this.commandeCollection = commandeCollection;
    }

    public Collection<Stock> getStockCollection() {
        return stockCollection;
    }

    public void setStockCollection(Collection<Stock> stockCollection) {
        this.stockCollection = stockCollection;
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
        if (!(object instanceof Magasin)) {
            return false;
        }
        Magasin other = (Magasin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Magasin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", adresse=" + adresse +
                '}';
    }
}
