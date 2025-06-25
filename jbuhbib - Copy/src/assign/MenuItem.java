package assign;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private String name;
    private int quantity;
    private double price;
    private List<Review> reviews = new ArrayList<>();
    private String category; // Added category field

    public MenuItem(String name, int quantity, double price, String category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category; // Initialize category
    }


    public void addReview(Review review) {
        reviews.add(review);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category; // Getter for category
    }

    @Override
    public String toString() {
        return name + " - Qty: " + quantity + ", Price: $" + price + ", Category: " + category;
    }
} 