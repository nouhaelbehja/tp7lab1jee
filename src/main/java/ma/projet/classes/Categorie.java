package ma.projet.classes;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
public class Categorie extends Entite {
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String libelle;

    private String description;
    public Categorie() {}
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}
