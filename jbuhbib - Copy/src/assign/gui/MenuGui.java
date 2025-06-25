package assign.gui;

import assign.MenuItem;
import assign.MenuManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MenuGui {
    private JFrame frame;
    private MenuManagement menuManagement;

    public MenuGui(MenuManagement menuManagement) {
        this.menuManagement = menuManagement;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Canteen Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panel for Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Back");

        // Action Listeners
        backButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(backButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        displayMenuItems();

        frame.setVisible(true);
    }

    // Display Menu Items in Table
    private void displayMenuItems() {
        JTable menuTable = new JTable();
        menuTable.setModel(new DefaultTableModel(new Object[]{"Item Name", "Quantity", "Price", "Category"}, 0));

        // Populate Table with Items from MenuManagement
        DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
        for (MenuItem item : menuManagement.getMenuItems()) {
            model.addRow(new Object[]{item.getName(), item.getQuantity(), item.getPrice(), item.getCategory()});
        }

        JScrollPane scrollPane = new JScrollPane(menuTable);
        frame.add(scrollPane, BorderLayout.CENTER);
    }
}
