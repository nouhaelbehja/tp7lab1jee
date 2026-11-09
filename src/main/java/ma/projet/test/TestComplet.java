package ma.projet.test;
import ma.projet.util.HibernateUtil;
public class TestComplet {
    public static void main(String[] args) {
        try {
            TestStock.executer();
            TestProjets.executer();
            TestEtatCivil.executer();
            System.out.println("TOUTES LES VÉRIFICATIONS ONT RÉUSSI");
        } finally { HibernateUtil.shutdown(); }
    }
}
