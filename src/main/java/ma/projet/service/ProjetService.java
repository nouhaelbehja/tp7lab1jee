package ma.projet.service;
import ma.projet.classes.*;
import ma.projet.beans.*;
import ma.projet.util.Dates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.criteria.*;
public class ProjetService extends AbstractService<Projet> {
    public ProjetService() { super(Projet.class); }
    @Override protected void validate(Projet p) { super.validate(p); Dates.verifier(p.getDateDebut(), p.getDateFin()); }
    public List<Tache> tachesPlanifiees(int id) {
        return read(s -> s.createQuery("from Tache t where t.projet.id = :id order by t.dateDebut, t.id", Tache.class).setParameter("id", id).getResultList());
    }
    public List<EmployeTache> tachesRealisees(int id) {
        return read(s -> s.createQuery("from EmployeTache e join fetch e.tache where e.tache.projet.id = :id and e.dateFinReelle is not null order by e.dateDebutReelle, e.id", EmployeTache.class).setParameter("id", id).getResultList());
    }
    public void afficherTachesRealisees(int id) {
        Projet p = findById(id);
        if (p == null) throw new IllegalArgumentException("Projet introuvable : " + id);
        System.out.println("Projet : " + id + "   Nom : " + p.getNom() + "   Date début : " + p.getDateDebut());
        System.out.println("Liste des tâches :");
        System.out.printf("%-6s %-22s %-20s %s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (EmployeTache e : tachesRealisees(id))
            System.out.printf("%-6d %-22s %-20s %s%n", e.getTache().getId(), e.getTache().getNom(), e.getDateDebutReelle().format(f), e.getDateFinReelle().format(f));
    }
}
