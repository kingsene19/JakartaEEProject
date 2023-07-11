package sn.ept.ventesvelos.entites;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.time.LocalDate;

@XmlRootElement(name = "commande")
@Entity
@Table(name = "commande")
@NamedQueries({
        @NamedQuery(name = "Commande.findAll", query = "SELECT c FROM Commande c"),
        @NamedQuery(name = "Commande.findByNumero", query = "SELECT c FROM Commande c WHERE c.numero = :numero"),
        @NamedQuery(name = "Commande.findByStatut", query = "SELECT c FROM Commande c WHERE c.statut = :statut"),
        @NamedQuery(name = "Commande.findByDateCommande", query = "SELECT c FROM Commande c WHERE c.dateCommande = :dateCommande"),
        @NamedQuery(name = "Commande.findByDateLivraisonVoulue", query = "SELECT c FROM Commande c WHERE c.dateLivraisonVoulue = :dateLivraisonVoulue"),
        @NamedQuery(name = "Commande.findByDateLivraison", query = "SELECT c FROM Commande c WHERE c.dateLivraison = :dateLivraison")})
@SequenceGenerator(name = "commande_seq", sequenceName = "commande_seq", allocationSize = 1, initialValue = 1616)
public class Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commande_seq")
    @Basic(optional = false)
    @Column(name = "NUMERO")
    private Integer numero;
    @Basic(optional = false)
    @Column(name = "STATUT")
    private short statut;

    @JsonbDateFormat("yyyy-MM-dd")
    @Basic(optional = false)
    @Column(name = "DATE_COMMANDE")
    private LocalDate dateCommande;

    @JsonbDateFormat("yyyy-MM-dd")
    @Basic(optional = false)
    @Column(name = "DATE_LIVRAISON_VOULUE")
    private LocalDate dateLivraisonVoulue;

    @JsonbDateFormat("yyyy-MM-dd")
    @Column(name = "DATE_LIVRAISON")
    private LocalDate dateLivraison;

    @JsonbTransient
    @OneToMany(mappedBy = "commandeId", cascade = CascadeType.ALL, orphanRemoval = true)
    @CascadeOnDelete
    private Collection<ArticleCommande> articleCommandeCollection;

    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Client clientId;

    @JoinColumn(name = "VENDEUR_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Employe vendeurId;

    @JoinColumn(name = "MAGASIN_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Magasin magasinId;

    public Commande() {
    }

    public Commande(Integer numero) {
        this.numero = numero;
    }

    public Commande(Integer numero, short statut, LocalDate dateCommande, LocalDate dateLivraisonVoulue) {
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

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public LocalDate getDateLivraisonVoulue() {
        return dateLivraisonVoulue;
    }

    public void setDateLivraisonVoulue(LocalDate dateLivraisonVoulue) {
        this.dateLivraisonVoulue = dateLivraisonVoulue;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateCommande = dateCommande.format(dateFormatter);
        String formattedDateLivraisonVoulue = dateLivraisonVoulue.format(dateFormatter);
        String formattedDateLivraison = dateLivraison != null ? dateLivraison.format(dateFormatter) : "null";

        return "Commande{" +
                "numero=" + numero +
                ", statut=" + statut +
                ", dateCommande=" + formattedDateCommande +
                ", dateLivraisonVoulue=" + formattedDateLivraisonVoulue +
                ", dateLivraison=" + formattedDateLivraison +
                ", clientId=" + clientId +
                ", vendeurId=" + vendeurId +
                ", magasinId=" + magasinId +
                '}';
    }

}
