import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends JFrame {

    public RegisterScreen() {
        setTitle("Register");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel title = new JLabel("Register New Account");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        // Name
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Name:"), gbc);
        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        add(nameField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);
        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Password:"), gbc);
        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Role:"), gbc);
        String[] roles = {"citizen", "official"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        add(roleBox, gbc);

        // Register Button
        JButton registerBtn = new JButton("Register");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(registerBtn, gbc);

        // Back Button
        JButton backBtn = new JButton("Back to Login");
        gbc.gridy = 6;
        add(backBtn, gbc);

        // Register action
        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
            } else {
                boolean success = AuthService.register(name, email, password, role);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
                    dispose();
                    new LoginScreen().setVisible(true);
                }
            }
        });

        // Back action
        backBtn.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });

        setVisible(true);
    }
}
