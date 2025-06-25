package assign;

import java.util.*;

public class Customer {
    private static final long serialVersionUID = 1L;
    private String name;

    private Cart cart;
    private List<Order> orderHistory;
    private MenuManagement menuManagement;
    private int customerId;
    private boolean isVip;
    private double totalSpent;
    private double vipPayment;
    private static int nextCustomerId = 1000;

    private static final double MIN_SHOPPING_AMOUNT = 200.0;
    private static final double MIN_VIP_PAYMENT = 50.0;

    private static Map<String, List<Review>> itemReviews = new HashMap<>();
    private static Map<String, Customer> customersDatabase = new HashMap<>();



    private String username;
    private String password;

    public Customer(String username, String password, MenuManagement menuManagement) {
        this.username = username;
        this.password = password;
        this.customerId = getNextCustomerId();
        this.cart = new Cart();
        this.menuManagement = menuManagement;
        this.orderHistory = new ArrayList<>();
        this.isVip = false;
        this.totalSpent = 0.0;
        this.vipPayment = 0.0;
    }
    public Cart getCart() {
        return cart; // Return the Cart instance
    }
    static{
        customersDatabase.put("nitin", new Customer("nitin",  "1", new MenuManagement()));
    customersDatabase.put("bihari", new Customer("bihari",  "2", new MenuManagement()));
    customersDatabase.put("lassi", new Customer("lassi", "3", new MenuManagement()));
    }


    private synchronized int getNextCustomerId() {
        return nextCustomerId++;
    }

    public static Customer login(String username, String password) {
        if (customersDatabase.containsKey(username)) {
            Customer customer = customersDatabase.get(username);
            if (customer.password.equals(password)) {
                return customer;  // Successful login
            }
        }
        System.out.println("Login failed. Invalid username or password.");
        return null;  // Login failed
    }

    public static void signUp(String username, String password, MenuManagement menuManagement) {
        if (customersDatabase.containsKey(username)) {
            System.out.println("Username already taken. Please choose a different username.");
            return;
        }
        Customer newCustomer = new Customer(username, password, menuManagement);
        customersDatabase.put(username, newCustomer);
        System.out.println("Sign-up successful! Welcome, " + username + "!");
    }

    // Getter methods for Customer details
    public int getCustomerId() {
        return customerId;
    }

    public boolean isVipStatus() {
        return isVip;
    }

    public void setVipStatus(boolean vipStatus) {
        this.isVip = vipStatus;
    }

    public boolean attemptVipUpgrade(double payment, double shoppingAmount) {
        if (payment >= MIN_VIP_PAYMENT && shoppingAmount >= MIN_SHOPPING_AMOUNT) {
            this.isVip = true;
            this.vipPayment = payment;
            this.totalSpent += shoppingAmount;
            return true;
        }
        return false;
    }

    public void updateTotalSpent(double amount) {
        this.totalSpent += amount;
        if (!isVip && this.totalSpent >= MIN_SHOPPING_AMOUNT) {
            this.isVip = true;
        }
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public double getVipPayment() {
        return vipPayment;
    }

    // **Browse Menu Functions**

    public void viewAllItems() {
        System.out.println("All Available Items:");
        if (menuManagement == null || menuManagement.getMenuItems() == null) {
            System.out.println("Menu is not available.");
            return;
        }
        for (MenuItem item : menuManagement.getMenuItems()) {
            System.out.println(item);
        }
    }

    public void searchItems(String searchTerm) {
        System.out.println("Search Results for \"" + searchTerm + "\":");
        if (menuManagement == null || menuManagement.getMenuItems() == null) {
            System.out.println("Menu is not available.");
            return;
        }
        for (MenuItem item : menuManagement.getMenuItems()) {
            if (item.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                System.out.println(item);
            }
        }
    }

    public void filterByCategory(String category) {
        System.out.println("Items in Category: " + category);

        List<String> healthyItems = Arrays.asList("apple", "orange");
        List<String> drinksItems = Arrays.asList("coffee", "coldrink");
        List<String> chineseItems = Arrays.asList("pizza", "burger");

        if (menuManagement == null || menuManagement.getMenuItems() == null) {
            System.out.println("Menu is not available.");
            return;
        }

        List<MenuItem> filteredItems = new ArrayList<>();
        for (MenuItem item : menuManagement .getMenuItems()) {
            String itemName = item.getName().toLowerCase();
            switch (category.toLowerCase()) {
                case "healthy":
                    if (healthyItems.contains(itemName)) filteredItems.add(item);
                    break;
                case "drinks":
                    if (drinksItems.contains(itemName)) filteredItems.add(item);
                    break;
                case "chinese":
                    if (chineseItems.contains(itemName)) filteredItems.add(item);
                    break;
                case "other":
                    if (!healthyItems.contains(itemName) && !drinksItems.contains(itemName) && !chineseItems.contains(itemName)) {
                        filteredItems.add(item);
                    }
                    break;
                default:
                    System.out.println("Invalid category. Please choose healthy, drinks, chinese, or other.");
                    return;
            }
        }

        if (filteredItems.isEmpty()) {
            System.out.println("No items found in this category.");
        } else {
            for (MenuItem item : filteredItems) {
                System.out.println(item);
            }
        }
    }

    public void sortByPrice() {
        if (menuManagement == null || menuManagement.getMenuItems() == null) {
            System.out.println("Menu is not available.");
            return;
        }
        menuManagement.getMenuItems().sort(Comparator.comparingDouble(MenuItem::getPrice));
        System.out.println("Menu sorted by price:");
        menuManagement.displayMenuItems();
    }

    // **Cart Operations**

    public void addItemToCart(MenuItem item, int quantity) {
        cart.addItem(item, quantity);
        System.out.println("Added " + item.getName() + " (x" + quantity + ") to cart.");
        FileManager.saveCartData(this); // Save cart data after adding
    }

    public void modifyItemQuantity(MenuItem item, int newQuantity) {
        cart.modifyQuantity(item, newQuantity);
        System.out.println("Updated quantity of " + item.getName() + " to " + newQuantity + ".");
        FileManager.saveCartData(this); // Save cart data after modifying
    }

    public void removeItemFromCart(MenuItem item) {
        cart.removeItem(item);
        System.out.println("Removed " + item.getName() + " from cart.");
        FileManager.saveCartData(this); // Save cart data after removing
    }

    public void viewTotal() {
        double total = cart.calculateTotal();
        System.out.println("Total cost: $" + total);
    }
//    public void checkout(OrderQueue orderQueue, String specialRequest) {
//        if (cart.isEmpty()) {
//            System.out.println("Cart is empty. Add items to checkout.");
//            return;
//        }
//
//        double total = cart.calculateTotal();
//        if (isVip) {
//            double discount = total * 0.1;
//            total -= discount;
//            System.out.println("VIP Discount applied: -$" + String.format("%.2f", discount));
//        }
//
//        Order order = new Order(String.valueOf(customerId), isVip, cart, specialRequest);
//        orderQueue.addOrder(order);
//        orderHistory.add(order);
//
//        // Save updated customer data
//        HashMap<String, Customer> customers = FileManager.loadCustomerData();
//        customers.put(this.username, this); // Update the current customer data
//        FileManager.saveCustomerData(customers);
//
//        System.out.println("Checkout successful!");
//        System.out.println("Order ID: " + order.getId());
//        System.out.println("Final Total: $" + String.format("%.2f", total));
//        System.out.println("VIP Status: " + (isVip ? "Yes" : "No"));
//
//        cart.clear();
//    }


    public void checkout(OrderQueue orderQueue, String specialRequest) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Add items to checkout.");
            return;
        }

        double total = cart.calculateTotal();
        if (isVip) {
            double discount = total * 0.1;
            total -= discount;
            System.out.println("VIP Discount applied: -$" + String.format("%.2f", discount));
        }

        Order order = new Order(String.valueOf(customerId), isVip, cart, specialRequest);
        orderQueue.addOrder(order);
        orderHistory.add(order);

        FileManager.saveOrderHistory(this); // Save order history after checkout
        System.out.println("Checkout successful!");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Final Total: $" + String.format("%.2f", total));
        System.out.println("VIP Status: " + (isVip ? "Yes" : "No"));

        cart.clear();
    }

    public String getVipStatus() {
        return isVip ? "VIP Customer (ID: " + customerId + ")" : "Regular Customer (ID: " + customerId + ")";
    }

    public double calculateVipDiscount(double amount) {
        return isVip ? amount * 0.1 : 0.0;
    }

    // **Order Tracking**

    public void viewOrderStatus(int orderId) {
        for (Order order : orderHistory) {
            if (order.getId() == orderId) {
                System.out.println("Order ID: " + orderId + " - Status: " + order.getStatus());
                return;
            }
        }
        System.out.println("Order ID not found.");
    }

    public void cancelOrder(int orderId) {
        for (Order order : orderHistory) {
            if (order.getId() == orderId && order.getStatus().equals("Processing")) {
                order.setStatus("Cancelled");
                System.out.println("Order ID: " + orderId + " has been cancelled.");
                return;
            }
        }
        System.out.println("Order cannot be cancelled or not found.");
    }

    public void viewOrderHistory() {
        System.out.println("Order History:");
        for (Order order : orderHistory) {
            System.out.println(order);
        }
    }

    // **Reviews**

    public void addReview(MenuItem item, String customerName, int rating, String reviewText) {
        String itemName = item.getName();
        Review newReview = new Review(customerName, rating, reviewText);
        itemReviews.putIfAbsent(itemName, new ArrayList<>());
        itemReviews.get(itemName).add(newReview);
        System.out.println("Review added for " + itemName + ".");
    }

    public void provideReview(MenuItem item, String customerName, int rating, String comment) {
        Review review = new Review(customerName, rating, comment);
        item.addReview(review);
        System.out.println("Review added successfully.");
    }

    public void viewReviews(MenuItem item) {
        System.out.println("Reviews for " + item.getName() + ":");
        if (item.getReviews().isEmpty()) {
            System.out.println("No reviews available.");
        } else {
            for (Review review : item.getReviews()) {
                System.out.println(review);
            }
        }
    }
    public List<CartItem> getCartItems() {
        return cart.getItems(); // Assuming you have a method in Cart that returns the list of items
    }

    public List<Order> getOrderHistory() {
        return orderHistory; // This method already exists, just ensure it's public
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password.toString();
    }
}
