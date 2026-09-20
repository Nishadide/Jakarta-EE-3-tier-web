package store.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

/**
 * Customer stores the details of a customer of the company. A customer can
 * place many orders, so Customer has a one-to-many relationship with Order
 * (mapped by the "customer" field on the Order side). Orders are fetched
 * eagerly so the customer list can show the order count and the customer
 * details page can list the orders without a lazy-loading problem in the view.
 */
@Entity
@NamedQueries({
    // Retrieve every customer (used by "List of Customers").
    @NamedQuery(name = "Customer.findAll",
                query = "SELECT c FROM Customer c"),
    // Retrieve a single customer by primary key.
    @NamedQuery(name = "Customer.findById",
                query = "SELECT c FROM Customer c WHERE c.id = :id"),
    // Retrieve customers by name (used by "Search for a Customer").
    @NamedQuery(name = "Customer.findByName",
                query = "SELECT c FROM Customer c WHERE c.name = :name")
})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    // One customer has many orders. Cascade ALL so orders follow the customer;
    // EAGER so the collection is available to the JSF views.
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,
               fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        return (this.id != null || other.id == null)
                && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "store.entities.Customer[ id=" + id + " ]";
    }
}
