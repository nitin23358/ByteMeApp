package assign;

import java.util.ArrayList;
import java.util.List;

class SpecialRequestManagement {
    private List<String> specialRequests;

    public SpecialRequestManagement() {
        specialRequests = new ArrayList<>();
        // Simulate a customer request
        specialRequests.add("Customer request: Add Veggie Burger to the menu");
    }

    public void handleSpecialRequests() {
        System.out.println("\nHandling Special Requests:");

        if (specialRequests.isEmpty()) {
            System.out.println("No special requests at this time.");
            return;
        }

        for (String request : specialRequests) {
            System.out.println(request + " - Response: 'We will bring this item soon.'");
        }

        // Clear requests after handling
        specialRequests.clear();
    }
}

// ReportGeneration class
class ReportGeneration {
    public void generateDailySalesReport() {
        System.out.println("\nGenerating Daily Sales Report...");
        // Hardcoded report data
        System.out.println("Total Sales: $200");
        System.out.println("Total Orders: 5");
        System.out.println("Refunds Processed: 2");
    }
}