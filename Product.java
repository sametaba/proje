public class Product {
    private String id;
    private String name;
    private double price; // Fiyat bilgisini de saklamak için yeni bir alan ekleniyor.

    public Product(String id, String name, double price) { // Constructor güncelleniyor.
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() { // Fiyatı döndüren yeni bir getter metod.
        return price;
    }
}
