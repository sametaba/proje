import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String id;
    private String name;
    private List<CustomerProduct> products;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.products = new ArrayList<>();
    }

    public void addProduct(CustomerProduct product) {
        this.products.add(product);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CustomerProduct> getProducts() {
        return products;
    }

    public void printBalances() {
        System.out.println("Customer: " + name);
        for (CustomerProduct cp : products) {
            System.out.println("  Product: " + cp.getProduct().getName() +
                    ", Ordered: " + cp.getExpectedAmount() +
                    ", Delivered: " + cp.getDeliveredAmount() +
                    ", Remaining: " + cp.getRemainingBalance());
        }
    }
}
