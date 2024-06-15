public class CustomerProduct {
    private Product product;
    private double expectedAmount;
    private double deliveredAmount;

    public CustomerProduct(Product product, double expectedAmount) {
        this.product = product;
        this.expectedAmount = expectedAmount;
        this.deliveredAmount = 0.0;
    }

    public Product getProduct() {
        return product;
    }

    public double getExpectedAmount() {
        return expectedAmount;
    }

    // Modify this method to add to the current expected amount
    public void addExpectedAmount(double amount) {
        this.expectedAmount += amount;
    }

    public double getDeliveredAmount() {
        return deliveredAmount;
    }

    public void setDeliveredAmount(double deliveredAmount) {
        this.deliveredAmount = deliveredAmount;
    }

    public double getRemainingBalance() {
        return expectedAmount - deliveredAmount;
    }

    public void deliverProduct(double amount) {
        this.deliveredAmount += amount;
    }
}
