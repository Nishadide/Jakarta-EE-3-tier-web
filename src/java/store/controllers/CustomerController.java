package store.controllers;

import java.util.List;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import store.ejb.CustomerEJB;
import store.entities.Customer;

/**
 * CustomerController is the JSF backing (managed) bean for the customer
 * screens. It holds the view state for creating, listing, searching and
 * viewing customers, and delegates all business logic to CustomerEJB.
 */
@Named
@RequestScoped
public class CustomerController {

    @EJB
    private CustomerEJB customerEJB;

    // Bound to the "Create a New Customer" form.
    private Customer customer = new Customer();

    // Bound to the search form and its results.
    private String searchName;
    private List<Customer> customerResults;

    // Used when a "View Details" link is clicked.
    private Long selectedId;
    private Customer selectedCustomer;

    // ---- Create -------------------------------------------------------

    /** Create a customer, then show the customer list with a confirmation. */
    public String doCreateCustomer() {
        String name = customer.getName();
        customerEJB.createCustomer(customer);
        addMessage("Successfully created the customer: " + name);
        customer = new Customer();
        return "listCustomers.xhtml";
    }

    // ---- List (re-queried on every render) -----------------------------

    public List<Customer> getCustomers() {
        return customerEJB.findAllCustomers();
    }

    // ---- Search (results shown on the same page) -----------------------

    public String doSearchCustomer() {
        customerResults = customerEJB.searchCustomerByName(searchName);
        return null;
    }

    // ---- View one customer's details (with orders) ---------------------

    public String doViewDetails() {
        selectedCustomer = customerEJB.findCustomerById(selectedId);
        return "customerDetails.xhtml";
    }

    // ---- Helper --------------------------------------------------------

    private void addMessage(String text) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text));
    }

    // ---- Getters & setters ---------------------------------------------

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public List<Customer> getCustomerResults() {
        return customerResults;
    }

    public Long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }
}
