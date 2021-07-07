package iftm.pedro.repositories;

import iftm.pedro.entities.Order;
import iftm.pedro.utils.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderRepository implements JpaUtil<String, Order> {

    @Override
    public List<Order> findAll() {
        EntityManager manager = getEntityManager();

        TypedQuery<Order> query = manager.createQuery("SELECT o FROM Order o", Order.class);

        List<Order> orders = query.getResultList();
        manager.close();
        return orders;
    }

    @Override
    public Order findById(String id) {
        EntityManager manager = getEntityManager();
        Order o = manager.find(Order.class,id);

        manager.close();
        return o;
    }

    @Override
    public void save(Order object) {
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

        manager.remove(manager.find(Order.class,id));

        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void update(Order object, String id) {
        EntityManager manager = getEntityManager();

        manager.getTransaction().begin();

        Order o = manager.find(Order.class,id);
        o.setOrderDate(object.getOrderDate());
        o.setDiscount(object.getDiscount());
        o.setPrice(object.getPrice());
        o.setGlasses(object.getGlasses());

        manager.getTransaction().commit();
        manager.close();
    }
}
