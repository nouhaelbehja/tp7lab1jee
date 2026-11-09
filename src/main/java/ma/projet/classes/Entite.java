package ma.projet.classes;
import jakarta.persistence.*;
@MappedSuperclass
public abstract class Entite {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() { return id; }
}
