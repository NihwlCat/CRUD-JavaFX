package iftm.pedro.repositories;

import iftm.pedro.entities.Client;
import iftm.pedro.utils.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClientRepository implements JpaUtil<String, Client> {

    public ClientRepository(){}

    @Override
    public List<Client> findAll() {
        EntityManager manager = getEntityManager();

        TypedQuery<Client> query = manager.createQuery("SELECT c FROM Client c", Client.class);

        List<Client> clients = query.getResultList();
        manager.close();
        return clients;
    }

    public List<Client> clientsToBox() {
        EntityManager manager = getEntityManager();

        TypedQuery<Client> query = manager.createNamedQuery("Client.getClientsToBox",Client.class);
        List<Client> clients = query.getResultList();
        manager.close();
        return clients;
    }

    @Override
    public Client findById(String id) {
        EntityManager manager = getEntityManager();
        Client c = manager.find(Client.class,id);

        manager.close();
        return c;
    }

    @Override
    public void save(Client object) {
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

        manager.remove(manager.find(Client.class,id));

        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void update(Client object, String id) {
        EntityManager manager = getEntityManager();

        manager.getTransaction().begin();

        Client c = manager.find(Client.class,id);
        c.setName(object.getName());
        c.setEmail(object.getEmail());
        c.setAddress(object.getAddress());
        c.setPhone(object.getPhone());
        c.setInstagram(object.getInstagram());

        manager.getTransaction().commit();
        manager.close();
    }

}
