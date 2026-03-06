package repository;

import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T, ID> {
    private final Class<T> entityClass;

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(Session session, T entity) {
        try {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            System.out.println("Сохранить в БД не удалось: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void update(Session session, T entity) {
        try {
            session.beginTransaction();
            T t = session.merge(entity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            System.out.print("Обновить в БД не удалось");
            throw new RuntimeException(e);
        }
    }

    public void delete(Session session, T entity) {
        try {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            System.out.println("Удалить из БД не удалось: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<T> findById(Session session, Long id) {
        var entity = session.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    public List<T> findAll(Session session) {
        String hq = "from " + entityClass.getSimpleName();
        return session.createQuery(hq, entityClass).list();
    }
}
