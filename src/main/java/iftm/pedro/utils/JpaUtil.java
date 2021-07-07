package iftm.pedro.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public interface JpaUtil <T,R> {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-mysql");

    default EntityManager getEntityManager(){
        return factory.createEntityManager();
    }

    List<R> findAll();

    R findById(T id);

    void save (R object);

    void remove (T id);

    void update (R object, T id);

}
