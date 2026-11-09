package ma.projet.beans;
import jakarta.persistence.*;
@Entity
@NamedQuery(name = "Femme.auMoinsDeuxMariages",
 query = "select f from Femme f where (select count(m) from Mariage m where m.femme = f) >= 2 order by f.id")
@NamedNativeQuery(name = "Femme.nombreEnfants",
 query = "select coalesce(sum(m.nbrEnfant), 0) from Mariage m where m.femme_id = :femmeId and m.dateDebut between :debut and :fin")
public class Femme extends Personne { public Femme() {} }
