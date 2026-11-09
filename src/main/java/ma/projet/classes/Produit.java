package ma.projet.classes;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
@NamedQuery(name = "Produit.prixSuperieur100", query = "from Produit p where p.prix > 100 order by p.reference")
@Entity
public class Produit extends Entite {
    @Column(nullable = false, unique = true)
    private String reference;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal prix;
    @ManyToOne(optional = false) @JoinColumn(nullable = false)
    private Categorie categorie;

    private String marque;
    @Column(nullable = false)
    private int stock;
    public Produit() {}
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

}
