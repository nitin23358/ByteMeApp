//package assign;
//import assign.Order;
//import assign.OrderQueue;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.util.List;
//
//public class OrderGUI {
//    private JFrame frame;
//    private OrderQueue orderQueue;
//
//    public OrderGUI(OrderQueue orderQueue) {
//        this.orderQueue = orderQueue;
//        initialize();
//    }
//
//    private void initialize() {
//        frame = new JFrame("Pending Orders");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setSize(600, 400);
//        frame.setLayout(new BorderLayout());
//
//        JTable orderTable = new JTable();
//        orderTable.setModel(new DefaultTableModel(new Object[]{"Order ID", "Items", "Status"}, 0));
//
//        loadPendingOrders(orderTable);
//
//        JScrollPane scrollPane = new JScrollPane(orderTable);
//        frame.add(scrollPane, BorderLayout.CENTER);
//
//        JButton backButton = new JButton("Back");
//        backButton.addActionListener(e -> frame.dispose()); // Close this screen when "Back" is clicked
//        frame.add(backButton, BorderLayout.SOUTH);
//
//        frame.setVisible(true);
//    }
//
//    private void loadPendingOrders(JTable orderTable) {
//        List<Order> pendingOrders = orderQueue.getPendingOrders();
//        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
//        model.setRowCount(0); // Clear existing rows in the table
//
//        for (Order order : pendingOrders) {
//            String items = String.join(", ", order.getItems()); // Convert items list to a readable string
//            model.addRow(new Object[]{order.getId(), items, order.getStatus()});
//        }
//    }
//}
