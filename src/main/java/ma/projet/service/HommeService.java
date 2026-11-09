package ma.projet.service;
import ma.projet.classes.*;
import ma.projet.beans.*;
import ma.projet.util.Dates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.criteria.*;
public class HommeService extends AbstractService<Homme> {
    public HommeService() { super(Homme.class); }

    public List<Femme> epousesEntreDates(int id, LocalDate debut, LocalDate fin) {
        Dates.verifier(debut, fin);
        return read(s -> s.createQuery("select distinct m.femme from Mariage m where m.homme.id = :id and m.dateDebut <= :f and (m.dateFin is null or m.dateFin >= :d)", Femme.class)
            .setParameter("id", id).setParameter("d", debut).setParameter("f", fin).getResultList());
    }
    /** Quatre épouses simultanées au moins un jour de l'intervalle, bornes incluses. */
    public List<Homme> hommesMariesAQuatreFemmes(LocalDate debut, LocalDate fin) {
        Dates.verifier(debut, fin);
        return read(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Homme> query = cb.createQuery(Homme.class);
            Root<Homme> homme = query.from(Homme.class);
            Subquery<Integer> event = query.subquery(Integer.class);
            Root<Mariage> pivot = event.from(Mariage.class);
            // Un maximum apparaît au début de l'intervalle ou au début d'un mariage.
            Expression<LocalDate> jour = cb.<LocalDate>selectCase()
                .when(cb.lessThan(pivot.<LocalDate>get("dateDebut"), debut), debut)
                .otherwise(pivot.<LocalDate>get("dateDebut"));
            Subquery<Long> actifs = event.subquery(Long.class);
            Root<Mariage> mariage = actifs.from(Mariage.class);
            actifs.select(cb.countDistinct(mariage.get("femme").get("id"))).where(
                cb.equal(mariage.get("homme"), homme),
                cb.lessThanOrEqualTo(mariage.<LocalDate>get("dateDebut"), jour),
                cb.or(cb.isNull(mariage.get("dateFin")), cb.greaterThanOrEqualTo(mariage.<LocalDate>get("dateFin"), jour)));
            event.select(cb.literal(1)).where(cb.equal(pivot.get("homme"), homme),
                cb.lessThanOrEqualTo(pivot.<LocalDate>get("dateDebut"), fin),
                cb.or(cb.isNull(pivot.get("dateFin")), cb.greaterThanOrEqualTo(pivot.<LocalDate>get("dateFin"), debut)),
                cb.equal(actifs, 4L));
            query.select(homme).where(cb.exists(event)).orderBy(cb.asc(homme.get("id")));
            return s.createQuery(query).getResultList();
        });
    }
    public long nombreHommesMariesAQuatreFemmes(LocalDate debut, LocalDate fin) {
        return hommesMariesAQuatreFemmes(debut, fin).size();
    }
}
