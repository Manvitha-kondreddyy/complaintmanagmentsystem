import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ComplaintService {

    // Submit a new complaint
    public static void submitComplaint(String title, String category,
                                       String description, String area, int citizenId) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "INSERT INTO complaints (title, category, description, area, citizen_id, date, status) " +
                    "VALUES (?, ?, ?, ?, ?, CURDATE(), 'Pending')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, category);
            ps.setString(3, description);
            ps.setString(4, area);
            ps.setInt(5, citizenId);
            ps.executeUpdate();
            con.close();
            System.out.println("Complaint submitted successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // View all complaints
    public static void viewAllComplaints() {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM complaints";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- All Complaints ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Title: " + rs.getString("title") +
                        " | Category: " + rs.getString("category") +
                        " | Status: " + rs.getString("status") +
                        " | Area: " + rs.getString("area"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Update complaint status (for officials)
    public static void updateStatus(int complaintId, String newStatus) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "UPDATE complaints SET status=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setInt(2, complaintId);
            ps.executeUpdate();
            con.close();
            System.out.println("Status updated successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // Filter complaints by category
    public static void filterByCategory(String category) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM complaints WHERE category=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- Complaints in category: " + category + " ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Title: " + rs.getString("title") +
                        " | Status: " + rs.getString("status") +
                        " | Area: " + rs.getString("area"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Filter complaints by status
    public static void filterByStatus(String status) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM complaints WHERE status=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- Complaints with status: " + status + " ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Title: " + rs.getString("title") +
                        " | Category: " + rs.getString("category") +
                        " | Area: " + rs.getString("area"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // View all users (admin only)
    public static void viewAllUsers() {
        try {
            Connection con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- All Users ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Name: " + rs.getString("name") +
                        " | Email: " + rs.getString("email") +
                        " | Role: " + rs.getString("role"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

