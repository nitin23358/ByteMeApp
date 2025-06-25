package assign;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PendingOrdersGUI {

    public static void showPendingOrdersGUI(JFrame parentFrame, OrderQueue orderQueue) {
        // Create a new JFrame for the Pending Orders GUI
        JFrame pendingOrdersFrame = new JFrame("Pending Orders");
        pendingOrdersFrame.setSize(800, 500);
        pendingOrdersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pendingOrdersFrame.setLayout(new BorderLayout());

        // Create a table model and table to display the orders
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };

        tableModel.addColumn("Order ID");
        tableModel.addColumn("Customer ID");
        tableModel.addColumn("VIP");
        tableModel.addColumn("Total ($)");
        tableModel.addColumn("Status");
        tableModel.addColumn("Special Request");
        tableModel.addColumn("Timestamp");

        // Populate the table with pending orders
        List<Order> pendingOrders = orderQueue.getPendingOrders();
        if (pendingOrders.isEmpty()) {
            tableModel.addRow(new Object[]{"No orders available", "", "", "", "", "", ""});
        } else {
            for (Order order : pendingOrders) {
                tableModel.addRow(new Object[]{
                        order.getId(),
                        order.getCustomerId(),
                        order.isVip() ? "Yes" : "No",
                        String.format("%.2f", order.getTotal()),
                        order.getStatus(),
                        order.getSpecialRequest() != null ? order.getSpecialRequest() : "None",
                        order.getTimestamp()
                });
            }
        }

        JTable ordersTable = new JTable(tableModel);
        ordersTable.setFillsViewportHeight(true); // Makes sure the table fills the space
        ordersTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(ordersTable);

        // Back button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            pendingOrdersFrame.dispose(); // Close this frame
            parentFrame.setVisible(true); // Show the parent admin menu frame
        });
        buttonPanel.add(backButton);

        // Add components to the frame
        pendingOrdersFrame.add(new JLabel("Pending Orders", SwingConstants.CENTER), BorderLayout.NORTH);
        pendingOrdersFrame.add(scrollPane, BorderLayout.CENTER);
        pendingOrdersFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Center the frame on the screen
        pendingOrdersFrame.setLocationRelativeTo(null);

        // Display the frame
        pendingOrdersFrame.setVisible(true);
        parentFrame.setVisible(false); // Hide the parent admin menu frame
    }
}
