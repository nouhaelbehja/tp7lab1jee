package ma.projet.test;
import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
public class TestProjets {
    static LocalDate date(String s) { return LocalDate.parse(s); }
    public static void main(String[] args) {
        try { executer(); } finally { HibernateUtil.shutdown(); }
    }
    public static void executer() {
        EmployeService es = new EmployeService(); ProjetService ps = new ProjetService();
        TacheService ts = new TacheService(); EmployeTacheService ets = new EmployeTacheService();
        if (!es.findAll().isEmpty() || !ps.findAll().isEmpty() || !ts.findAll().isEmpty() || !ets.findAll().isEmpty())
            throw new IllegalStateException("Tests projets : utiliser une base vide, données existantes conservées.");
        Employe e = new Employe(); e.setNom("EL AMRANI"); e.setPrenom("Sara");
        e.setTelephone("0600000001"); e.setEmail("sara@example.test"); e.setSpecialite("Java"); es.create(e);
        Projet p = new Projet(); p.setNom("Gestion de stock"); p.setChefProjet(e);
        p.setDateDebut(date("2026-01-14")); p.setDateFin(date("2026-06-30"));
        p.setBudget(BigDecimal.valueOf(45000)); p.setStatut("EN_COURS"); ps.create(p);
        String[] noms = {"Analyse", "Conception", "Développement", "Livraison"};
        int[] prix = {1200, 1000, 5000, 800};
        for (int i = 0; i < 4; i++) {
            Tache t = new Tache(); t.setNom(noms[i]); t.setProjet(p);
            t.setDateDebut(date("2026-02-01").plusMonths(i)); t.setDateFin(date("2026-02-25").plusMonths(i));
            t.setPrix(BigDecimal.valueOf(prix[i])); t.setPriorite(i == 2 ? "HAUTE" : "NORMALE"); ts.create(t);
            EmployeTache et = new EmployeTache(); et.setEmploye(e); et.setTache(t);
            et.setDateDebutReelle(date("2026-02-10").plusMonths(i));
            et.setDateFinReelle(i == 3 ? null : date("2026-02-20").plusMonths(i));
            et.setHeuresPassees(30 + i * 5); ets.create(et);
        }
        Verification.egal(3, es.tachesRealisees(e.getId()).size(), "Tâches terminées de l'employé");
        Verification.egal(1, es.projetsGeres(e.getId()).size(), "Projets gérés");
        Verification.egal(4, ps.tachesPlanifiees(p.getId()).size(), "Tâches planifiées");
        Verification.egal(3, ps.tachesRealisees(p.getId()).size(), "Réalisations et dates réelles");
        Verification.egal(2, ts.tachesPrixSuperieur1000().size(), "Prix strictement supérieur à 1000");
        Verification.egal(1, ts.tachesRealiseesEntreDates(date("2026-02-10"), date("2026-02-20")).size(), "Réalisation incluse aux bornes");
        Verification.egal(0, ts.tachesRealiseesEntreDates(date("2025-01-01"), date("2025-12-31")).size(), "Aucune réalisation hors période");
        Verification.invalide(() -> ts.tachesRealiseesEntreDates(date("2026-03-01"), date("2026-02-01")), "Intervalle invalide");
        ps.afficherTachesRealisees(p.getId());
        System.out.println("Budget : " + p.getBudget() + " DH | Statut : " + p.getStatut());
        System.out.println("TEST PROJETS TERMINÉ");
    }
}
