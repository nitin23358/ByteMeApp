package assign;

import java.io.*;

/**
 * Utility class for generating unique Customer IDs and persisting them to a file.
 */
public class CustomerIdGenerator {
    private static final String ID_FILE = "customer_id.txt"; // File to store the last generated ID
    private static int nextCustomerId = 1; // Default starting ID

    // Static block to initialize nextCustomerId from the file
    static {
        File file = new File(ID_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                nextCustomerId = Integer.parseInt(br.readLine());
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error loading customer ID: " + e.getMessage());
            }
        }
    }

    /**
     * Generates the next unique customer ID and saves the updated value to the file.
     *
     * @return the next unique customer ID
     */
    public static synchronized int getNextCustomerId() {
        int id = nextCustomerId++;
        saveNextCustomerId();
        return id;
    }

    /**
     * Saves the next customer ID to the file to persist its state.
     */
    private static void saveNextCustomerId() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ID_FILE))) {
            bw.write(String.valueOf(nextCustomerId));
        } catch (IOException e) {
            System.err.println("Error saving customer ID: " + e.getMessage());
        }
    }
}
