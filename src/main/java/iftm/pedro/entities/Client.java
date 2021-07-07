package iftm.pedro.entities;

import javax.persistence.*;

@Entity
@Table(name = "TB_CLIENT")
@NamedQuery(name = "Client.getClientsToBox", query = "SELECT c FROM Client c WHERE c.clientCpf NOT IN (SELECT client FROM Order)")
public class Client {

    @Id
    private String clientCpf;

    private String name;
    private String address;
    private String email;
    private String phone;
    private String instagram;

    @OneToOne (mappedBy = "client", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Order order;

    public Client(String clientCpf, String name, String address, String email, String phone, String instagram) {
        this.clientCpf = clientCpf;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.instagram = instagram;
    }

    public Client() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getClientCpf() {
        return clientCpf;
    }

    public void setClientCpf(String clientCpf) {
        this.clientCpf = clientCpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientCpf='" + clientCpf + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", instagram='" + instagram + '\'' +
                ", order=" + order +
                '}';
    }
}
