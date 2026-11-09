package ma.projet.dao;
import java.util.List;
public interface IDao<T> {
    boolean create(T entity);
    boolean update(T entity);
    boolean delete(T entity);
    T findById(int id);
    List<T> findAll();
}
