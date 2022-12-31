package com.example.tp5_exo1_gui_software.screens;

import com.example.tp5_exo1_gui_software.config.AppConfig;
import com.example.tp5_exo1_gui_software.config.GsonSingleton;
import com.example.tp5_exo1_gui_software.models.Product;
import com.example.tp5_exo1_gui_software.models.User;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class MenuScreen extends JFrame {

    public User user;

    public MenuScreen(User user /* // L'utilisateur connecté */) {
        // Get the AppConfig Singleton
        AppConfig config = AppConfig.getInstance();

        // Preparing the window
        setSize(config.WINDOW_WIDTH, config.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Product Management App");

        // Menubar dans le haut pour afficher un message d'accueil pour l'utilisateur
        // et lui offrir un menu pour se déconnecter ou quitter
        JMenuBar menuBar = new JMenuBar();
        JMenu userMenu = new JMenu("Welcome, " + user.getName() + "! 🙂");
        JMenuItem editAccountMenuItem = new JMenuItem("Edit Account");
        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        JMenuItem helpMenuItem = new JMenuItem("Help");
        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        // Ajouter les Options au Menu
        userMenu.add(editAccountMenuItem);
        userMenu.add(settingsMenuItem);
        userMenu.addSeparator();
        userMenu.add(helpMenuItem);
        userMenu.add(logoutMenuItem);
        userMenu.addSeparator();
        userMenu.add(exitMenuItem);

        // Désactiver les menus non implémenté
        editAccountMenuItem.setEnabled(false);
        settingsMenuItem.setEnabled(false);
        helpMenuItem.setEnabled(false);

        // Les Actions Logout et Exit
        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Naviguer vers la page d'authentification
                MenuScreen.this.dispose();
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            }
        });
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the app
                System.exit(0);
            }
        });

        menuBar.add(userMenu);

        // Menu Panel to display the buttons stacked vertically
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        // Menu Actions
        Action displayAction = new AbstractAction("\uD83D\uDCC1  Display Products") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display the products in a repository
                displayProducts();
            }
        };
        Action fetchAction = new AbstractAction("\uD83D\uDD0D  Fetch a Product by ID") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Afficher un dialogue pour chercher le Produit
                String id = JOptionPane.showInputDialog(MenuScreen.this, "Enter the ID of the product:");
                // Get Product
                Product product = getProduct(id);
                // Vérifier si l'utilisateur a cliqué sur OK
                if (id != null) {
                    // Vérifier si le Produit existe
                    if (product == null) {
                        // Sinon Lancer l'exception
                        throw new ProductNotFoundException("Product not found!");
                    } else {
                        // Afficher le Produit
                        JOptionPane.showMessageDialog(MenuScreen.this, "Product: " + product, "Product Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        };
        Action addAction = new AbstractAction("\uD83D\uDDF3  Create a new Product") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Créer un dialoggue
                JDialog dialog = new JDialog(MenuScreen.this, "Create a new Product", true);
                dialog.setSize(config.LOGIN_WINDOW_WIDTH, config.LOGIN_WINDOW_HEIGHT);
                dialog.setLocationRelativeTo(MenuScreen.this);

                // Créer un panel pour contenir les champs de saisie
                JPanel panel = new JPanel(new GridLayout(11, 1));
                panel.setBorder(new EmptyBorder(config.SMALL_FONT_SIZE, config.SMALL_PADDING, config.SMALL_PADDING, config.SMALL_PADDING));

                // Ajouter les champs de saisie
                panel.add(new JLabel("ID :"));
                JTextField idField = new JTextField();
                panel.add(idField);
                panel.add(new JLabel("Product Name :"));
                JTextField nameField = new JTextField();
                panel.add(nameField);
                panel.add(new JLabel("Product Price :"));
                JTextField priceField = new JTextField();
                panel.add(priceField);
                panel.add(new JLabel("Expiration Date :"));
                JTextField dateField = new JTextField();
                dateField.setText(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
                panel.add(dateField);

                // Séparateurs
                panel.add(Box.createVerticalStrut(config.SMALL_PADDING));
                panel.add(new JSeparator(SwingConstants.HORIZONTAL));

                // CREATE NEW PRODUCT Button
                JButton createButton = new JButton("CREATE NEW PRODUCT");
                createButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, config.NORMAL_FONT_SIZE));
                createButton.setForeground(config.TEXT_COLOR);
                createButton.setPreferredSize(new Dimension(200, 40));
                createButton.setBackground(config.BTN_COLOR);

                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Get les valeurs des champs
                        String id = idField.getText();
                        String name = nameField.getText();
                        int price = Integer.parseInt(priceField.getText());
                        LocalDate expirationDate = LocalDate.parse(dateField.getText(), DateTimeFormatter.ISO_DATE);

                        // Ajouter le nouveau produit dans le Repository
                        Product product = new Product();
                        product.setId(id);
                        product.setName(name);
                        product.setPrice(price);
                        product.setExpirationDate(expirationDate);
                        try {
                            addProduct(product);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        // Fermer le dialogue
                        dialog.dispose();
                    }
                });
                panel.add(createButton);
                dialog.add(panel);
                dialog.setVisible(true);
            }
        };
        Action deleteAction = new AbstractAction("\uD83D\uDEAE  Delete a Product by ID") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user to enter the ID of the product to delete
                String id = JOptionPane.showInputDialog(MenuScreen.this, "Enter the ID of the product:");

                // Delete the product
                try {
                    deleteProduct(id);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        Action updateAction = new AbstractAction("\uD83D\uDCDD  Update an existing Product") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Créer un dialogue pour fetcher le Produit par le ID
                String id = JOptionPane.showInputDialog(MenuScreen.this, "Enter the ID of the Product :");
                Product product = getProduct(id);
                // Vérifier si l'utilisateur a cliqué sur OK
                if (id != null) {
                    // Vérifier si le Produit existe
                    if (product == null) {
                        // Sinon Lancer l'exception
                        throw new ProductNotFoundException("Product not found!");
                    }
                }

                // Créer un dialogue
                JDialog dialog = new JDialog(MenuScreen.this, "Create a new Product", true);
                dialog.setSize(config.LOGIN_WINDOW_WIDTH, config.LOGIN_WINDOW_HEIGHT);
                dialog.setLocationRelativeTo(MenuScreen.this);

                // Créer un panel pour contenir les champs de saisie
                JPanel panel = new JPanel(new GridLayout(11, 1));
                panel.setBorder(new EmptyBorder(config.SMALL_FONT_SIZE, config.SMALL_PADDING, config.SMALL_PADDING, config.SMALL_PADDING));

                // Ajouter les champs de saisie (avec les valeurs du produit existant)
                panel.add(new JLabel("ID :"));
                JTextField idField = new JTextField();
                idField.setText(Objects.requireNonNull(product).getId());
                panel.add(idField);
                panel.add(new JLabel("Product Name :"));
                JTextField nameField = new JTextField();
                nameField.setText(product.getName());
                panel.add(nameField);
                panel.add(new JLabel("Product Price :"));
                JTextField priceField = new JTextField();
                priceField.setText(Double.toString(product.getPrice()));
                panel.add(priceField);
                panel.add(new JLabel("Expiration Date :"));
                JTextField dateField = new JTextField();
                dateField.setText(product.getExpirationDate().format(DateTimeFormatter.ISO_DATE));
                panel.add(dateField);

                // Séparateurs
                panel.add(Box.createVerticalStrut(config.SMALL_PADDING));
                panel.add(new JSeparator(SwingConstants.HORIZONTAL));

                // UPDATE PRODUCT Button
                JButton createButton = new JButton("UPDATE PRODUCT");
                createButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, config.NORMAL_FONT_SIZE));
                createButton.setForeground(config.TEXT_COLOR);
                createButton.setPreferredSize(new Dimension(200, 40));
                createButton.setBackground(config.BTN_COLOR);

                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Get les valeurs des champs
                        String id = idField.getText();
                        String name = nameField.getText();
                        double price = Double.parseDouble(priceField.getText());
                        LocalDate expirationDate = LocalDate.parse(dateField.getText(), DateTimeFormatter.ISO_DATE);
                        // Update the product's details
                        product.setId(id);
                        product.setName(name);
                        product.setPrice(price);
                        product.setExpirationDate(expirationDate);
                        // On met à jours le fichier Json qui stock les produits
                        String updatedJsonString = GsonSingleton.getInstance().toJson(Product.repository, new TypeToken<List<Product>>() {
                        }.getType());
                        BufferedWriter writer = null;
                        try {
                            writer = new BufferedWriter(new FileWriter(GsonSingleton.currentDir + "\\data\\repository.json"));
                            writer.write(updatedJsonString);
                            writer.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        // Fermer le dialogue
                        dialog.dispose();
                    }
                });
                panel.add(createButton);
                dialog.add(panel);
                dialog.setVisible(true);
            }
        };

        // Création des éléments du Menu Principal
        JMenuItem displayMenuItem = new JMenuItem(displayAction);
        JMenuItem fetchMenuItem = new JMenuItem(fetchAction);
        JMenuItem addMenuItem = new JMenuItem(addAction);
        JMenuItem deleteMenuItem = new JMenuItem(deleteAction);
        JMenuItem updateMenuItem = new JMenuItem(updateAction);

        // Modification de la police
        displayMenuItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        displayMenuItem.setForeground(config.BG_COLOR);
        fetchMenuItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        fetchMenuItem.setForeground(config.BG_COLOR);
        addMenuItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        addMenuItem.setForeground(config.BG_COLOR);
        deleteMenuItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        deleteMenuItem.setForeground(config.BG_COLOR);
        updateMenuItem.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        updateMenuItem.setForeground(config.BG_COLOR);

        // Ajout des éléments out boutons au Menu Principal
        buttonPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPanel.add(displayMenuItem);
        buttonPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPanel.add(fetchMenuItem);
        buttonPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPanel.add(addMenuItem);
        buttonPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPanel.add(deleteMenuItem);
        buttonPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPanel.add(updateMenuItem);

        add(buttonPanel, BorderLayout.CENTER);

        // Set Menu Bar
        setJMenuBar(menuBar);
    }

    // Afficher les Produits dans un tableau
    private void displayProducts() {
        // Get the AppConfig Singleton
        AppConfig config = AppConfig.getInstance();

        // Create the dialog
        JDialog dialog = new JDialog(MenuScreen.this, "Products in Repository", true);
        dialog.setSize(config.LOGIN_WINDOW_WIDTH, config.LOGIN_WINDOW_HEIGHT);
        dialog.setLocationRelativeTo(MenuScreen.this);

        // Create a panel to hold the fields
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(new EmptyBorder(config.SMALL_FONT_SIZE, config.SMALL_PADDING, config.SMALL_PADDING, config.SMALL_PADDING));

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel();

        // Add columns to the table model
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Expiration Date");

        // Add rows to the table model
        for (Product product : Product.repository) {
            Object[] row = {product.getId(), product.getName(), product.getPrice(), product.getExpirationDate()};
            tableModel.addRow(row);
        }

        // Create the table
        JTable table = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Add the panel to the dialog
        dialog.add(scrollPane);

        // Show the dialog
        dialog.setVisible(true);
    }

    // Ajouter un Produit
    private void addProduct(Product product) throws IOException {
        for (Product p : Product.repository) {
            if (p.getId().equals(product.getId())) {
                // If a product with the same ID already exists, throw an exception
                throw new ProductAlreadyExistsException("Product already exists!");
            }
        }
        // On met à jours la liste statique des produits
        Product.repository.add(product);
        // Ainsi que le fichier Json qui stock les produits
        String updatedJsonString = GsonSingleton.getInstance().toJson(Product.repository, new TypeToken<List<Product>>() {
        }.getType());
        BufferedWriter writer = new BufferedWriter(new FileWriter(GsonSingleton.currentDir + "\\data\\repository.json"));
        writer.write(updatedJsonString);
        writer.close();
    }

    // Supprimer un Produit
    private void deleteProduct(String id) throws IOException {
        Product product = getProduct(id);
        // Vérifier si l'utilisateur a cliqué sur OK
        if (id != null) {
            // Vérifier si le Produit existe
            if (product == null) {
                // Sinon Lancer l'exception
                throw new ProductNotFoundException("Product not found!");
            }
        }
        Product.repository.remove(product);
        // Ainsi que le fichier Json qui stock les produits
        String updatedJsonString = GsonSingleton.getInstance().toJson(Product.repository, new TypeToken<List<Product>>() {
        }.getType());
        BufferedWriter writer = new BufferedWriter(new FileWriter(GsonSingleton.currentDir + "\\data\\repository.json"));
        writer.write(updatedJsonString);
        writer.close();
    }

    // Fetch Produit par son ID
    private Product getProduct(String id) {
        for (Product product : Product.repository) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    // Cette Exception est lancé lorsque le ID saisit n'existe pas
    private static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

    // Cette Exception est lancé lorsque un Produit avec le meme ID existe déja
    private static class ProductAlreadyExistsException extends RuntimeException {
        public ProductAlreadyExistsException(String message) {
            super(message);
        }
    }
}