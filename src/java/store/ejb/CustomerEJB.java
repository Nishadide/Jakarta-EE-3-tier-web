package store.ejb;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import store.entities.Customer;

/**
 * CustomerEJB is the stateless session bean that holds all business logic for
 * customers. It uses JPQL named queries to create, list, search and retrieve
 * customers.
 */
@Stateless
public class CustomerEJB {

    @PersistenceContext(unitName = "assignment2PU")
    private EntityManager em;

    /** Persist a new customer and return the managed instance. */
    public Customer createCustomer(Customer customer) {
        em.persist(customer);
        return customer;
    }

    /** Return every customer. */
    public List<Customer> findAllCustomers() {
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll", Customer.class);
        return query.getResultList();
    }

    /** Return the customers whose name equals the given search term. */
    public List<Customer> searchCustomerByName(String name) {
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findByName", Customer.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    /** Look up a single customer by its primary key. */
    public Customer findCustomerById(Long id) {
        return em.find(Customer.class, id);
    }
}
