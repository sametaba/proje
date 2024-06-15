import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerService {
    private static final Scanner scanner = new Scanner(System.in);

    public static void addNewCustomer() {
        System.out.println("Yeni müşteri ID'si girin:");
        String customerId = scanner.nextLine();
        System.out.println("Yeni müşteri ismi girin:");
        String customerName = scanner.nextLine();
        System.out.println("Yeni müşteri email'i girin:");
        String customerEmail = scanner.nextLine();
        System.out.println("Varsa başlangıç bakiyesi girin, (eğer yoksa 0 girin):");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine(); // consume newline after double

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT count(*) FROM Customers WHERE customer_id = ?")) {

            checkStmt.setString(1, customerId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO Customers (customer_id, name, email, balance, debt) VALUES (?, ?, ?, ?, 0.00)")) {
                    insertStmt.setString(1, customerId);
                    insertStmt.setString(2, customerName);
                    insertStmt.setString(3, customerEmail);
                    insertStmt.setDouble(4, initialBalance);
                    int affectedRows = insertStmt.executeUpdate();

                    if (affectedRows > 0) {
                        conn.commit();
                        System.out.println("Yeni müşteri başarıyla eklendi.");
                    } else {
                        conn.rollback();
                        System.out.println("Müşteri eklenemedi.");
                    }
                }
            } else {
                System.out.println("Müşteri ID'si zaten mevcut!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Customer findCustomerById(String customerId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT customer_id, name FROM Customers WHERE customer_id = ?")) {
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Customer(rs.getString("customer_id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void listCustomers() {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT customer_id, name FROM Customers");
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Mevcut Müşterileri:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("customer_id") + ", İsim: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printAllBalances() {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT customer_id, name, balance, debt FROM Customers");
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Müşteri Bakiyeleri:");
            while (rs.next()) {
                System.out.printf("Customer ID: %s, Name: %s, Balance: %.2f, Debt: %.2f\n", rs.getString("customer_id"), rs.getString("name"), rs.getDouble("balance"), rs.getDouble("debt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void printCustomerBalance() {
        System.out.println("Müşteri ID'sini girin:");
        String customerId = scanner.nextLine();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT customer_id, name, balance, debt FROM Customers WHERE customer_id = ?")) {
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.printf("Customer ID: %s, Name: %s, Balance: %.2f, Debt: %.2f\n", rs.getString("customer_id"), rs.getString("name"), rs.getDouble("balance"), rs.getDouble("debt"));
            } else {
                System.out.println("Müşteri bulunamadı!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
