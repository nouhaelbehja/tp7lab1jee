package ma.projet.service;
import ma.projet.classes.*;
import ma.projet.beans.*;
import ma.projet.util.Dates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.criteria.*;
public class FemmeService extends AbstractService<Femme> {
    public FemmeService() { super(Femme.class); }

    public long nombreEnfants(int id, LocalDate debut, LocalDate fin) {
        Dates.verifier(debut, fin);
        return read(s -> ((Number) s.createNamedQuery("Femme.nombreEnfants")
            .setParameter("femmeId", id).setParameter("debut", debut).setParameter("fin", fin).getSingleResult()).longValue());
    }
    public List<Femme> femmesMarieesAuMoinsDeuxFois() {
        return read(s -> s.createNamedQuery("Femme.auMoinsDeuxMariages", Femme.class).getResultList());
    }
    public Femme femmeLaPlusAgee() {
        return read(s -> s.createQuery("from Femme f order by f.dateNaissance, f.id", Femme.class).setMaxResults(1).uniqueResult());
    }
}
