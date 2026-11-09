package ma.projet.classes;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
public class Commande extends Entite {
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String client;
    @Column(nullable = false)
    private String statut;
    public Commande() {}
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

}
