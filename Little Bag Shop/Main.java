import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Bag> bags = new ArrayList<>();
    private static String filename = "bags.dat";

    public static void main(String[] args) {
        // Load existing users
        UserHandler.loadUsers();

        bags = FileHandler.loadBagsFromFile(filename);
        if (bags == null) {
            bags = new ArrayList<>();
        }

        Scanner scanner = new Scanner(System.in);
        User user = null;

        // Loop until the user enters the correct username and password
        while (user == null) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            user = UserHandler.authenticate(username, password);
            if (user == null) {
                System.out.println("Invalid username or password. Please try again.");
            }
        }

        // If we reach here, the user has entered the correct credentials
        if (user instanceof Manager) {
            Manager manager = (Manager) user;

            while (true) {
                System.out.println("1. View Bags");
                System.out.println("2. Add Bag");
                System.out.println("3. Search Bags");
                System.out.println("4. Create Cashier Account");
                System.out.println("5. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        manager.viewBags();
                        break;
                    case 2:
                        SwingUtilities.invokeLater(() -> new BagShopGUI(manager).setVisible(true));
                        break;
                    case 3:
                        System.out.println("Enter category to search:");
                        String searchCategory = scanner.nextLine();
                        manager.searchBags(searchCategory);
                        break;
                    case 4:
                        System.out.println("Enter new cashier username:");
                        String cashierUsername = scanner.nextLine();
                        System.out.println("Enter new cashier password:");
                        String cashierPassword = scanner.nextLine();
                        manager.createCashierAccount(cashierUsername, cashierPassword, bags);
                        break;
                    case 5:
                        FileHandler.saveBagsToFile(bags, filename);
                        System.out.println("Exiting...");
                        UserHandler.saveUsers();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } else {
            // Handle cashier or other user roles here
        }
    }
}
