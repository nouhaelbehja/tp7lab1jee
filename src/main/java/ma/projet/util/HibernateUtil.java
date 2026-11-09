package ma.projet.util;
import java.io.InputStream;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ma.projet.classes.*;
import ma.projet.beans.*;

public final class HibernateUtil {
    private static final SessionFactory FACTORY = build();
    private HibernateUtil() {}
    private static SessionFactory build() {
        Properties properties = new Properties();
        try (InputStream input = HibernateUtil.class.getResourceAsStream("/application.properties")) {
            if (input == null) throw new IllegalStateException("application.properties introuvable");
            properties.load(input);
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");
            if (user == null || password == null)
                throw new IllegalStateException("Définir DB_USER et DB_PASSWORD avant de démarrer.");
            properties.setProperty("hibernate.connection.username", user);
            properties.setProperty("hibernate.connection.password", password);
            String url = System.getenv("DB_URL");
            if (url != null && !url.isBlank()) properties.setProperty("hibernate.connection.url", url);
            Configuration configuration = new Configuration().addProperties(properties);
            for (Class<?> type : new Class<?>[] {Categorie.class, Produit.class, Commande.class,
                LigneCommandeProduit.class, Employe.class, Projet.class, Tache.class, EmployeTache.class,
                Personne.class, Homme.class, Femme.class, Mariage.class})
                configuration.addAnnotatedClass(type);
            return configuration.buildSessionFactory();
        } catch (Exception e) { throw new IllegalStateException("Initialisation Hibernate impossible", e); }
    }
    public static SessionFactory getSessionFactory() { return FACTORY; }
    public static void shutdown() { if (!FACTORY.isClosed()) FACTORY.close(); }
}
