package ma.projet.beans;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import ma.projet.classes.Entite;

@Entity
public class Mariage extends Entite {
    @ManyToOne(optional = false) @JoinColumn(nullable = false)
    private Homme homme;
    @ManyToOne(optional = false) @JoinColumn(nullable = false)
    private Femme femme;
    @Column(nullable = false)
    private LocalDate dateDebut;

    private LocalDate dateFin;
    @Column(nullable = false)
    private int nbrEnfant;
    @Column(nullable = false)
    private String lieu;
    @Column(nullable = false, unique = true)
    private String numeroActe;
    public Mariage() {}
    public Homme getHomme() { return homme; }
    public void setHomme(Homme homme) { this.homme = homme; }
    public Femme getFemme() { return femme; }
    public void setFemme(Femme femme) { this.femme = femme; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public void setNbrEnfant(int nbrEnfant) { this.nbrEnfant = nbrEnfant; }
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public String getNumeroActe() { return numeroActe; }
    public void setNumeroActe(String numeroActe) { this.numeroActe = numeroActe; }

}
