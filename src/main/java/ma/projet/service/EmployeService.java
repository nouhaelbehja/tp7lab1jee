package ma.projet.service;
import ma.projet.classes.*;
import ma.projet.beans.*;
import ma.projet.util.Dates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.criteria.*;
public class EmployeService extends AbstractService<Employe> {
    public EmployeService() { super(Employe.class); }

    public List<Tache> tachesRealisees(int id) {
        return read(s -> s.createQuery("select distinct e.tache from EmployeTache e where e.employe.id = :id and e.dateFinReelle is not null", Tache.class).setParameter("id", id).getResultList());
    }
    public List<Projet> projetsGeres(int id) {
        return read(s -> s.createQuery("from Projet p where p.chefProjet.id = :id order by p.id", Projet.class).setParameter("id", id).getResultList());
    }
}
