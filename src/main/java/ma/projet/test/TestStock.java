package ma.projet.test;
import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TestStock {
    static LocalDate date(String s) { return LocalDate.parse(s); }
    public static void main(String[] args) {
        try { executer(); } finally { HibernateUtil.shutdown(); }
    }
    public static void executer() {
        ProduitService ps = new ProduitService();
        CategorieService cs = new CategorieService();
        CommandeService commandes = new CommandeService();
        LigneCommandeService ls = new LigneCommandeService();
        if (!ps.findAll().isEmpty() || !cs.findAll().isEmpty() || !commandes.findAll().isEmpty() || !ls.findAll().isEmpty())
            throw new IllegalStateException("Tests stock : utiliser une base vide, données existantes conservées.");
        Categorie c = new Categorie(); c.setCode("INFO"); c.setLibelle("Informatique");
        c.setDescription("Périphériques et accessoires"); cs.create(c);
        Categorie autre = new Categorie(); autre.setCode("AUTRE"); autre.setLibelle("Divers"); cs.create(autre);
        Categorie temporaire = new Categorie(); temporaire.setCode("TEMP"); temporaire.setLibelle("Temporaire");
        cs.create(temporaire);
        temporaire.setLibelle("Modifiée"); cs.update(temporaire);
        Verification.egal("Modifiée", cs.findById(temporaire.getId()).getLibelle(), "CRUD modification");
        cs.delete(temporaire); Verification.egal(null, cs.findById(temporaire.getId()), "CRUD suppression");
        Produit[] produits = new Produit[4];
        String[] refs = {"ES12", "ZR85", "EE85", "USB10"};
        int[] prix = {120, 100, 200, 50};
        for (int i = 0; i < produits.length; i++) {
            Produit p = new Produit(); p.setReference(refs[i]); p.setPrix(BigDecimal.valueOf(prix[i]));
            p.setCategorie(i == 3 ? autre : c); p.setMarque("AtlasTech"); p.setStock(50);
            ps.create(p); produits[i] = p;
        }
        Commande commande = new Commande(); commande.setDate(date("2026-03-14"));
        commande.setClient("Client démonstration"); commande.setStatut("VALIDEE"); commandes.create(commande);
        int[] quantites = {7, 14, 5};
        for (int i = 0; i < 3; i++) {
            LigneCommandeProduit l = new LigneCommandeProduit(); l.setCommande(commande); l.setProduit(produits[i]);
            l.setQuantite(quantites[i]); l.setPrixUnitaire(produits[i].getPrix()); ls.create(l);
        }
        Verification.egal(3, ps.produitsParCategorie(c.getId()).size(), "Produits par catégorie");
        Verification.egal(3, ps.produitsCommandesEntreDates(date("2026-03-14"), date("2026-03-14")).size(), "Commandes aux bornes incluses");
        Verification.egal(0, ps.produitsCommandesEntreDates(date("2025-01-01"), date("2025-12-31")).size(), "Intervalle sans commande");
        Verification.egal(3, ps.produitsParCommande(commande.getId()).size(), "Produits de la commande");
        Verification.egal(2, ps.produitsPrixSuperieur100().size(), "Requête nommée prix strictement supérieur à 100");
        Verification.egal(0, ps.produitsParCategorie(-1).size(), "Catégorie inexistante");
        Verification.invalide(() -> ps.produitsCommandesEntreDates(date("2026-03-15"), date("2026-03-14")), "Dates inversées");
        Produit invalide = new Produit(); invalide.setPrix(BigDecimal.valueOf(-1));
        Verification.invalide(() -> ps.create(invalide), "Prix négatif refusé");
        ps.afficherProduitsCommande(commande.getId());
        ps.produitsParCategorie(c.getId()).forEach(p -> System.out.println(p.getReference() + " | " + p.getMarque() + " | stock : " + p.getStock()));
        System.out.println("TEST STOCK TERMINÉ");
    }
}
