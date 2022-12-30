package com.example.tp5_exo1_gui_software.screens;

import com.example.tp5_exo1_gui_software.config.AppConfig;
import com.example.tp5_exo1_gui_software.models.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    //private final JPanel signupPanel;

    public LoginScreen() {
        // Get the AppConfig Singleton
        AppConfig config = AppConfig.getInstance();

        // Preparing the window
        setSize(config.LOGIN_WINDOW_WIDTH, config.LOGIN_WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(config.TEXT_COLOR);

        // Layout Manager
        setLayout(new BorderLayout());

        // Header Panel
        JLabel titlePanel = new JLabel("Stock Management App");
        titlePanel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, config.TITLE_SIZE));
        titlePanel.setForeground(config.BG_COLOR);
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Load images
        // ImageIcon logo = new ImageIcon("images/fds.png");
        // JLabel universityLogo = new JLabel(logo);
        // universityLogo.setSize(20, 20);
        // headerPanel.add(universityLogo);
        headerPanel.add(titlePanel);

        // Form Panel (Email and Password)
        JLabel emailLabel = new JLabel("\uD83D\uDC64 Email :");
        JLabel passwordLabel = new JLabel("\uD83D\uDD12 Password :");
        JTextField emailField = new JTextField(config.TEXT_FIELD_SIZE);
        JPasswordField passwordField = new JPasswordField(config.TEXT_FIELD_SIZE);
        // Editing Fonts and Colors
        emailLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, config.NORMAL_FONT_SIZE));
        emailLabel.setForeground(config.BG_COLOR);
        passwordLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, config.NORMAL_FONT_SIZE));
        passwordLabel.setForeground(config.BG_COLOR);
        emailField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, config.NORMAL_FONT_SIZE));
        passwordField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, config.NORMAL_FONT_SIZE));

        // Login and Signup Buttons
        JButton loginButton = new JButton("LOGIN");
        JButton signButton = new JButton("SIGNUP");
        loginButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, config.NORMAL_FONT_SIZE));
        loginButton.setForeground(config.TEXT_COLOR);
        final int buttonWidth = config.LOGIN_WINDOW_WIDTH - (config.LARGE_PADDING * 2) - config.SMALL_PADDING;
        loginButton.setPreferredSize(new Dimension(buttonWidth, 40));
        loginButton.setBackground(config.BTN_COLOR);
        loginButton.setBorder(new EmptyBorder(config.SMALL_PADDING, 152, config.SMALL_PADDING, 152));
        signButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, config.NORMAL_FONT_SIZE));
        signButton.setForeground(config.TEXT_COLOR);
        signButton.setPreferredSize(new Dimension(buttonWidth, 40));
        signButton.setBackground(config.BTN_COLOR);
        signButton.setBorder(new EmptyBorder(config.SMALL_PADDING, 144, config.SMALL_PADDING, 145));

        // Align everything
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        signButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        formPanel.add(headerPanel);
        formPanel.add(Box.createVerticalStrut(config.SMALL_PADDING));
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(Box.createVerticalStrut(config.SMALL_PADDING));
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(config.SMALL_PADDING));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(config.SMALL_PADDING));
        formPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        formPanel.add(Box.createVerticalStrut(config.SMALL_PADDING));
        formPanel.add(signButton);

        // Wrapping everything inside a Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(config.LARGE_PADDING, config.LARGE_PADDING, config.LARGE_PADDING, config.LARGE_PADDING));
        contentPanel.add(formPanel);

        add(contentPanel);

        // Login Button Listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the email and password from the text fields
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    User user = User.login(email, password);
                    // Affiche un dialogue qui affiche le succées de l'authentification
                    JOptionPane.showMessageDialog(LoginScreen.this, "Login successful!");
                    // Ouvrir le Menu Principale si l'utilisateur s'est authentifié
                    LoginScreen.this.dispose();
                    MenuScreen menuScreen = new MenuScreen(user);
                    menuScreen.setVisible(true);
                } catch (Exception ex) {
                    // Sinon, affiche un dialogue qui affiche une erreur
                    JOptionPane.showMessageDialog(LoginScreen.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Signup Button Listener
        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create the dialog
                JDialog dialog = new JDialog(LoginScreen.this, "Create User", true);
                dialog.setSize(config.LOGIN_WINDOW_WIDTH, config.LOGIN_WINDOW_HEIGHT);
                dialog.setLocationRelativeTo(LoginScreen.this);

                // Create a panel to hold the fields
                JPanel panel = new JPanel(new GridLayout(11, 1));
                panel.setBorder(new EmptyBorder(config.SMALL_FONT_SIZE, config.SMALL_PADDING, config.SMALL_PADDING, config.SMALL_PADDING));

                // Add the fields to the panel
                panel.add(new JLabel("Fullname :"));
                JTextField nameField = new JTextField();
                panel.add(nameField);
                panel.add(new JLabel("Age :"));
                JTextField ageField = new JTextField();
                panel.add(ageField);
                panel.add(new JLabel("Email Address :"));
                JTextField emailField = new JTextField();
                panel.add(emailField);
                panel.add(new JLabel("Password :"));
                JTextField passwordField = new JTextField();
                panel.add(passwordField);

                // Separators
                panel.add(Box.createVerticalStrut(config.SMALL_PADDING));
                panel.add(new JSeparator(SwingConstants.HORIZONTAL));

                // Create the "Create" button
                JButton createButton = new JButton("CREATE ACCOUNT");
                createButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, config.NORMAL_FONT_SIZE));
                createButton.setForeground(config.TEXT_COLOR);
                createButton.setPreferredSize(new Dimension(buttonWidth, 40));
                createButton.setBackground(config.BTN_COLOR);

                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Get the user input
                        String name = nameField.getText();
                        int age = Integer.parseInt(ageField.getText());
                        String email = emailField.getText();
                        String password = passwordField.getText();

                        // Create a new user with the input
                        User user = new User(name, age, email, password);

                        // Add the user to the list
                        User.users.add(user);

                        // Close the dialog
                        dialog.dispose();
                    }
                });
                panel.add(createButton);

                // Add the panel to the dialog
                dialog.add(panel);

                // Show the dialog
                dialog.setVisible(true);
            }
        });
    }
}