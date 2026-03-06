package repository;

import org.hibernate.Session;

public abstract class BaseDao<T, ID> {
    private final Class<T> entityClass;

    protected BaseDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(Session session, T entity) {
      try {
          session.persist(entity);
      } catch (RuntimeException e) {
          throw new RuntimeException(e);
      }
    }
}
