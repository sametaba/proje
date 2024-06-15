import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Foundry Factory Customer Balance Manager");

        boolean quit = false;
        while (!quit) {
            System.out.println("\nBir seçenek seçin:");
            System.out.println("1. Yeni bir sipariş oluşturun veya ekleyin");
            System.out.println("2. Teslimat Bildirin");
            System.out.println("3. Müşteri Bakiyelerini Görüntüleyin");
            System.out.println("4. Belirli Bir Müşterinin Bakiyesini Görüntüleyin");
            System.out.println("5. Yeni Bir Ürün Ekleyin");
            System.out.println("6. Yeni Bir Müşteri Ekleyin");
            System.out.println("7. Çıkış Yapın");

            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    OrderService.handleProductOrder();
                    break;
                case 2:
                    OrderService.recordProductDelivery();
                    break;
                case 3:
                    CustomerService.printAllBalances();
                    break;
                case 4:
                    CustomerService.printCustomerBalance();
                    break;
                case 5:
                    ProductService.addNewProduct();
                    break;
                case 6:
                    CustomerService.addNewCustomer();
                    break;
                case 7:
                    quit = true;
                    break;
                default:
                    System.out.println("Hatalı seçenek, lütfen yeniden deneyin.");
            }
        }
    }
}
