package store.entities;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Order represents a single customer order. Each order is for exactly one
 * product (the many-to-one side of the Customer to Order relationship, and a
 * many-to-one link to the ordered Product).
 *
 * The order stores a snapshot of the unit price at the time it was placed, the
 * quantity, the computed total price and the creation timestamp, so the line
 * item keeps its price even if the product price changes later.
 *
 * The JPA entity name is set to "Orders" and the table to "ORDERS" because
 * ORDER is a reserved word in SQL; this avoids clashes in both the generated
 * DDL and JPQL queries.
 */
@Entity(name = "Orders")
@Table(name = "ORDERS")
@NamedQueries({
    // Retrieve every order (used by "List of Orders").
    @NamedQuery(name = "Orders.findAll",
                query = "SELECT o FROM Orders o"),
    // Retrieve a single order by primary key (used by "Search for an Order").
    @NamedQuery(name = "Orders.findById",
                query = "SELECT o FROM Orders o WHERE o.id = :id"),
    // Retrieve all orders that belong to one customer.
    @NamedQuery(name = "Orders.findByCustomer",
                query = "SELECT o FROM Orders o WHERE o.customer.id = :customerId")
})
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // The customer who placed this order (many orders to one customer).
    @ManyToOne
    private Customer customer;

    // The single product this order is for (many orders to one product).
    @ManyToOne
    private Product product;

    private Integer quantity;
    private Double unitPrice;          // snapshot of the product price
    private Double totalPrice;         // unitPrice * quantity

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        return (this.id != null || other.id == null)
                && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "store.entities.Order[ id=" + id + " ]";
    }
}
