package ma.projet.service;
import ma.projet.classes.*;
import ma.projet.beans.*;
import ma.projet.util.Dates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.criteria.*;
public class EmployeTacheService extends AbstractService<EmployeTache> {
    public EmployeTacheService() { super(EmployeTache.class); }
    @Override protected void validate(EmployeTache e) {
        super.validate(e);
        if (e.getDateDebutReelle() == null || e.getHeuresPassees() < 0) throw new IllegalArgumentException("Date de début et heures valides requises");
        if (e.getDateFinReelle() != null) Dates.verifier(e.getDateDebutReelle(), e.getDateFinReelle());
    }

}
