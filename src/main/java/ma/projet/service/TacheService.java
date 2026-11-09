package ma.projet.service;
import ma.projet.classes.*;
import ma.projet.beans.*;
import ma.projet.util.Dates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.criteria.*;
public class TacheService extends AbstractService<Tache> {
    public TacheService() { super(Tache.class); }
    @Override protected void validate(Tache t) {
        super.validate(t); Dates.verifier(t.getDateDebut(), t.getDateFin());
        if (t.getPrix() == null || t.getPrix().signum() < 0) throw new IllegalArgumentException("Prix non négatif requis");
    }
    public List<Tache> tachesPrixSuperieur1000() {
        return read(s -> s.createNamedQuery("Tache.prixSuperieur1000", Tache.class).getResultList());
    }
    public List<Tache> tachesRealiseesEntreDates(LocalDate debut, LocalDate fin) {
        Dates.verifier(debut, fin);
        return read(s -> s.createQuery("select distinct e.tache from EmployeTache e where e.dateDebutReelle >= :d and e.dateFinReelle <= :f", Tache.class)
            .setParameter("d", debut).setParameter("f", fin).getResultList());
    }
}
