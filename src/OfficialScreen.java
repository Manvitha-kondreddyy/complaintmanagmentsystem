import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OfficialScreen extends JFrame {
    private User user;

    public OfficialScreen(User user) {
        this.user = user;
        setTitle("Official Dashboard - " + user.getName());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"ID", "Title", "Category", "Status", "Area", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        loadComplaints(tableModel);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        JButton updateBtn = new JButton("Update Status");
        JButton refreshBtn = new JButton("Refresh");
        JButton logoutBtn = new JButton("Logout");
        bottomPanel.add(updateBtn);
        bottomPanel.add(refreshBtn);
        bottomPanel.add(logoutBtn);

        // Update status action
        updateBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a complaint first!");
            } else {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String[] statuses = {"Pending", "In Progress", "Resolved"};
                String newStatus = (String) JOptionPane.showInputDialog(
                        this,
                        "Select new status:",
                        "Update Status",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        statuses,
                        statuses[0]
                );
                if (newStatus != null) {
                    ComplaintService.updateStatus(id, newStatus);
                    JOptionPane.showMessageDialog(this, "Status updated!");
                    loadComplaints(tableModel);
                }
            }
        });

        refreshBtn.addActionListener(e -> loadComplaints(tableModel));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadComplaints(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM complaints";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getString("status"),
                        rs.getString("area"),
                        rs.getString("date")
                });
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}

