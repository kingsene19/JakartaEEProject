package sn.ept.ventesvelos.entites;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@XmlRootElement(name = "produit")
@Entity
@Table(name = "produit")
@NamedQueries({
        @NamedQuery(name = "Produit.findAll", query = "SELECT p FROM Produit p"),
        @NamedQuery(name = "Produit.findById", query = "SELECT p FROM Produit p WHERE p.id = :id"),
        @NamedQuery(name = "Produit.findByNom", query = "SELECT p FROM Produit p WHERE p.nom = :nom"),
        @NamedQuery(name = "Produit.findByAnneeModel", query = "SELECT p FROM Produit p WHERE p.anneeModel = :anneeModel"),
        @NamedQuery(name = "Produit.findByPrixDepart", query = "SELECT p FROM Produit p WHERE p.prixDepart = :prixDepart")})
@SequenceGenerator(name = "produit_seq", sequenceName = "produit_seq", allocationSize = 1, initialValue = 322)
public class Produit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produit_seq")
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOM")
    private String nom;
    @Basic(optional = false)
    @Column(name = "ANNEE_MODEL")
    private short anneeModel;
    @Basic(optional = false)
    @Column(name = "PRIX_DEPART")
    private BigDecimal prixDepart;

    @JoinColumn(name = "CATEGORIE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Categorie categorieId;

    @JoinColumn(name = "MARQUE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Marque marqueId;

    @JsonbTransient
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "produitId", orphanRemoval = true)
    private Collection<Stock> stockCollection;

    @JsonbTransient
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "produitId", orphanRemoval = true)
    private Collection<ArticleCommande> articleCommandeCollection;

    public Produit() {
    }

    public Produit(Integer id) {
        this.id = id;
    }

    public Produit(Integer id, String nom, short anneeModel, BigDecimal prixDepart) {
        this.id = id;
        this.nom = nom;
        this.anneeModel = anneeModel;
        this.prixDepart = prixDepart;
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

    public short getAnneeModel() {
        return anneeModel;
    }

    public void setAnneeModel(short anneeModel) {
        this.anneeModel = anneeModel;
    }

    public BigDecimal getPrixDepart() {
        return prixDepart;
    }

    public void setPrixDepart(BigDecimal prixDepart) {
        this.prixDepart = prixDepart;
    }

    public Categorie getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Categorie categorieId) {
        this.categorieId = categorieId;
    }

    public Marque getMarqueId() {
        return marqueId;
    }

    public void setMarqueId(Marque marqueId) {
        this.marqueId = marqueId;
    }

    public Collection<Stock> getStockCollection() {
        return stockCollection;
    }

    public void setStockCollection(Collection<Stock> stockCollection) {
        this.stockCollection = stockCollection;
    }

    public Collection<ArticleCommande> getArticleCommandeCollection() {
        return articleCommandeCollection;
    }

    public void setArticleCommandeCollection(Collection<ArticleCommande> articleCommandeCollection) {
        this.articleCommandeCollection = articleCommandeCollection;
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
        if (!(object instanceof Produit)) {
            return false;
        }
        Produit other = (Produit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", anneeModel=" + anneeModel +
                ", prixDepart=" + prixDepart +
                ", categorieId=" + categorieId +
                ", marqueId=" + marqueId +
                '}';
    }
}
