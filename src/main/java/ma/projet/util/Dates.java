package ma.projet.util;
import java.time.LocalDate;
public final class Dates {
    private Dates() {}
    public static void verifier(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null || debut.isAfter(fin))
            throw new IllegalArgumentException("Intervalle de dates invalide");
    }
}
