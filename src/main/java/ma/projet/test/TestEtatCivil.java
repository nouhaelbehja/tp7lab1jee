package ma.projet.test;
import ma.projet.beans.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;
import java.time.LocalDate;
public class TestEtatCivil {
    static LocalDate date(String s) { return LocalDate.parse(s); }
    public static void main(String[] args) {
        try { executer(); } finally { HibernateUtil.shutdown(); }
    }
    static void mariage(MariageService service, Homme h, Femme f, String debut, String fin, int enfants, String acte) {
        Mariage m = new Mariage(); m.setHomme(h); m.setFemme(f); m.setDateDebut(date(debut));
        m.setDateFin(fin == null ? null : date(fin)); m.setNbrEnfant(enfants);
        m.setLieu("Casablanca"); m.setNumeroActe(acte); service.create(m);
    }
    public static void executer() {
        FemmeService fs = new FemmeService(); HommeService hs = new HommeService(); MariageService ms = new MariageService();
        if (!fs.findAll().isEmpty() || !hs.findAll().isEmpty() || !ms.findAll().isEmpty())
            throw new IllegalStateException("Tests état civil : utiliser une base vide, données existantes conservées.");
        Femme[] femmes = new Femme[10]; Homme[] hommes = new Homme[5];
        for (int i = 0; i < 10; i++) {
            Femme f = new Femme(); f.setNom("FAMILLE" + (i + 1)); f.setPrenom("Femme" + (i + 1));
            f.setCin("F" + (1000 + i)); f.setDateNaissance(date("1960-01-01").plusYears(i));
            f.setAdresse("Casablanca"); f.setTelephone("061000000" + i); fs.create(f); femmes[i] = f;
        }
        for (int i = 0; i < 5; i++) {
            Homme h = new Homme(); h.setNom(i == 0 ? "SAFI" : "NOM" + i); h.setPrenom(i == 0 ? "SAID" : "Homme" + i);
            h.setCin("H" + (1000 + i)); h.setDateNaissance(date("1955-01-01").plusYears(i));
            h.setAdresse("Rabat"); h.setTelephone("062000000" + i); hs.create(h); hommes[i] = h;
        }
        mariage(ms, hommes[0], femmes[0], "1989-09-03", "1990-09-03", 0, "ACT01");
        mariage(ms, hommes[0], femmes[1], "1990-09-03", null, 4, "ACT02");
        mariage(ms, hommes[0], femmes[2], "1995-09-03", null, 2, "ACT03");
        mariage(ms, hommes[0], femmes[3], "2000-11-04", null, 3, "ACT04");
        mariage(ms, hommes[0], femmes[4], "2005-01-01", null, 1, "ACT05");
        mariage(ms, hommes[1], femmes[0], "1992-01-01", "1998-01-01", 2, "ACT06");
        // Quatre unions successives, sans quatre épouses simultanées.
        mariage(ms, hommes[2], femmes[5], "2000-01-01", "2001-01-01", 0, "ACT07");
        mariage(ms, hommes[2], femmes[6], "2002-01-01", "2003-01-01", 0, "ACT08");
        mariage(ms, hommes[2], femmes[7], "2004-01-01", "2005-01-01", 0, "ACT09");
        mariage(ms, hommes[2], femmes[8], "2006-01-01", "2007-01-01", 0, "ACT10");
        Verification.egal(10, fs.findAll().size(), "Création de dix femmes");
        Verification.egal(5, hs.findAll().size(), "Création de cinq hommes");
        fs.findAll().forEach(System.out::println);
        Verification.egal(femmes[0].getId(), fs.femmeLaPlusAgee().getId(), "Femme la plus âgée");
        System.out.println("Femme la plus âgée : " + fs.femmeLaPlusAgee());
        Verification.egal(4, hs.epousesEntreDates(hommes[0].getId(), date("2005-01-01"), date("2010-12-31")).size(), "Épouses sur la période");
        hs.epousesEntreDates(hommes[0].getId(), date("2005-01-01"), date("2010-12-31")).forEach(System.out::println);
        Verification.egal(2L, fs.nombreEnfants(femmes[0].getId(), date("1989-01-01"), date("2000-12-31")), "Somme des enfants, requête native nommée");
        Verification.egal(0L, fs.nombreEnfants(femmes[9].getId(), date("1980-01-01"), date("2026-12-31")), "Femme sans mariage");
        Verification.egal(1, fs.femmesMarieesAuMoinsDeuxFois().size(), "Femmes mariées au moins deux fois");
        fs.femmesMarieesAuMoinsDeuxFois().forEach(System.out::println);
        Verification.egal(1L, hs.nombreHommesMariesAQuatreFemmes(date("2005-01-01"), date("2010-12-31")), "Criteria : quatre épouses simultanées");
        hs.hommesMariesAQuatreFemmes(date("2005-01-01"), date("2010-12-31")).forEach(System.out::println);
        Verification.egal(0L, hs.nombreHommesMariesAQuatreFemmes(date("1980-01-01"), date("1985-12-31")), "Criteria : aucune union");
        Verification.egal(5, ms.mariagesHomme(hommes[0].getId()).size(), "Historique complet");
        Verification.invalide(() -> hs.epousesEntreDates(hommes[0].getId(), date("2010-01-01"), date("2000-01-01")), "Dates inversées");
        ms.afficherMariagesHomme(hommes[0].getId());
        System.out.println("TEST ÉTAT CIVIL TERMINÉ");
    }
}
