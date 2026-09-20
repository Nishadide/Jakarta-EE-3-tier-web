package store.controllers;

import java.util.List;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import store.ejb.ProductEJB;
import store.entities.Laptop;
import store.entities.Product;
import store.entities.SmartPhone;

/**
 * ProductController is the JSF backing (managed) bean for the product screens.
 * It holds the view state for creating, listing, searching and viewing
 * laptops and smart phones, and delegates all business logic to ProductEJB.
 */
@Named
@RequestScoped
public class ProductController {

    @EJB
    private ProductEJB productEJB;

    // Bound to the "Create a New Laptop" / "Create a New Phone" forms.
    private Laptop laptop = new Laptop();
    private SmartPhone phone = new SmartPhone();

    // Bound to the search forms and their results.
    private String searchModel;
    private List<Laptop> laptopResults;
    private List<SmartPhone> phoneResults;

    // Used when a Model link is clicked to view one product's details.
    private Long selectedId;
    private Laptop selectedLaptop;
    private SmartPhone selectedPhone;

    // ---- Create -------------------------------------------------------

    /** Create a laptop, then show the stock of laptops with a confirmation. */
    public String doCreateLaptop() {
        String model = laptop.getModel();
        productEJB.createLaptop(laptop);
        addMessage("Successfully created the laptop: " + model);
        laptop = new Laptop();
        return "listLaptops.xhtml";
    }

    /** Create a phone, then show the stock of phones with a confirmation. */
    public String doCreatePhone() {
        String model = phone.getModel();
        productEJB.createSmartPhone(phone);
        addMessage("Successfully created the Phone: " + model);
        phone = new SmartPhone();
        return "listPhones.xhtml";
    }

    // ---- Lists (re-queried on every render) ----------------------------

    public List<Laptop> getLaptops() {
        return productEJB.findAllLaptops();
    }

    public List<SmartPhone> getPhones() {
        return productEJB.findAllSmartPhones();
    }

    // ---- Search (results shown on the same page) -----------------------

    public String doSearchLaptop() {
        laptopResults = productEJB.searchLaptopByModel(searchModel);
        return null; // stay on the search page and render the results
    }

    public String doSearchPhone() {
        phoneResults = productEJB.searchSmartPhoneByModel(searchModel);
        return null;
    }

    // ---- View one product's details ------------------------------------

    /**
     * Load the selected product and navigate to the matching details page.
     * With the Joined-Subclass strategy the persistence provider returns the
     * concrete subclass, so an instanceof check picks the correct page.
     */
    public String doViewProduct() {
        Product p = productEJB.findProductById(selectedId);
        if (p instanceof Laptop) {
            selectedLaptop = (Laptop) p;
            return "laptopDetails.xhtml";
        } else if (p instanceof SmartPhone) {
            selectedPhone = (SmartPhone) p;
            return "phoneDetails.xhtml";
        }
        return null;
    }

    // ---- Helper --------------------------------------------------------

    private void addMessage(String text) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text));
    }

    // ---- Getters & setters ---------------------------------------------

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public SmartPhone getPhone() {
        return phone;
    }

    public void setPhone(SmartPhone phone) {
        this.phone = phone;
    }

    public String getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(String searchModel) {
        this.searchModel = searchModel;
    }

    public List<Laptop> getLaptopResults() {
        return laptopResults;
    }

    public List<SmartPhone> getPhoneResults() {
        return phoneResults;
    }

    public Long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
    }

    public Laptop getSelectedLaptop() {
        return selectedLaptop;
    }

    public SmartPhone getSelectedPhone() {
        return selectedPhone;
    }
}
