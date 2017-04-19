import java.io.Serializable;

/**
 * Created by jayesh on 4/18/17.
 */
public class Customer implements Serializable {
    private int id;
    private String name;

    Customer() {
        this.id = 0;
        this.name = "";
    }

    Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name;}

    @Override
    public String toString() {
        return "Customer: " + name + " Id: " + id;
    }
}
