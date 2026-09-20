package store.ejb;

import java.util.Date;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import store.entities.Customer;
import store.entities.Order;
import store.entities.Product;

/**
 * OrderEJB is the stateless session bean that holds all business logic for
 * orders, including the stock management required by the assignment:
 *
 *  - Creating an order reduces the ordered product's stock by the quantity.
 *  - Deleting an order restores that quantity back to the product's stock.
 *
 * The order also records a snapshot of the unit price and the computed total
 * price, so the line item keeps its price independently of later changes to
 * the product. All of this logic lives here in the EJB (not in a backing
 * bean), as required.
 */
@Stateless
public class OrderEJB {

    @PersistenceContext(unitName = "assignment2PU")
    private EntityManager em;

    /**
     * Create an order for one customer and one product with the given
     * quantity. The product stock is decremented by the quantity, and the
     * unit price and total price are recorded from the product's current
     * price.
     */
    public Order createOrder(Long customerId, Long productId, Integer quantity) {
        Customer customer = em.find(Customer.class, customerId);
        Product product = em.find(Product.class, productId);

        Order order = new Order();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setQuantity(quantity);

        // Record the price snapshot and the computed total.
        double unitPrice = product.getPrice();
        order.setUnitPrice(unitPrice);
        order.setTotalPrice(unitPrice * quantity);
        order.setCreatedAt(new Date());

        // Stock management: reduce the product's stock by the ordered amount.
        product.setStockNumber(product.getStockNumber() - quantity);

        em.persist(order);
        em.merge(product);
        return order;
    }

    /**
     * Delete an order and restore the ordered quantity back to the product's
     * stock (the reverse of createOrder).
     */
    public void deleteOrder(Long orderId) {
        Order order = em.find(Order.class, orderId);
        if (order != null) {
            Product product = order.getProduct();
            if (product != null) {
                // Stock management: give the quantity back to the product.
                product.setStockNumber(product.getStockNumber() + order.getQuantity());
                em.merge(product);
            }
            em.remove(order);
        }
    }

    /** Return every order. */
    public List<Order> findAllOrders() {
        TypedQuery<Order> query = em.createNamedQuery("Orders.findAll", Order.class);
        return query.getResultList();
    }

    /** Look up a single order by its primary key (for "Search for an Order"). */
    public Order findOrderById(Long id) {
        TypedQuery<Order> query = em.createNamedQuery("Orders.findById", Order.class);
        query.setParameter("id", id);
        List<Order> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    /** Return all orders that belong to one customer. */
    public List<Order> findOrdersByCustomer(Long customerId) {
        TypedQuery<Order> query = em.createNamedQuery("Orders.findByCustomer", Order.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }
}
