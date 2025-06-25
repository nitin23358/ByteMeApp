package assign;

public class Review {
    private String customerName;
    private int rating; // e.g., out of 5
    private String comment;

    public Review(String customerName, int rating, String comment) {
        this.customerName = customerName;
        this.rating = rating;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Rating: " + rating + "/5, Comment: " + comment + " - by " + customerName;
    }
}

