package ma.projet.service;
import ma.projet.classes.*;
import ma.projet.beans.*;
import ma.projet.util.Dates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.criteria.*;
public class MariageService extends AbstractService<Mariage> {
    public MariageService() { super(Mariage.class); }
    @Override protected void validate(Mariage m) {
        super.validate(m);
        if (m.getDateDebut() == null || m.getNbrEnfant() < 0) throw new IllegalArgumentException("Date et nombre d'enfants valides requis");
        if (m.getDateFin() != null) Dates.verifier(m.getDateDebut(), m.getDateFin());
    }
    public List<Mariage> mariagesHomme(int id) {
        return read(s -> s.createQuery("from Mariage m join fetch m.femme where m.homme.id = :id order by m.dateDebut, m.id", Mariage.class).setParameter("id", id).getResultList());
    }
    public void afficherMariagesHomme(int id) {
        Homme h = new HommeService().findById(id);
        if (h == null) throw new IllegalArgumentException("Homme introuvable : " + id);
        System.out.println("Nom : " + h.getNom() + " " + h.getPrenom());
        List<Mariage> mariages = mariagesHomme(id);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (boolean enCours : new boolean[]{true, false}) {
            System.out.println(enCours ? "Mariages En Cours :" : "Mariages échoués :");
            int n = 0;
            for (Mariage m : mariages) {
                if ((m.getDateFin() == null) != enCours) continue;
                System.out.println(++n + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom()
                    + "   Date Début : " + m.getDateDebut().format(f)
                    + (m.getDateFin() == null ? "" : "   Date Fin : " + m.getDateFin().format(f))
                    + "   Nbr Enfants : " + m.getNbrEnfant() + "   Lieu : " + m.getLieu());
            }
        }
    }
}
