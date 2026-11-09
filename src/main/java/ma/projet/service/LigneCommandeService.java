package ma.projet.service;
import ma.projet.classes.*;
import ma.projet.beans.*;
import ma.projet.util.Dates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.criteria.*;
public class LigneCommandeService extends AbstractService<LigneCommandeProduit> {
    public LigneCommandeService() { super(LigneCommandeProduit.class); }
    @Override protected void validate(LigneCommandeProduit l) {
        super.validate(l);
        if (l.getQuantite() <= 0 || l.getPrixUnitaire() == null || l.getPrixUnitaire().signum() < 0)
            throw new IllegalArgumentException("Quantité positive et prix unitaire non négatif requis");
    }

}
