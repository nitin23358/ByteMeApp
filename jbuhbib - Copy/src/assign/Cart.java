package assign;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class CartItem {
    private MenuItem item;
    private int quantity;

    public CartItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return item.getPrice() * quantity;
    }
}

public class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    // Add an item to the cart with specified quantity
    public void addItem(MenuItem item, int quantity) {
        if (item.getQuantity() < quantity) {
            System.out.println("Cannot add " + item.getName() + " to cart. Insufficient stock.");
            return; // Prevent adding if not enough stock
        }

        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().equals(item)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(item, quantity));
    }
//    public void addItem(MenuItem item, int quantity) {
//        for (CartItem cartItem : cartItems) {
//            if (cartItem.getItem().equals(item)) {
//                cartItem.setQuantity(cartItem.getQuantity() + quantity);
//                return;
//            }
//        }
//        cartItems.add(new CartItem(item, quantity));
//    }

    // Modify the quantity of an existing item in the cart
    public void modifyQuantity(MenuItem item, int newQuantity) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().equals(item)) {
                cartItem.setQuantity(newQuantity);
                return;
            }
        }
        System.out.println("Item not found in cart.");
    }

    // Remove an item from the cart
    public void removeItem(MenuItem item) {
        CartItem itemToRemove = null;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().equals(item)) {
                itemToRemove = cartItem;
                break;
            }
        }
        if (itemToRemove != null) {
            cartItems.remove(itemToRemove);
        } else {
            System.out.println("Item not found in cart.");
        }
    }

    // Calculate the total price of items in the cart
    public double calculateTotal() {
        double total = 0.0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getTotalPrice();
        }
        return total;
    }

    // Check if the cart is empty
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    // Clear all items from the cart
    public void clear() {
        cartItems.clear();
    }

    // New method to get items in the cart
    public List<CartItem> getItems() {
        return cartItems; // Return the list of CartItem objects in the cart
    }
}








//package assign;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class CartItem {
//    private MenuItem item;
//    private int quantity;
//
//    public CartItem(MenuItem item, int quantity) {
//        this.item = item;
//        this.quantity = quantity;
//    }
//
//    public MenuItem getItem() {
//        return item;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public double getTotalPrice() {
//        return item.getPrice() * quantity;
//    }
//}
//
//public class Cart {
//    private List<CartItem> cartItems;
//
//    public Cart() {
//        this.cartItems = new ArrayList<>();
//    }
//
//    // Add an item to the cart with specified quantity
//    public void addItem(MenuItem item, int quantity) {
//        for (CartItem cartItem : cartItems) {
//            if (cartItem.getItem().equals(item)) {
//                cartItem.setQuantity(cartItem.getQuantity() + quantity);
//                return;
//            }
//        }
//        cartItems.add(new CartItem(item, quantity));
//    }
//
//    // Modify the quantity of an existing item in the cart
//    public void modifyQuantity(MenuItem item, int newQuantity) {
//        for (CartItem cartItem : cartItems) {
//            if (cartItem.getItem().equals(item)) {
//                cartItem.setQuantity(newQuantity);
//                return;
//            }
//        }
//        System.out.println("Item not found in cart.");
//    }
//
//    // Remove an item from the cart
//    public void removeItem(MenuItem item) {
//        CartItem itemToRemove = null;
//        for (CartItem cartItem : cartItems) {
//            if (cartItem.getItem().equals(item)) {
//                itemToRemove = cartItem;
//                break;
//            }
//        }
//        if (itemToRemove != null) {
//            cartItems.remove(itemToRemove);
//        } else {
//            System.out.println("Item not found in cart.");
//        }
//    }
//
//    // Calculate the total price of items in the cart
//    public double calculateTotal() {
//        double total = 0.0;
//        for (CartItem cartItem : cartItems) {
//            total += cartItem.getTotalPrice();
//        }
//        return total;
//    }
//
//    // Check if the cart is empty
//    public boolean isEmpty() {
//        return cartItems.isEmpty();
//    }
//
//    // Clear all items from the cart
//    public void clear() {
//        cartItems.clear();
//    }
//}
