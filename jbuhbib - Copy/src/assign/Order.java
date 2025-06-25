package assign;

import java.time.LocalDateTime;

public class Order {
    private static int orderCounter = 0; // Static counter for unique IDs
    private final int id; // Unique order ID
    private final String customerId; // Customer ID
    private final boolean isVip; // VIP status
    private String status; // Order status
    private final double total; // Total cost
    private final LocalDateTime timestamp; // Timestamp
    private final String specialRequest; // Special request

    public Order(String customerId, boolean isVip, Cart cart, String specialRequest) {
        synchronized (Order.class) {
            this.id = ++orderCounter;
        }
        this.customerId = customerId;
        this.isVip = isVip;
        this.status = isVip ? "Processing" : "Pending"; // Set initial status
        this.total = cart.calculateTotal(); // Calculate total from cart
        this.timestamp = LocalDateTime.now(); // Set timestamp
        this.specialRequest = (specialRequest == null || specialRequest.isEmpty()) ? "None" : specialRequest;
    }

    public int getId() { return id; }
    public String getCustomerId() { return customerId; }
    public boolean isVip() { return isVip; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotal() { return total; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getSpecialRequest() { return specialRequest; }

    @Override
    public String toString() {
        return "Order ID: " + id +
                "\nCustomer ID: " + customerId +
                "\nVIP: " + (isVip ? "Yes" : "No") +
                "\nTotal: $" + total +
                "\nStatus: " + status +
                "\nSpecial Request: " + specialRequest +
                "\nPlaced at: " + timestamp;
    }
}
