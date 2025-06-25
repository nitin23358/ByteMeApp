package assign;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class OrderQueue {
    private PriorityQueue<Order> orderQueue; // Priority queue for orders
    private static final List<Order> dailySales = new ArrayList<>(); // Daily sales log

    public OrderQueue() {
        // Initialize priority queue to sort VIPs first, then by timestamp
        orderQueue = new PriorityQueue<>(Comparator.comparing((Order o) -> !o.isVip())
                .thenComparing(Order::getTimestamp));
    }

    public static synchronized void addToDailySales(Order order) {
        dailySales.add(order); // Record order in daily sales
    }

    public static List<Order> getDailySales() {
        return new ArrayList<>(dailySales); // Return copy to prevent external modifications
    }

    public void addOrder(Order order) {
        orderQueue.offer(order); // Add to queue
        addToDailySales(order); // Record in daily sales
    }

    public List<Order> getPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : orderQueue) {
            if ("Pending".equals(order.getStatus()) || "Processing".equals(order.getStatus())) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }

    public Order peekNextOrder() {
        return orderQueue.peek(); // View next order without removing
    }

    public Order getNextOrder() {
        return orderQueue.poll(); // Retrieve and remove the highest-priority order
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        List<Order> tempOrders = new ArrayList<>(orderQueue);

        for (Order order : tempOrders) {
            if (order.getId() == orderId) {
                order.setStatus(newStatus); // Update status
                orderQueue.clear();
                orderQueue.addAll(tempOrders); // Rebuild queue with updated order
                return true;
            }
        }
        System.out.println("Order ID not found for updating status.");
        return false;
    }

    public boolean processRefund(int orderId) {
        return orderQueue.removeIf(order -> order.getId() == orderId && "Canceled".equals(order.getStatus()));
    }

    public static synchronized void clearDailySales() {
        dailySales.clear(); // Clear daily sales data
    }

    public void generateDailySalesReport() {
        System.out.println("\n--- Daily Sales Report ---");
        double totalRevenue = 0;
        for (Order order : dailySales) {
            System.out.println(order);
            totalRevenue += order.getTotal();
        }
        System.out.println("Total Revenue: $" + totalRevenue);
        System.out.println("-------------------------");
    }

    public boolean processOrder(Customer customer) {
        if (customer.getCartItems().isEmpty()) {
            System.out.println("Cannot process order: Cart is empty.");
            return false; // Cannot process if cart is empty
        }

        // Check if all items in the cart are available
        for (CartItem cartItem : customer.getCartItems()) {
            MenuItem item = cartItem.getItem();
            if (item.getQuantity() < cartItem.getQuantity()) {
                System.out.println("Cannot process order: Not enough stock for " + item.getName());
                return false; // Not enough stock for one of the items
            }
        }

        // Create an order and add it to the queue
        Order order = new Order(String.valueOf(customer.getCustomerId()), customer.isVipStatus(), customer.getCart(), ""); // Assuming empty special request
        addOrder(order); // Add order to the queue
        System.out.println("Order processed successfully: " + order);
        return true; // Order processed successfully
    }
}















//package assign;
//
//import assign.Order;
//
//import java.util.PriorityQueue;
//import java.util.Comparator;
//import java.util.List;
//import java.util.ArrayList;
//
//public class OrderQueue {
//    private PriorityQueue<Order> orderQueue; // Priority queue for orders
//    private static final List<Order> dailySales = new ArrayList<>(); // Daily sales log
//
//    public OrderQueue() {
//        // Initialize priority queue to sort VIPs first, then by timestamp
//        orderQueue = new PriorityQueue<>(Comparator.comparing((Order o) -> !o.isVip())
//                .thenComparing(Order::getTimestamp));
//    }
//
//    public static synchronized void addToDailySales(Order order) {
//        dailySales.add(order); // Record order in daily sales
//    }
//
//    public static List<Order> getDailySales() {
//        return new ArrayList<>(dailySales); // Return copy to prevent external modifications
//    }
//
//    public void addOrder(Order order) {
//        orderQueue.offer(order); // Add to queue
//        addToDailySales(order); // Record in daily sales
//    }
//
//    public List<Order> getPendingOrders() {
//        List<Order> pendingOrders = new ArrayList<>();
//        for (Order order : orderQueue) {
//            if ("Pending".equals(order.getStatus()) || "Processing".equals(order.getStatus())) {
//                pendingOrders.add(order);
//            }
//        }
//        return pendingOrders;
//    }
//
//    public Order peekNextOrder() {
//        return orderQueue.peek(); // View next order without removing
//    }
//
//    public Order getNextOrder() {
//        return orderQueue.poll(); // Retrieve and remove the highest-priority order
//    }
//
//    public boolean updateOrderStatus(int orderId, String newStatus) {
//        List<Order> tempOrders = new ArrayList<>(orderQueue);
//
//        for (Order order : tempOrders) {
//            if (order.getId() == orderId) {
//                order.setStatus(newStatus); // Update status
//                orderQueue.clear();
//                orderQueue.addAll(tempOrders); // Rebuild queue with updated order
//                return true;
//            }
//        }
//        System.out.println("Order ID not found for updating status.");
//        return false;
//    }
//
//    public boolean processRefund(int orderId) {
//        return orderQueue.removeIf(order -> order.getId() == orderId && "Canceled".equals(order.getStatus()));
//    }
//
//    public static synchronized void clearDailySales() {
//        dailySales.clear(); // Clear daily sales data
//    }
//
//    public void generateDailySalesReport() {
//        System.out.println("\n--- Daily Sales Report ---");
//        double totalRevenue = 0;
//        for (Order order : dailySales) {
//            System.out.println(order);
//            totalRevenue += order.getTotal();
//        }
//        System.out.println("Total Revenue: $" + totalRevenue);
//        System.out.println("-------------------------");
//    }
//}
