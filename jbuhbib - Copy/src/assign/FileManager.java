


package assign;

import java.io.*;
import java.util.List;

public class FileManager {
    private static final String CART_FILE = "cart.txt";
    private static final String ORDER_HISTORY_FILE = "order_history.txt";

    // Save cart data to a file
    public static void saveCartData(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE))) {
            for (CartItem cartItem : customer.getCart().getItems()) { // Get CartItem instead of MenuItem
                writer.write(cartItem.getItem().getName() + "," + cartItem.getQuantity()); // Use CartItem to get MenuItem and quantity
                writer.newLine();
            }
            System.out.println("Cart data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving cart data: " + e.getMessage());
        }
    }

    // Load cart data from a file
    public static void loadCartData(Customer customer, MenuManagement menuManagement) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String itemName = parts[0];
                int quantity = Integer.parseInt(parts[1]);
                MenuItem item = menuManagement.findItemByName(itemName); // Use the instance of MenuManagement
                if (item != null) {
                    customer.addItemToCart(item, quantity);
                }
            }
            System.out.println("Cart data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading cart data: " + e.getMessage());
        }
    }

    // Save order history
    public static void saveOrderHistory(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_HISTORY_FILE, true))) {
            for (Order order : customer.getOrderHistory()) {
                writer.write(order.toString()); // Assuming Order class has a meaningful toString() method
                writer.newLine();
            }
            System.out.println("Order history saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving order history: " + e.getMessage());
        }
    }
}