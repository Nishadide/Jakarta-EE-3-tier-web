package store.controllers;

import java.util.List;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import store.ejb.CustomerEJB;
import store.ejb.OrderEJB;
import store.ejb.ProductEJB;
import store.entities.Customer;
import store.entities.Order;
import store.entities.Product;

/**
 * OrderController is the JSF backing (managed) bean for the order screens. It
 * supplies the customer and product drop-downs, and delegates all business
 * logic (including stock management) to OrderEJB.
 */
@Named
@RequestScoped
public class OrderController {

    @EJB
    private OrderEJB orderEJB;
    @EJB
    private CustomerEJB customerEJB;
    @EJB
    private ProductEJB productEJB;

    // Bound to the "Create a New Order" form.
    private Long selectedCustomerId;
    private Long selectedProductId;
    private Integer quantity;

    // Bound to the search form and its result.
    private Long searchOrderId;
    private Order orderResult;

    // Used by the Delete link.
    private Long selectedId;

    // ---- Drop-down data ------------------------------------------------

    public List<Customer> getCustomers() {
        return customerEJB.findAllCustomers();
    }

    public List<Product> getProducts() {
        return productEJB.findAllProducts();
    }

    // ---- Create -------------------------------------------------------

    /** Create an order (which reduces product stock), then show the list. */
    public String doCreateOrder() {
        orderEJB.createOrder(selectedCustomerId, selectedProductId, quantity);
        Customer customer = customerEJB.findCustomerById(selectedCustomerId);
        String name = (customer != null) ? customer.getName() : "";
        addMessage("Successfully created the order for " + name);
        return "listOrders.xhtml";
    }

    // ---- List (re-queried on every render) -----------------------------

    public List<Order> getOrders() {
        return orderEJB.findAllOrders();
    }

    // ---- Delete (which restores product stock) -------------------------

    public String doDeleteOrder() {
        orderEJB.deleteOrder(selectedId);
        return "listOrders.xhtml";
    }

    // ---- Search (result shown on the same page) ------------------------

    public String doSearchOrder() {
        orderResult = orderEJB.findOrderById(searchOrderId);
        return null;
    }

    // ---- Helper --------------------------------------------------------

    private void addMessage(String text) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text));
    }

    // ---- Getters & setters ---------------------------------------------

    public Long getSelectedCustomerId() {
        return selectedCustomerId;
    }

    public void setSelectedCustomerId(Long selectedCustomerId) {
        this.selectedCustomerId = selectedCustomerId;
    }

    public Long getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(Long selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getSearchOrderId() {
        return searchOrderId;
    }

    public void setSearchOrderId(Long searchOrderId) {
        this.searchOrderId = searchOrderId;
    }

    public Order getOrderResult() {
        return orderResult;
    }

    public Long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
    }
}
