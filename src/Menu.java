import java.util.Scanner;

public class Menu {
    static Scanner sc = new Scanner(System.in);
    static User loggedInUser = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Public Complaint Management System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) register();
            else if (choice == 2) login();
            else break;
        }
    }

    static void register() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.println("Role: 1. Citizen  2. Official");
        int r = sc.nextInt(); sc.nextLine();
        String role = (r == 1) ? "citizen" : "official";
        AuthService.register(name, email, password, role);
    }

    static void login() {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        loggedInUser = AuthService.login(email, password);

        if (loggedInUser == null) {
            System.out.println("Invalid email or password!");
        } else {
            System.out.println("Welcome, " + loggedInUser.getName() + "! Role: " + loggedInUser.getRole());
            if (loggedInUser.getRole().equals("citizen")) citizenMenu();
            else if (loggedInUser.getRole().equals("official")) officialMenu();
            else if (loggedInUser.getRole().equals("admin")) adminMenu();
        }
    }

    static void citizenMenu() {
        while (true) {
            System.out.println("\n--- Citizen Menu ---");
            System.out.println("1. Submit Complaint");
            System.out.println("2. View My Complaints");
            System.out.println("3. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) {
                System.out.print("Title: "); String title = sc.nextLine();
                System.out.print("Category (Roads/Water/Electricity): "); String category = sc.nextLine();
                System.out.print("Description: "); String description = sc.nextLine();
                System.out.print("Area: "); String area = sc.nextLine();
                ComplaintService.submitComplaint(title, category, description, area, loggedInUser.getId());
            } else if (choice == 2) {
                ComplaintService.viewAllComplaints();
            } else break;
        }
    }

    static void officialMenu() {
        while (true) {
            System.out.println("\n--- Official Menu ---");
            System.out.println("1. View All Complaints");
            System.out.println("2. Update Complaint Status");
            System.out.println("3. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) {
                ComplaintService.viewAllComplaints();
            } else if (choice == 2) {
                System.out.print("Enter Complaint ID: "); int id = sc.nextInt(); sc.nextLine();
                System.out.print("New Status (Pending/In Progress/Resolved): "); String status = sc.nextLine();
                ComplaintService.updateStatus(id, status);
            } else break;
        }
    }
    static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Complaints");
            System.out.println("2. Filter by Category");
            System.out.println("3. Filter by Status");
            System.out.println("4. View All Users");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) {
                ComplaintService.viewAllComplaints();
            } else if (choice == 2) {
                System.out.print("Enter category (Roads/Water/Electricity): ");
                String category = sc.nextLine();
                ComplaintService.filterByCategory(category);
            } else if (choice == 3) {
                System.out.print("Enter status (Pending/In Progress/Resolved): ");
                String status = sc.nextLine();
                ComplaintService.filterByStatus(status);
            } else if (choice == 4) {
                ComplaintService.viewAllUsers();
            } else break;
        }
    }
}
