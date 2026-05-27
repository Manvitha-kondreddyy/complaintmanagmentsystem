import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        setTitle("Public Complaint Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel title = new JLabel("Complaint Management System");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        // Email
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Email:"), gbc);
        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Login Button
        JButton loginBtn = new JButton("Login");
        gbc.gridx = 0; gbc.gridy = 3;
        add(loginBtn, gbc);

        // Register Button
        JButton registerBtn = new JButton("Register");
        gbc.gridx = 1;
        add(registerBtn, gbc);

        // Login action
        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            User user = AuthService.login(email, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid email or password!");
            } else {
                JOptionPane.showMessageDialog(this, "Welcome " + user.getName() + "!");
                dispose();
                if (user.getRole().equals("citizen")) new CitizenScreen(user).setVisible(true);
                else if (user.getRole().equals("official")) new OfficialScreen(user).setVisible(true);
                else if (user.getRole().equals("admin")) new AdminScreen(user).setVisible(true);
            }
        });

        // Register action
        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterScreen().setVisible(true);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}

