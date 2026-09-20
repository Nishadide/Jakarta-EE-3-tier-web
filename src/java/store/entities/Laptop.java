package store.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

/**
 * Laptop is a sub class of Product. Under the Joined-Subclass strategy it maps
 * to a LAPTOP table that holds only the laptop-specific columns and is joined
 * to the PRODUCT table on the shared primary key.
 *
 * Laptop-specific properties: Network Interface (e.g. 1000G Ethernet LAN),
 * Hard Drive and Ports.
 */
@Entity
@NamedQueries({
    // Retrieve every laptop (used by "The Stock of Laptops").
    @NamedQuery(name = "Laptop.findAll",
                query = "SELECT l FROM Laptop l"),
    // Retrieve laptops whose model matches the search term (used by search).
    @NamedQuery(name = "Laptop.findByModel",
                query = "SELECT l FROM Laptop l WHERE l.model = :model")
})
public class Laptop extends Product {

    private static final long serialVersionUID = 1L;

    // Laptop-specific properties.
    private String networkInterface;   // e.g. "1000G Ethernet LAN"
    private String hardDrive;          // e.g. "750G"
    private String ports;              // e.g. "HDMIx1 USBCx1 USBAx3"

    public Laptop() {
    }

    public String getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(String networkInterface) {
        this.networkInterface = networkInterface;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }
}
