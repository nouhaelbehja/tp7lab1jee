package ma.projet.test;
import java.util.Objects;
final class Verification {
    private Verification() {}
    static void egal(Object attendu, Object obtenu, String nom) {
        if (!Objects.equals(attendu, obtenu))
            throw new IllegalStateException("ÉCHEC " + nom + " : attendu=" + attendu + ", obtenu=" + obtenu);
        System.out.println("[OK] " + nom + " : " + obtenu);
    }
    static void invalide(Runnable operation, String nom) {
        try { operation.run(); } catch (IllegalArgumentException e) { System.out.println("[OK] " + nom); return; }
        throw new IllegalStateException("ÉCHEC : " + nom);
    }
}
