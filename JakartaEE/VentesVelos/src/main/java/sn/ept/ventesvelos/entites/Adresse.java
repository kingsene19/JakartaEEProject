package sn.ept.ventesvelos.entites;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Adresse {
    private String adresse;
    private String ville;
    private String etat;
    private String codeZip;

    public Adresse() {}

    public Adresse(String adresse, String ville, String etat, String codeZip) {
        this.adresse = adresse;
        this.ville = ville;
        this.etat = etat;
        this.codeZip = codeZip;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getVille() {
        return ville;
    }

    public String getEtat() {
        return etat;
    }

    public String getCodeZip() {
        return codeZip;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setCodeZip(String codeZip) {
        this.codeZip = codeZip;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "adresse='" + adresse + '\'' +
                ", ville='" + ville + '\'' +
                ", etat='" + etat + '\'' +
                ", codeZip='" + codeZip + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adresse)) return false;
        Adresse adresse1 = (Adresse) o;
        return Objects.equals(getAdresse(), adresse1.getAdresse()) && Objects.equals(getVille(), adresse1.getVille()) && Objects.equals(getEtat(), adresse1.getEtat()) && Objects.equals(getCodeZip(), adresse1.getCodeZip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdresse(), getVille(), getEtat(), getCodeZip());
    }
}
