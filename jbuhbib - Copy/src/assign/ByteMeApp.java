package assign;

import assign.Customer;
import assign.Item;
import assign.MenuManagement;
import assign.gui.MenuGui;

import javax.swing.*;
import java.util.*;

public class ByteMeApp {

    private static final String ADMIN_NAME = "Rahul";
    private static final String ADMIN_PASSWORD = "123@";
    static MenuManagement menuManagement = new MenuManagement();
    private static List<Item> menuItems = new ArrayList<>();
    private static OrderQueue orderQueue = new OrderQueue();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to Byte Me!");
            System.out.println("Are you an Admin or a Customer?");
            System.out.print("Enter 'Admin' or 'Customer' (or type 'Exit' to quit): ");
            String userType = scanner.nextLine();

            if (userType.equalsIgnoreCase("Admin")) {
                handleAdminLogin(scanner);
            } else if (userType.equalsIgnoreCase("Customer")) {
                showCustomerMenu(scanner);
            } else if (userType.equalsIgnoreCase("Exit")) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void handleAdminLogin(Scanner scanner) {
        System.out.print("Enter Admin Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (name.equals(ADMIN_NAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Welcome, Admin Rahul!");
            showAdminMenu(scanner);
        } else {
            System.out.println("Incorrect name or password. Access denied.");
        }
    }

    private static void showAdminMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Menu Management");
            System.out.println("2. Order Management");
            System.out.println("3. Report Generation");
            System.out.println("4. Logout");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    showMenuManagementOptions(scanner);
                    break;
                case 2:
                    showOrderManagementOptions(scanner, orderQueue);
                    break;
                case 3:
                    generateDailySalesReport();
                    break;
                case 4:
                    System.out.println("Logging out as Admin...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void generateDailySalesReport() {
        System.out.println("Generating Daily Sales Report...");
        List<Order> dailySales = orderQueue.getDailySales();
        double totalSales = 0;

        for (Order order : dailySales) {
            totalSales += order.getTotal();
            System.out.println(order);
        }

        System.out.println("Total Sales for the Day: $" + String.format("%.2f", totalSales));
    }

    private static void showMenuManagementOptions(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\nMenu Management:");
            System.out.println("1. Add new items");
            System.out.println("2. Update existing items");
            System.out.println("3. Remove items");
            System.out.println("4. Modify prices");
            System.out.println("5. Display menu items");
            System.out.println("6. Back to Admin Menu");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    menuManagement.addMenuItem(scanner);
                    break;
                case 2:
                    menuManagement.updateMenuItem(scanner);
                    break;
                case 3:
                    menuManagement.removeMenuItem(scanner);
                    break;
                case 4:
                    menuManagement.modifyPrice(scanner);
                    break;
                case 5:
                    SwingUtilities.invokeLater(() -> new MenuGui(menuManagement));
                    break;
                case 6:
                    running = false; // Go back to Admin Menu
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void showOrderManagementOptions(Scanner scanner, OrderQueue orderQueue) {
        boolean running = true;
        while (running) {
            System.out.println("\nOrder Management:");
            System.out.println("1. View pending orders");
            System.out.println("2. Update order status");
            System.out.println("3. Process refunds");
            System.out.println("4. Handle special requests");
            System.out.println("5. Back to Admin Menu");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    PendingOrdersGUI.showPendingOrdersGUI(new JFrame("Admin Menu"), orderQueue);
                    break;
                case 2:
                    updateOrderStatus(scanner, orderQueue);
                    break;
                case 3:
                    processRefund(scanner, orderQueue);
                    break;
                case 4:
                    handleSpecialRequests(orderQueue);
                    break;
                case 5:
                    running = false; // Go back to Admin Menu
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void updateOrderStatus(Scanner scanner, OrderQueue orderQueue) {
        System.out.print("Enter Order ID to update: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter new status (e.g., Preparing, Out for Delivery, Completed, Canceled): ");
        String newStatus = scanner.nextLine();

        if (orderQueue.updateOrderStatus(orderId, newStatus)) {
            System.out.println("Order status updated.");
        } else {
            System.out.println("Order not found or invalid status.");
        }
    }

    private static void processRefund(Scanner scanner, OrderQueue orderQueue) {
        System.out.print("Enter Order ID to process refund: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (orderQueue.processRefund(orderId)) {
            System.out.println("Refund processed for Order ID " + orderId);
        } else {
            System.out.println("Order not eligible for refund or not found.");
        }
    }

    private static void handleSpecialRequests(OrderQueue orderQueue) {
        System.out.println("Special Requests in Pending Orders:");
        for (Order order : orderQueue.getPendingOrders()) {
            if (!order.getSpecialRequest().equals("None")) {
                System.out.println("Order ID: " + order.getId() + " - Special Request: " + order.getSpecialRequest());
            }
        }
    }

    private static void showCustomerMenu(Scanner scanner) {
        System.out.println("\nWelcome to Customer Portal!");
        System.out.println("1. Login");
        System.out.println("2. Signup");
        System.out.println("3. Back to Main Menu");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Customer customer = null;

        switch (choice) {
            case 1:
                customer = login(scanner);
                break;
            case 2:
                customer = signUp(scanner);
                break;
            case 3:
                return; // Go back to Main Menu
            default:
                System.out.println("Invalid option. Returning to Main Menu.");
                return;
        }
        if (customer != null) {
            showCustomerMenu(customer, scanner);
        }
    }

    private static void showCustomerMenu(Customer customer, Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Browse Menu");
            System.out.println("2. Cart Operations");
            System.out.println("3. Order Tracking");
            System.out.println("4. Reviews");
            System.out.println("5. Become a VIP");
            System.out.println("6. Logout");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    showBrowseMenuOptions(scanner, customer);
                    break;
                case 2:
                    showCartOperationsOptions(scanner, customer);
                    break;
                case 3:
                    showOrderTrackingOptions(scanner, customer);
                    break;
                case 4:
                    showReviewOptions(scanner, customer);
                    break;
                case 5:
                    becomeVIP(scanner, customer);
                    break;
                case 6:
                    System.out.println("Logging out as Customer...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void becomeVIP(Scanner scanner, Customer customer) {
        System.out.println("Welcome, valued Customer!");
        System.out.print("Would you like to become a VIP? (yes/no): ");
        String wantsVIP = scanner.nextLine().trim().toLowerCase();

        if (wantsVIP.equals("yes")) {
            double payment = 0.0;

            // Prompt for payment amount
            System.out.print("To become a VIP, you need to pay a certain amount. Please enter the amount you are willing to pay: ");
            while (true) {
                try {
                    payment = Double.parseDouble(scanner.nextLine());
                    if (payment <= 0) {
                        System.out.println("Payment must be greater than 0. Please enter again: ");
                        continue;
                    }
                    break; // Valid payment entered
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number for payment: ");
                }
            }

            System.out.print("Please enter your total shopping amount: $");
            double totalShoppingAmount = 0.0;
            while (true) {
                try {
                    totalShoppingAmount = Double.parseDouble(scanner.nextLine());
                    if (totalShoppingAmount < 200.0) {
                        System.out.println("To qualify for VIP, you need to shop for at least $200. Please add more items to your cart to reach the minimum.");
                        return; // Exit if not eligible
                    }
                    break; // Valid total shopping amount entered
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number for total shopping amount: ");
                }
            }

            System.out.println("Thank you for becoming a VIP! We’re thrilled to have you as a valued member.");
            customer.setVipStatus(true); // Assuming you have a method to set VIP status in the Customer class
        } else {
            System.out.println("Thank you for choosing us! We hope you enjoy your shopping experience with us.");
        }
    }

    private static void showBrowseMenuOptions(Scanner scanner, Customer customer) {
        boolean browsing = true;
        while (browsing) {
            System.out.println("\nBrowse Menu:");
            System.out.println("1. View All Items (GUI)");
            System.out.println("2. Search by Item Name");
            System.out.println("3. Filter by Category");
            System.out.println("4. Sort by Price");
            System.out.println("5. Back to Customer Menu");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Launch the GUI to view all items
                    SwingUtilities.invokeLater(() -> new MenuGui(menuManagement));
                    break;
                case 2:
                    System.out.print("Enter item name to search: ");
                    String searchTerm = scanner.nextLine();
                    customer.searchItems(searchTerm);
                    break;
                case 3:
                    System.out.print("Enter category (healthy/drinks/chinese/other): ");
                    String category = scanner.nextLine();
                    customer.filterByCategory(category);
                    break;
                case 4:
                    customer.sortByPrice();
                    break;
                case 5:
                    browsing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showCartOperationsOptions(Scanner scanner, Customer customer) {
        boolean managingCart = true;
        while (managingCart) {
            System.out.println("\nCart Operations:");
            System.out.println("1. Add Item to Cart");
            System.out.println("2. Modify Item Quantity");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. View Total");
            System.out.println("5. Checkout");
            System.out.println("6. Back to Customer Menu");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addItemToCart(scanner, customer);
                    break;
                case 2:
                    modifyCartItemQuantity(scanner, customer);
                    break;
                case 3:
                    removeCartItem(scanner, customer);
                    break;
                case 4:
                    customer.viewTotal();
                    break;
                case 5:
                    System.out.print("Enter any special requests (or press enter to skip): ");
                    String specialRequest = scanner.nextLine();
                    customer.checkout(orderQueue, specialRequest);
                    break;
                case 6:
                    managingCart = false;
                    break;
                default:
                    System.out.println("Invalid option . Please try again.");
            }
        }
    }

    private static void addItemToCart(Scanner scanner, Customer customer) {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();

        int quantity = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter quantity: ");
            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter a valid number for quantity.");
                scanner.next();  // Clear the invalid input
            }
        }
        scanner.nextLine();  // Consume newline

        MenuItem item = menuManagement.findItemByName(itemName);
        if (item != null) {
            customer.addItemToCart(item, quantity);
            System.out.println("Item added to cart successfully!");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void modifyCartItemQuantity(Scanner scanner, Customer customer) {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter new quantity: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        MenuItem item = menuManagement.findItemByName(itemName);
        if (item != null) {
            customer.modifyItemQuantity(item, newQuantity);
            System.out.println("Item quantity updated successfully!");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void removeCartItem(Scanner scanner, Customer customer) {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();

        MenuItem item = menuManagement.findItemByName(itemName);
        if (item != null) {
            customer.removeItemFromCart(item);
            System.out.println("Item removed from cart successfully!");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void showOrderTrackingOptions(Scanner scanner, Customer customer) {
        boolean trackingOrders = true;
        while (trackingOrders) {
            System.out.println("\nOrder Tracking:");
            System.out.println("1. View Order Status");
            System.out.println("2. Cancel Order");
            System.out.println("3. View Order History");
            System.out.println("4. Back to Customer Menu");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Order ID: ");
                    int statusOrderId = scanner.nextInt();
                    customer.viewOrderStatus(statusOrderId);
                    break;
                case 2:
                    System.out.print("Enter Order ID: ");
                    int cancelOrderId = scanner.nextInt();
                    customer.cancelOrder(cancelOrderId);
                    break;
                case 3:
                    customer.viewOrderHistory();
                    break;
                case 4:
                    trackingOrders = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showReviewOptions(Scanner scanner, Customer customer) {
        boolean reviewing = true;
        while (reviewing) {
            System.out.println("\nReviews:");
            System.out.println("1. Provide Review");
            System.out.println("2. View Reviews");
            System.out.println("3. Back to Customer Menu");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    provideReview(scanner, customer);
                    break;
                case 2:
                    viewReviews(scanner, customer);
                    break;
                case 3:
                    reviewing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void provideReview(Scanner scanner, Customer customer) {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        MenuItem item = menuManagement.findItemByName(itemName);

        if (item != null) {
            System.out.print("Enter your name: ");
            String customerName = scanner.nextLine();

            int rating = 0;
            while (true) {
                System.out.print("Enter your rating (1-5): ");
                try {
                    rating = scanner.nextInt();
                    if (rating >= 1 && rating <= 5) {
                        break;
                    } else {
                        System.out.println("Please enter a rating between 1 and 5.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer between 1 and  5.");
                    scanner.next(); // Consume the invalid input
                }
            }
            scanner.nextLine(); // Consume newline left by nextInt()

            System.out.print("Enter your review: ");
            String reviewText = scanner.nextLine();

            customer.provideReview(item, customerName, rating, reviewText);
            System.out.println("Thank you for your review!");
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void viewReviews(Scanner scanner, Customer customer) {
        System.out.print("Enter item name to view reviews: ");
        String itemName = scanner.nextLine();
        MenuItem item = menuManagement.findItemByName(itemName);
        if (item != null) {
            customer.viewReviews(item);
        } else {
            System.out.println("Item not found.");
        }
    }




    private static Customer login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Validate credentials directly in the Customer class
        Customer customer = Customer.login(username, password);
        if (customer != null) {
            System.out.println("Login successful! Welcome back, " + username + "!");
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
        return customer;
    }

    private static Customer signUp(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Sign up directly in the Customer class
        Customer.signUp(username, password, menuManagement);
        return Customer.login(username, password); // Attempt to log in after sign-up
    }
}
