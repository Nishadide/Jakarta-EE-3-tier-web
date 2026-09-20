package store.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

/**
 * SmartPhone is a sub class of Product. Under the Joined-Subclass strategy it
 * maps to a SMARTPHONE table that holds only the phone-specific columns and is
 * joined to the PRODUCT table on the shared primary key.
 *
 * Phone-specific properties: Cellular Connectivity (e.g. 4G or 5G), Location
 * (e.g. GPS) and SIM Card (e.g. Nano or Micro).
 */
@Entity
@NamedQueries({
    // Retrieve every phone (used by "The Stock of Phones").
    @NamedQuery(name = "SmartPhone.findAll",
                query = "SELECT s FROM SmartPhone s"),
    // Retrieve phones whose model matches the search term (used by search).
    @NamedQuery(name = "SmartPhone.findByModel",
                query = "SELECT s FROM SmartPhone s WHERE s.model = :model")
})
public class SmartPhone extends Product {

    private static final long serialVersionUID = 1L;

    // Phone-specific properties.
    private String cellularConnectivity;   // e.g. "4G" or "5G"
    private String location;               // e.g. "GPS"
    private String simCard;                // e.g. "Nano" or "Micro"

    public SmartPhone() {
    }

    public String getCellularConnectivity() {
        return cellularConnectivity;
    }

    public void setCellularConnectivity(String cellularConnectivity) {
        this.cellularConnectivity = cellularConnectivity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard;
    }
}
