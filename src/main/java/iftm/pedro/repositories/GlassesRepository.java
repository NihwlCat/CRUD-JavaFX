package iftm.pedro.repositories;

import iftm.pedro.entities.Glasses;
import iftm.pedro.utils.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class GlassesRepository implements JpaUtil<String, Glasses> {

    @Override
    public List<Glasses> findAll() {
        EntityManager manager = getEntityManager();

        TypedQuery<Glasses> query = manager.createQuery("SELECT g FROM Glasses g", Glasses.class);

        List<Glasses> glasses = query.getResultList();
        manager.close();
        return glasses;
    }

    @Override
    public Glasses findById(String id) {
        EntityManager manager = getEntityManager();
        Glasses g = manager.find(Glasses.class,id);

        manager.close();
        return g;
    }

    @Override
    public void save(Glasses object) {
        EntityManager manager = getEntityManager();

        manager.getTransaction().begin();

        manager.persist(object);

        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void remove(String id) {
        EntityManager manager = getEntityManager();
        manager.getTransaction().begin();

        manager.remove(manager.find(Glasses.class,id));

        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void update(Glasses object, String id) {
        EntityManager manager = getEntityManager();

        manager.getTransaction().begin();

        Glasses g = manager.find(Glasses.class,id);
        g.setImageLink(object.getImageLink());
        g.setPrescription(object.getPrescription());

        manager.getTransaction().commit();
        manager.close();
    }
}
