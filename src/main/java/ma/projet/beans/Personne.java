package ma.projet.beans;
import jakarta.persistence.*;
import ma.projet.classes.Entite;
import java.time.LocalDate;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personne extends Entite {
    @Column(nullable = false) private String nom;
    @Column(nullable = false) private String prenom;
    private String telephone;
    private String adresse;
    @Column(nullable = false) private LocalDate dateNaissance;
    @Column(nullable = false, unique = true) private String cin;
    public String getNom() { return nom; }
    public void setNom(String value) { nom = value; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String value) { prenom = value; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String value) { telephone = value; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String value) { adresse = value; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate value) { dateNaissance = value; }
    public String getCin() { return cin; }
    public void setCin(String value) { cin = value; }
    @Override public String toString() { return getId() + " - " + nom + " " + prenom + " | " + dateNaissance + " | CIN : " + cin; }
}
