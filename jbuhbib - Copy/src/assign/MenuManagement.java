package assign;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MenuManagement {
    private List<MenuItem> menuItems;

    public void additem(MenuItem item) {
        menuItems.add(item); // Add the MenuItem directly
    }

    public MenuManagement() {
        menuItems = new ArrayList<>();
        addDefaultItems();
    }

    private void addDefaultItems() {
        menuItems.add(new MenuItem("Chips", 50, 1.50, "Snack"));
        menuItems.add(new MenuItem("Biscuit", 40, 2.00, "Snack"));
        menuItems.add(new MenuItem("Coldrink", 30, 1.00, "Beverage"));
        menuItems.add(new MenuItem("Coffee", 20, 2.50, "Beverage"));
        menuItems.add(new MenuItem("Ice Cream", 25, 3.00, "Dessert"));
        menuItems.add(new MenuItem("Pizza", 15, 8.00, "Main Course"));
        menuItems.add(new MenuItem("Burger", 10, 5.00, "Main Course"));
        menuItems.add(new MenuItem("Apple", 5, 4.00, "Fruit"));
        menuItems.add(new MenuItem("Oranges", 12, 7.00, "Fruit"));
    }

    public void addMenuItem(Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        System.out.print("Enter item quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter item category: ");
        String category = scanner.nextLine();

        menuItems.add(new MenuItem(name, quantity, price, category));
        System.out.println("Item added successfully.");
    }
    public void additem(Item item){
        menuItems.add(new MenuItem(item.getName(),0,item.getPrice(),item.getCategory()));
    }

    public void updateMenuItem(Scanner scanner) {
        System.out.print("Enter the name of the item to update: ");
        String name = scanner.nextLine();
        MenuItem itemToUpdate = null;

        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                itemToUpdate = item;
                break;
            }
        }

        if (itemToUpdate != null) {
            System.out.print("Enter new quantity: ");
            int newQuantity = scanner.nextInt();

            System.out.print("Enter new price: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();  // Consume newline

            itemToUpdate.setQuantity(newQuantity);
            itemToUpdate.setPrice(newPrice);
            System.out.println("Item updated successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void removeMenuItem(Scanner scanner) {
        System.out.print("Enter the name of the item to remove: ");
        String name = scanner.nextLine();
        MenuItem itemToRemove = null;

        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null) {
            menuItems.remove(itemToRemove);
            System.out.println("Item removed successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void modifyPrice(Scanner scanner) {
        System.out.print("Enter the name of the item to modify price: ");
        String name = scanner.nextLine();
        MenuItem itemToModify = null;
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                itemToModify = item;
                break;
            }
        }

        if (itemToModify != null) {
            System.out.print("Enter new price: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();  // Consume newline

            itemToModify.setPrice(newPrice);
            System.out.println("Price modified successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void displayMenuItems() {
        System.out.println("\nCurrent Menu Items:");
        for (MenuItem item : menuItems) {
            System.out.println(item);
        }
    }

    public MenuItem findItemByName(String itemName) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;  // Return the item if the name matches
            }
        }
        return null;  // Return null if the item is not found
    }

    public List<MenuItem> searchItems(String searchTerm) {
        List<MenuItem> foundItems = new ArrayList<>();
        for (MenuItem item : menuItems) {
            if (item.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    public List<MenuItem> filterByCategory(String category) {
        List<MenuItem> filteredItems = new ArrayList<>();
        for (MenuItem item : menuItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public void sortByPrice() {
        Collections.sort(menuItems, new Comparator<MenuItem>() {
            @Override
            public int compare(MenuItem item1, MenuItem item2) {
                return Double.compare(item1.getPrice(), item2.getPrice());
            }
        });
        System.out.println("Menu items sorted by price.");
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}