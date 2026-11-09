package ma.projet.service;
import ma.projet.classes.*;
import ma.projet.beans.*;
import ma.projet.util.Dates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.persistence.criteria.*;
public class ProduitService extends AbstractService<Produit> {
    public ProduitService() { super(Produit.class); }
    @Override protected void validate(Produit p) {
        super.validate(p);
        if (p.getPrix() == null || p.getPrix().signum() < 0 || p.getStock() < 0)
            throw new IllegalArgumentException("Prix et stock non négatifs requis");
    }
    public List<Produit> produitsParCategorie(int id) {
        return read(s -> s.createQuery("from Produit p where p.categorie.id = :id order by p.reference", Produit.class)
            .setParameter("id", id).getResultList());
    }
    public List<Produit> produitsCommandesEntreDates(LocalDate debut, LocalDate fin) {
        Dates.verifier(debut, fin);
        return read(s -> s.createQuery("select distinct l.produit from LigneCommandeProduit l where l.commande.date between :d and :f", Produit.class)
            .setParameter("d", debut).setParameter("f", fin).getResultList());
    }
    public List<LigneCommandeProduit> produitsParCommande(int id) {
        return read(s -> s.createQuery("from LigneCommandeProduit l join fetch l.produit join fetch l.commande where l.commande.id = :id order by l.produit.reference", LigneCommandeProduit.class)
            .setParameter("id", id).getResultList());
    }
    public List<Produit> produitsPrixSuperieur100() {
        return read(s -> s.createNamedQuery("Produit.prixSuperieur100", Produit.class).getResultList());
    }
    public void afficherProduitsCommande(int id) {
        Commande c = new CommandeService().findById(id);
        if (c == null) throw new IllegalArgumentException("Commande introuvable : " + id);
        System.out.println("Commande : " + id + "     Date : " + c.getDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", java.util.Locale.FRENCH)));
        System.out.println("Liste des produits :");
        System.out.printf("%-15s %-15s %s%n", "Référence", "Prix", "Quantité");
        for (LigneCommandeProduit l : produitsParCommande(id))
            System.out.printf("%-15s %-15s %d%n", l.getProduit().getReference(), l.getPrixUnitaire() + " DH", l.getQuantite());
    }
}
