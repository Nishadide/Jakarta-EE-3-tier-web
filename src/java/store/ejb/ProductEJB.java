package store.ejb;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import store.entities.Laptop;
import store.entities.Product;
import store.entities.SmartPhone;

/**
 * ProductEJB is the stateless session bean that holds all business logic for
 * products. The presentation tier calls these methods; the bean uses JPQL
 * named queries to operate on the entities. As an EJB it runs inside a
 * container-managed transaction, so persist operations are committed
 * automatically when the method returns.
 */
@Stateless
public class ProductEJB {

    @PersistenceContext(unitName = "assignment2PU")
    private EntityManager em;

    // ---- Laptops -------------------------------------------------------

    /** Persist a new laptop and return the managed instance. */
    public Laptop createLaptop(Laptop laptop) {
        em.persist(laptop);
        return laptop;
    }

    /** Return every laptop in stock. */
    public List<Laptop> findAllLaptops() {
        TypedQuery<Laptop> query = em.createNamedQuery("Laptop.findAll", Laptop.class);
        return query.getResultList();
    }

    /** Return the laptops whose model equals the given search term. */
    public List<Laptop> searchLaptopByModel(String model) {
        TypedQuery<Laptop> query = em.createNamedQuery("Laptop.findByModel", Laptop.class);
        query.setParameter("model", model);
        return query.getResultList();
    }

    // ---- Smart phones --------------------------------------------------

    /** Persist a new smart phone and return the managed instance. */
    public SmartPhone createSmartPhone(SmartPhone phone) {
        em.persist(phone);
        return phone;
    }

    /** Return every smart phone in stock. */
    public List<SmartPhone> findAllSmartPhones() {
        TypedQuery<SmartPhone> query = em.createNamedQuery("SmartPhone.findAll", SmartPhone.class);
        return query.getResultList();
    }

    /** Return the phones whose model equals the given search term. */
    public List<SmartPhone> searchSmartPhoneByModel(String model) {
        TypedQuery<SmartPhone> query = em.createNamedQuery("SmartPhone.findByModel", SmartPhone.class);
        query.setParameter("model", model);
        return query.getResultList();
    }

    // ---- Products (any type) -------------------------------------------

    /** Return every product (laptops and phones together). */
    public List<Product> findAllProducts() {
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
        return query.getResultList();
    }

    /** Look up a single product by its primary key. */
    public Product findProductById(Long id) {
        return em.find(Product.class, id);
    }

    /** Look up a single laptop by its primary key (for the details page). */
    public Laptop findLaptopById(Long id) {
        return em.find(Laptop.class, id);
    }

    /** Look up a single phone by its primary key (for the details page). */
    public SmartPhone findSmartPhoneById(Long id) {
        return em.find(SmartPhone.class, id);
    }
}
