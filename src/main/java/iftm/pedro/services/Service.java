package iftm.pedro.services;

import iftm.pedro.entities.Client;
import iftm.pedro.entities.Glasses;
import iftm.pedro.entities.Order;
import iftm.pedro.repositories.ClientRepository;
import iftm.pedro.repositories.GlassesRepository;
import iftm.pedro.repositories.OrderRepository;

import java.util.List;

public class Service {
    private static final ClientRepository clientRepository = new ClientRepository();
    private static final GlassesRepository glassesRepository = new GlassesRepository();
    private static final OrderRepository orderRepository = new OrderRepository();

    public List<Client> getClients (){
        return clientRepository.findAll();
    }

    public List<Glasses> getGlasses(){
        return glassesRepository.findAll();
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public List<Client> getClientsToBox(){
        return clientRepository.clientsToBox();
    }

    public <T> void saveObject(T object){
        if (object.getClass() == Client.class){
            clientRepository.save((Client) object);
        } else if (object.getClass() == Glasses.class){
            glassesRepository.save((Glasses) object);
        } else {
            orderRepository.save((Order) object);
        }
    }

    public <T> void updateObject(T object, String id){
        if (object.getClass() == Client.class){
            clientRepository.update((Client) object,id);
        } else if (object.getClass() == Glasses.class){
            glassesRepository.update((Glasses) object,id);
        } else {
            orderRepository.update((Order) object,id);
        }
    }

    public <T> void removeObject(T object){
        if (object.getClass() == Client.class){
            clientRepository.remove(((Client) object).getClientCpf());
        } else if (object.getClass() == Glasses.class){
            glassesRepository.remove(((Glasses) object).getId());
        } else {
            orderRepository.remove(((Order) object).getCod());
        }
    }

    @SuppressWarnings(value = "unchecked")
    public <T> T findObject (String id){
        T object;

        if(id.startsWith("GLA")){
            object = (T) glassesRepository.findById(id);

        } else {
            object = (T) clientRepository.findById(id);
        }

        return object;
    }
}
