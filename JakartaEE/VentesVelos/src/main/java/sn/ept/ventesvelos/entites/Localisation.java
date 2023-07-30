package sn.ept.ventesvelos.entites;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@XmlRootElement(name="localisation")
@Table(name="localisation")
@Entity
@NamedQueries({
        @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Localisation l"),
        @NamedQuery(name = "Commande.findByTimestamp", query = "SELECT l FROM Localisation l WHERE l.timestamp = :timestamp"),
        @NamedQuery(name = "Commande.findByLatitude", query = "SELECT l FROM Localisation l WHERE l.latitude = :latitude"),
        @NamedQuery(name = "Commande.findByLongitude", query = "SELECT l FROM Localisation l WHERE l.longitude = :longitude"),
})
public class Localisation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name="ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name="TIMESTAMP")
    private Timestamp timestamp; // Using Timestamp as ID

    @Basic(optional = false)
    @Column(name="LATITUDE")
    private double latitude;

    @Basic(optional = false)
    @Column(name="LONGITUDE")
    private double longitude;


    public Localisation() {
    }

    public Localisation(Timestamp timestamp, double latitude, double longitude) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Localisation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @PrePersist
    public void onPrePersist() {
        if (timestamp == null) {
            timestamp = new Timestamp(System.currentTimeMillis());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Localisation)) return false;
        Localisation that = (Localisation) o;
        return Double.compare(that.getLatitude(), getLatitude()) == 0 && Double.compare(that.getLongitude(), getLongitude()) == 0 && getId().equals(that.getId()) && Objects.equals(getTimestamp(), that.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTimestamp(), getLatitude(), getLongitude());
    }

    @Override
    public String toString() {
        return "Localisation{" +
                "id="+ id +
                "timestamp=" + timestamp +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
