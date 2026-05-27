import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminScreen extends JFrame {
    private User user;

    public AdminScreen(User user) {
        this.user = user;
        setTitle("Admin Dashboard - " + user.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();

        // Tab 1 - All Complaints
        JPanel complaintsPanel = new JPanel(new BorderLayout());
        String[] compColumns = {"ID", "Title", "Category", "Status", "Area", "Date"};
        DefaultTableModel compModel = new DefaultTableModel(compColumns, 0);
        JTable compTable = new JTable(compModel);
        loadAllComplaints(compModel);

        // Filter panel
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter by Category:"));
        String[] categories = {"All", "Roads", "Water", "Electricity", "Sanitation", "Other"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        filterPanel.add(categoryBox);

        filterPanel.add(new JLabel("Filter by Status:"));
        String[] statuses = {"All", "Pending", "In Progress", "Resolved"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);
        filterPanel.add(statusBox);

        JButton filterBtn = new JButton("Filter");
        filterPanel.add(filterBtn);

        JButton refreshBtn = new JButton("Refresh All");
        filterPanel.add(refreshBtn);

        filterBtn.addActionListener(e -> {
            String category = (String) categoryBox.getSelectedItem();
            String status = (String) statusBox.getSelectedItem();
            loadFilteredComplaints(compModel, category, status);
        });

        refreshBtn.addActionListener(e -> loadAllComplaints(compModel));

        complaintsPanel.add(filterPanel, BorderLayout.NORTH);
        complaintsPanel.add(new JScrollPane(compTable), BorderLayout.CENTER);

        // Tab 2 - All Users
        JPanel usersPanel = new JPanel(new BorderLayout());
        String[] userColumns = {"ID", "Name", "Email", "Role"};
        DefaultTableModel userModel = new DefaultTableModel(userColumns, 0);
        JTable userTable = new JTable(userModel);
        loadAllUsers(userModel);

        JButton refreshUsersBtn = new JButton("Refresh");
        refreshUsersBtn.addActionListener(e -> loadAllUsers(userModel));

        usersPanel.add(new JScrollPane(userTable), BorderLayout.CENTER);
        usersPanel.add(refreshUsersBtn, BorderLayout.SOUTH);

        tabs.add("All Complaints", complaintsPanel);
        tabs.add("All Users", usersPanel);

        // Logout
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });

        add(tabs, BorderLayout.CENTER);
        add(logoutBtn, BorderLayout.SOUTH);
    }

    private void loadAllComplaints(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection con = DatabaseConnection.getConnection();
            ResultSet rs = con.prepareStatement("SELECT * FROM complaints").executeQuery();
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

    private void loadFilteredComplaints(DefaultTableModel model, String category, String status) {
        model.setRowCount(0);
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM complaints WHERE 1=1";
            if (!category.equals("All")) sql += " AND category='" + category + "'";
            if (!status.equals("All")) sql += " AND status='" + status + "'";
            ResultSet rs = con.prepareStatement(sql).executeQuery();
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

    private void loadAllUsers(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection con = DatabaseConnection.getConnection();
            ResultSet rs = con.prepareStatement("SELECT * FROM users").executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role")
                });
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
