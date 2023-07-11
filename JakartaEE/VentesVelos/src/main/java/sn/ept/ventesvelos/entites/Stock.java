package sn.ept.ventesvelos.entites;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.io.Serializable;

@XmlRootElement(name = "stock")
@Entity
@Table(name = "stock")
@NamedQueries({
        @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s"),
        @NamedQuery(name = "Stock.findByMagasinId", query = "SELECT s FROM Stock s WHERE s.stockPK.magasinId = :magasinId"),
        @NamedQuery(name = "Stock.findByProduitId", query = "SELECT s FROM Stock s WHERE s.stockPK.produitId = :produitId"),
        @NamedQuery(name = "Stock.findByQuantite", query = "SELECT s FROM Stock s WHERE s.quantite = :quantite")})
public class Stock implements Serializable {
    @EmbeddedId
    protected StockPK stockPK;
    @Basic(optional = false)
    @Column(name = "QUANTITE")
    private int quantite;

    @JoinColumn(name = "PRODUIT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Produit produitId;

    @JoinColumn(name = "MAGASIN_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Magasin magasinId;

    public Stock() {
    }

    public Stock(StockPK stockPK) {
        this.stockPK = stockPK;
    }

    public Stock(StockPK stockPK, int quantite) {
        this.stockPK = stockPK;
        this.quantite = quantite;
    }

    public Stock(int magasinId, int produitId) {
        this.stockPK = new StockPK(magasinId, produitId);
    }

    public StockPK getStockPK() {
        return stockPK;
    }

    public void setStockPK(StockPK stockPK) {
        this.stockPK = stockPK;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Produit getProduitId() {
        return produitId;
    }

    public void setProduitId(Produit produit) {
        this.produitId = produit;
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
        hash += (stockPK != null ? stockPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.stockPK == null && other.stockPK != null) || (this.stockPK != null && !this.stockPK.equals(other.stockPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockPK=" + stockPK +
                ", quantite=" + quantite +
                ", produit=" + produitId +
                ", magasin=" + magasinId +
                '}';
    }
}
