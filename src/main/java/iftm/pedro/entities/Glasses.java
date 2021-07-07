package iftm.pedro.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table (name = "TB_GLASSES")
@GenericGenerator(name = "glasses-gen", parameters = {
        @Parameter(name = "prefix", value = "GLA"),
        @Parameter(name = "identifier", value = "X")},
        strategy = "iftm.pedro.utils.IdGenerator")
public class Glasses {

    @Id
    @GeneratedValue (generator = "glasses-gen")
    private String id;

    private String imageLink;
    private String prescription;

    @OneToMany(mappedBy = "glasses", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Order> orders;

    public Glasses(String id, String imageLink, String prescription) {
        this.id = id;
        this.imageLink = imageLink;
        this.prescription = prescription;
    }

    public Glasses() {
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    @Override
    public String toString() {
        return "Glasses{" +
                "id='" + id + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", prescription='" + prescription + '\'' +
                '}';
    }
}
