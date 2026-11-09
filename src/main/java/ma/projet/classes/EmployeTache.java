package ma.projet.classes;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"employe_id", "tache_id"}))
@Entity
public class EmployeTache extends Entite {
    @ManyToOne(optional = false) @JoinColumn(nullable = false)
    private Employe employe;
    @ManyToOne(optional = false) @JoinColumn(nullable = false)
    private Tache tache;
    @Column(nullable = false)
    private LocalDate dateDebutReelle;

    private LocalDate dateFinReelle;
    @Column(nullable = false)
    private int heuresPassees;
    public EmployeTache() {}
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }
    public LocalDate getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(LocalDate dateDebutReelle) { this.dateDebutReelle = dateDebutReelle; }
    public LocalDate getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(LocalDate dateFinReelle) { this.dateFinReelle = dateFinReelle; }
    public int getHeuresPassees() { return heuresPassees; }
    public void setHeuresPassees(int heuresPassees) { this.heuresPassees = heuresPassees; }

}
