package ma.projet.service;
import java.util.List;
import java.util.function.Function;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.projet.dao.IDao;
import ma.projet.classes.Entite;
import ma.projet.util.HibernateUtil;

public abstract class AbstractService<T extends Entite> implements IDao<T> {
    private final Class<T> type;
    protected AbstractService(Class<T> type) { this.type = type; }
    protected <R> R read(Function<Session, R> operation) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return operation.apply(session);
        }
    }
    protected void validate(T entity) {
        if (entity == null) throw new IllegalArgumentException("Entité obligatoire");
    }
    private boolean write(T entity, int action) {
        validate(entity);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                if (action == 0) session.persist(entity);
                else if (action == 1) session.merge(entity);
                else session.remove(session.merge(entity));
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }
    public boolean create(T entity) { return write(entity, 0); }
    public boolean update(T entity) { return write(entity, 1); }
    public boolean delete(T entity) { return write(entity, 2); }
    public T findById(int id) { return read(s -> s.get(type, id)); }
    public List<T> findAll() {
        return read(s -> s.createQuery("from " + type.getSimpleName() + " e order by e.id", type).getResultList());
    }
}
