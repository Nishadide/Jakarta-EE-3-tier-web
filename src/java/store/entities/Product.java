package store.entities;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

/**
 * Product is the super class of the product hierarchy. It abstracts the
 * properties that are common to every product the company sells (laptops and
 * smart phones): brand, model, display size, weight, operating system, camera,
 * Wi-Fi, price, description and the number of units in stock.
 *
 * The assignment requires the Joined-Subclass inheritance strategy, so this
 * class is annotated with @Inheritance(strategy = InheritanceType.JOINED).
 * With this strategy the super class maps to its own PRODUCT table and each
 * sub class (Laptop, SmartPhone) maps to a separate table that is joined to
 * PRODUCT on the shared primary key.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    // Retrieve every product regardless of its concrete type.
    @NamedQuery(name = "Product.findAll",
                query = "SELECT p FROM Product p"),
    // Retrieve a single product by its primary key.
    @NamedQuery(name = "Product.findById",
                query = "SELECT p FROM Product p WHERE p.id = :id")
})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    // Primary key, generated automatically by the persistence provider.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Common properties shared by all products.
    private String brand;
    private String model;
    private String displaySize;        // e.g. "17.3"
    private Integer weight;            // weight in grams, e.g. 2500
    private String operatingSystem;
    private String camera;
    private String wifi;
    private Double price;              // unit price, e.g. 1819.0
    @Column(length = 2000)
    private String description;
    private Integer stockNumber;       // number of units currently in stock

    // Default constructor required by JPA.
    public Product() {
    }

    // Getters and setters.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(String displaySize) {
        this.displaySize = displaySize;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        return (this.id != null || other.id == null)
                && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "store.entities.Product[ id=" + id + " ]";
    }
}
