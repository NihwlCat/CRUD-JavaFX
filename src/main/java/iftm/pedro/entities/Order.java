package iftm.pedro.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "TB_ORDER")
@GenericGenerator(name = "order-gen", parameters = {
        @Parameter(name = "prefix", value = "ORD"),
        @Parameter(name = "identifier", value = "BR")},
        strategy = "iftm.pedro.utils.IdGenerator")
public class Order {

    @Id
    @GeneratedValue(generator = "order-gen")
    private String cod;

    private Date orderDate;

    private double price;
    private double discount;

    @OneToOne
    @JoinColumn(name = "client_cpf")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "glasses_id")
    private Glasses glasses;

    public Order(String cod, Date orderDate, double price, double discount, Client client, Glasses glasses) {
        this.cod = cod;
        this.orderDate = orderDate;
        this.price = price;
        this.discount = discount;

        this.client = client;
        this.glasses = glasses;
    }

    public Order() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Glasses getGlasses() {
        return glasses;
    }

    public void setGlasses(Glasses glasses) {
        this.glasses = glasses;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "cod='" + cod + '\'' +
                ", orderDate=" + orderDate +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
