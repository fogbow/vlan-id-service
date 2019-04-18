package cloud.fogbow.vlanid.core.model;

import cloud.fogbow.common.exceptions.UnexpectedException;
import cloud.fogbow.vlanid.core.datastore.DatabaseManager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "vlan_id")
public class VlanIdState implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    @Id
    private int id;

    @Column
    private boolean available;

    public VlanIdState() {
    }

    public VlanIdState(int id) {
        this.id = id;
        this.available = true;
    }

    public VlanIdState(int id, boolean available) {
        this(id);
        this.available = available;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public synchronized boolean isAvailable() {
        return this.available;
    }

    public synchronized void setAvailable(boolean available) throws UnexpectedException {
        this.available = available;
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.put(this);
    }

    public synchronized void setAvailableInTestMode(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VlanIdState)) return false;
        VlanIdState that = (VlanIdState) o;
        return id == that.id &&
                available == that.available;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, available);
    }

    @Override
    public String toString() {
        return "VLAN [id=" + this.id + ", available=" + this.available + "]";
    }
}
