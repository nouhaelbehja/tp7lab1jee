package ma.projet.classes;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"commande_id", "produit_id"}))
@Entity
public class LigneCommandeProduit extends Entite {
    @ManyToOne(optional = false) @JoinColumn(nullable = false)
    private Commande commande;
    @ManyToOne(optional = false) @JoinColumn(nullable = false)
    private Produit produit;
    @Column(nullable = false)
    private int quantite;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal prixUnitaire;
    public LigneCommandeProduit() {}
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

}
