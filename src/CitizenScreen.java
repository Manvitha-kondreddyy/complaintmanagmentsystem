import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CitizenScreen extends JFrame {
    private User user;

    public CitizenScreen(User user) {
        this.user = user;
        setTitle("Citizen Dashboard - " + user.getName());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();

        // Tab 1 - Submit Complaint
        JPanel submitPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        submitPanel.add(new JLabel("Title:"), gbc);
        JTextField titleField = new JTextField(25);
        gbc.gridx = 1;
        submitPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        submitPanel.add(new JLabel("Category:"), gbc);
        String[] categories = {"Roads", "Water", "Electricity", "Sanitation", "Other"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        gbc.gridx = 1;
        submitPanel.add(categoryBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        submitPanel.add(new JLabel("Area:"), gbc);
        JTextField areaField = new JTextField(25);
        gbc.gridx = 1;
        submitPanel.add(areaField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        submitPanel.add(new JLabel("Description:"), gbc);
        JTextArea descArea = new JTextArea(4, 25);
        gbc.gridx = 1;
        submitPanel.add(new JScrollPane(descArea), gbc);

        JButton submitBtn = new JButton("Submit Complaint");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        submitPanel.add(submitBtn, gbc);

        // Tab 2 - View Complaints
        JPanel viewPanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Title", "Category", "Status", "Area", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        viewPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        viewPanel.add(refreshBtn, BorderLayout.SOUTH);

        // Load complaints
        refreshBtn.addActionListener(e -> loadComplaints(tableModel));
        loadComplaints(tableModel);

        // Submit action
        submitBtn.addActionListener(e -> {
            String title = titleField.getText();
            String category = (String) categoryBox.getSelectedItem();
            String area = areaField.getText();
            String desc = descArea.getText();

            if (title.isEmpty() || area.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
            } else {
                ComplaintService.submitComplaint(title, category, desc, area, user.getId());
                JOptionPane.showMessageDialog(this, "Complaint submitted successfully!");
                titleField.setText("");
                areaField.setText("");
                descArea.setText("");
                loadComplaints(tableModel);
            }
        });

        tabs.add("Submit Complaint", submitPanel);
        tabs.add("My Complaints", viewPanel);

        // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });

        add(tabs, BorderLayout.CENTER);
        add(logoutBtn, BorderLayout.SOUTH);
    }

    private void loadComplaints(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM complaints WHERE citizen_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
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

