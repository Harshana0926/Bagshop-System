import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setTitle("Login");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for the central content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15); // Smaller text field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15); // Smaller text field

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        contentPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        contentPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        JButton resetButton = new JButton("Reset"); // New Reset button
        JPanel buttonPanel = new JPanel(); // Panel for buttons
        buttonPanel.add(loginButton);
        buttonPanel.add(resetButton);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(buttonPanel, gbc);

        // "Welcome" text in the middle
        JLabel welcomeLabel = new JLabel("Welcome to the Little Bag Shop", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        add(contentPanel, BorderLayout.CENTER);

        // Panel for images at the bottom
        JPanel imagePanel = new JPanel(new GridLayout(1, 5, 5, 5));
        String[] imagePaths = {
                "C:/Little Bag Shop/src/bag1.jpg",


        };
        int imageWidth = 600; // Adjusted for smaller images
        int imageHeight =400;

        for (String path : imagePaths) {
            ImageIcon originalIcon = new ImageIcon(path);
            if (originalIcon.getIconWidth() == -1) {
                // If image is not found, use a default image
                originalIcon = new ImageIcon("src/default.jpg");
            }
            Image img = originalIcon.getImage();
            Image resizedImg = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImg);
            JLabel imageLabel = new JLabel(resizedIcon);
            imagePanel.add(imageLabel);
        }

        add(imagePanel, BorderLayout.SOUTH);

        // Add action listeners
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Perform authentication using the UserHandler
            User user = UserHandler.authenticate(username, password);

            if (user != null && user instanceof Manager) {
                // If authentication is successful and user is a manager, open ManagerGUI
                Manager manager = (Manager) user;
                SwingUtilities.invokeLater(() -> {
                    new ManagerGUI(manager).setVisible(true);
                    dispose(); // Close the login window
                });
            } else {
                // Show error message if authentication fails
                JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password.");
            }
        });

        resetButton.addActionListener(e -> {
            // Reset fields
            usernameField.setText("");
            passwordField.setText("");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
            loginGUI.setVisible(true);
        });
    }
}
