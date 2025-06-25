package assign;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class OrderManagement {
    private List<String> pendingOrders;
    private List<String> completedOrders;
    private List<String> refunds;

    public OrderManagement() {
        pendingOrders = new ArrayList<>();
        completedOrders = new ArrayList<>();
        refunds = new ArrayList<>();
        // Simulating some data
        pendingOrders.add("Order #1: Burger");
        pendingOrders.add("Order #2: Pizza");
    }

    public void viewPendingOrders() {
        System.out.println("\nPending Orders:");
        for (String order : pendingOrders) {
            System.out.println(order);
        }
    }

    public void updateOrderStatus(Scanner scanner) {
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders to update.");
            return;
        }

        System.out.println("Select order to complete (1-" + pendingOrders.size() + "): ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (choice >= 0 && choice < pendingOrders.size()) {
            String completedOrder = pendingOrders.remove(choice);
            completedOrders.add(completedOrder);
            System.out.println("Order status updated successfully: " + completedOrder);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public void processRefunds() {
        System.out.println("\nProcessing Refunds:");
        refunds.add("Refund for Order #1");
        refunds.add("Refund for Order #2");

        for (String refund : refunds) {
            System.out.println(refund);
        }
    }
}