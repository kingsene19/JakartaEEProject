package sn.ept.ventesvelos.entites;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.CascadeOnDelete;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@XmlRootElement(name = "client")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
        @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id"),
        @NamedQuery(name = "Client.findByPrenom", query = "SELECT c FROM Client c WHERE c.prenom = :prenom"),
        @NamedQuery(name = "Client.findByNom", query = "SELECT c FROM Client c WHERE c.nom = :nom"),
        @NamedQuery(name = "Client.findByTelephone", query = "SELECT c FROM Client c WHERE c.telephone = :telephone"),
        @NamedQuery(name = "Client.findByEmail", query = "SELECT c FROM Client c WHERE c.email = :email"),
        @NamedQuery(name = "Client.findByAdresse", query = "SELECT c FROM Client c WHERE c.adresse = :adresse")})
@Table(name = "client")
public class Client extends Personne {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="adresse", column = @Column(name="ADRESSE")),
            @AttributeOverride(name="ville", column = @Column(name="VILLE")),
            @AttributeOverride(name="etat", column = @Column(name="ETAT")),
            @AttributeOverride(name="codeZip", column = @Column(name="CODE_ZIP"))
    })
    private Adresse adresse;

    @JsonbTransient
    @OneToMany(mappedBy = "clientId", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @CascadeOnDelete
    private Collection<Commande> commandeCollection;


    public Client() {
    }

    public Client(Integer id) {super(id);
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
