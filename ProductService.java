import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductService {
    private static final Scanner scanner = new Scanner(System.in);

    public static void addNewProduct() {
        System.out.println("Yeni ürün ID'si girin:");
        String productId = scanner.nextLine();
        System.out.println("Yeni ürün ismi girin:");
        String productName = scanner.nextLine();
        System.out.println("Yeni ürün fiyatı girin:");
        double productPrice = scanner.nextDouble();
        scanner.nextLine(); // consume newline after double

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT count(*) FROM Products WHERE product_id = ?")) {

            checkStmt.setString(1, productId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO Products (product_id, name, price) VALUES (?, ?, ?)")) {
                    insertStmt.setString(1, productId);
                    insertStmt.setString(2, productName);
                    insertStmt.setDouble(3, productPrice);
                    int affectedRows = insertStmt.executeUpdate();

                    if (affectedRows > 0) {
                        conn.commit();
                        System.out.println("Ürün başarıyla eklendi.");
                    } else {
                        conn.rollback();
                        System.out.println("Ürün eklenemedi.");
                    }
                }
            } else {
                System.out.println("Ürün ID'si zaten bulunuyor!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Product findProductById(String productId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT product_id, name, price FROM Products WHERE product_id = ?")) {
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Product(rs.getString("product_id"), rs.getString("name"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void listProducts() {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT product_id, name, price FROM Products");
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Mevcut Ürünler:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("product_id") + ", İsim: " + rs.getString("name") + ", Fiyat: " + rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
