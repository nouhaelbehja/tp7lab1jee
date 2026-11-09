package ma.projet.classes;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
@NamedQuery(name = "Tache.prixSuperieur1000", query = "from Tache t where t.prix > 1000 order by t.id")
@Entity
public class Tache extends Entite {
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private LocalDate dateDebut;
    @Column(nullable = false)
    private LocalDate dateFin;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal prix;
    @ManyToOne(optional = false) @JoinColumn(nullable = false)
    private Projet projet;

    private String priorite;
    public Tache() {}
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }
    public String getPriorite() { return priorite; }
    public void setPriorite(String priorite) { this.priorite = priorite; }

}
