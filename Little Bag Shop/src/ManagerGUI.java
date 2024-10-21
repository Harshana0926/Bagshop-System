import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagerGUI extends JFrame {
    private Manager manager;
    private JTextArea textArea1;

    public ManagerGUI(Manager manager) {
        this.manager = manager;

        // Load bags from file on startup
        List<Bag> bags = FileHandler.loadBagsFromDatFile("bags.dat");
        manager.setBags(bags);

        setTitle("Dashboard");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();




        // View Bags Tab
        JPanel viewBagsPanel = new JPanel(new BorderLayout());
        JTextArea bagsTextArea = new JTextArea();
        bagsTextArea.setEditable(false);
        viewBagsPanel.add(new JScrollPane(bagsTextArea), BorderLayout.CENTER);

        JButton viewBagsButton = new JButton("View");
        viewBagsButton.addActionListener(e -> {
            bagsTextArea.setText("");
            for (Bag bag : manager.getBags()) {
                bagsTextArea.append(bag.toString() + "\n");
            }
        });
        viewBagsPanel.add(viewBagsButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View Bags", viewBagsPanel);
        //viewBagsPanel.add(imagePanel, BorderLayout.SOUTH);

        // Add Bag Tab
        JPanel addBagPanel = new JPanel(new GridLayout(7, 2));
        JTextField idField = new JTextField(20); // Reduced size
        JTextField brandField = new JTextField(20); // Reduced size
        JTextField categoryField = new JTextField(20); // Reduced size
        JTextField sizeField = new JTextField(20); // Reduced size
        JTextField colourField = new JTextField(20); // Reduced size
        JTextField priceField = new JTextField(20); // Reduced size

        addBagPanel.add(new JLabel("Brand:"));
        addBagPanel.add(idField);
        addBagPanel.add(new JLabel("Bag ID:"));
        addBagPanel.add(brandField);
        addBagPanel.add(new JLabel("Size:"));
        addBagPanel.add(categoryField);
        addBagPanel.add(new JLabel("Category:"));
        addBagPanel.add(sizeField);
        addBagPanel.add(new JLabel("Colour:"));
        addBagPanel.add(colourField);
        addBagPanel.add(new JLabel("Price:"));
        addBagPanel.add(priceField);

        JButton addBagButton = new JButton("Add Bag");
        addBagButton.addActionListener(e -> {
            try {
                String id= brandField.getText();
                String brand = idField.getText();
                String category = sizeField.getText();
                String size = categoryField.getText();
                String colour = colourField.getText();
                double price = Double.parseDouble(priceField.getText());
                Bag newBag = new Bag(id, brand, category, size, colour, price);
                manager.addBag(newBag);

                //FileHandler.saveBagsToFile(manager.getBags(), "bags.dat");

                JOptionPane.showMessageDialog(this, "Bag added successfully.");
               // FileHandler.saveBagsToFile(manager.getBags(), "bags.dat"); // Save to file after adding
                //List<Bag> bags = manager.getBags();
                FileHandler.saveBagsToDatFile(bags, "bags.dat");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid inputs");
            }
        });
        addBagPanel.add(new JLabel()); // Empty cell
        addBagPanel.add(addBagButton);
        tabbedPane.addTab("Add Bag", addBagPanel);



// Search Bags Tab
        JPanel searchBagsPanel = new JPanel(new BorderLayout(10, 10));

// Creating a smaller search field
        JPanel searchFieldPanel = new JPanel(new BorderLayout());
        JTextField categorySearchField = new JTextField(15); // Smaller text field
        searchFieldPanel.add(new JLabel("Enter Category:"), BorderLayout.WEST);
        searchFieldPanel.add(categorySearchField, BorderLayout.CENTER);

        // Create and add search button next to the text field
        JButton searchButton = new JButton("Search");
        searchFieldPanel.add(searchButton, BorderLayout.EAST);



// Larger area for showing results
        JTextArea searchResultArea = new JTextArea();
        searchResultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchResultArea);
        scrollPane.setPreferredSize(new Dimension(400, 400)); // Adjust size of the result area

        // Panel for displaying bag images
        JPanel imagePanel = new JPanel(new GridLayout(1, 10, 10, 10)); // 1 row, 5 columns

        // Load and resize images
        String[] imagePaths = {
                "C:/Little Bag Shop/src/bag2.jpg",
                "C:/Little Bag Shop/src/bag3.jpeg",
                "C:/Little Bag Shop/src/bag4.png",
                "C:/Little Bag Shop/src/bag5.jpg",
                "C:/Little Bag Shop/src/bag6.jpeg"
        };

        int imageWidth = 200; // Set the desired width
        int imageHeight = 200; // Set the desired height

        for (String path : imagePaths) {
            ImageIcon originalIcon = new ImageIcon(path);
            Image img = originalIcon.getImage(); // Transform it
            Image resizedImg = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH); // Resize image
            ImageIcon resizedIcon = new ImageIcon(resizedImg); // Create new ImageIcon
            JLabel imageLabel = new JLabel(resizedIcon);
            imagePanel.add(imageLabel);
        }

// Add components to the search panel
        searchBagsPanel.add(searchFieldPanel, BorderLayout.SOUTH);
        searchBagsPanel.add(imagePanel, BorderLayout.CENTER);
        searchBagsPanel.add(scrollPane, BorderLayout.NORTH);


// Add the Search Bags tab to the tabbed pane
        tabbedPane.addTab("Search Bags", searchBagsPanel);

// Search button action listener
        searchButton.addActionListener(e -> {
            searchResultArea.setText("");
            String enteredCategory = categorySearchField.getText().trim();

            boolean found = false;
            for (Bag bag : manager.getBags()) {
                if (bag.getCategory().equalsIgnoreCase(enteredCategory)) {
                    searchResultArea.append(bag.toString() + "\n");
                    found = true;
                }
            }
            if (!found) {
                searchResultArea.append("No bags found in category: " + enteredCategory);
            }
        });


// Create Cashier Account Tab
        JPanel createCashierPanel = new JPanel(new GridLayout(3, 1));
        JTextField cashierUsernameField = new JTextField();
        JTextField cashierPasswordField = new JTextField();

        createCashierPanel.add(new JLabel("Cashier Username:"));
        createCashierPanel.add(cashierUsernameField);
        createCashierPanel.add(new JLabel("Cashier Password:"));
        createCashierPanel.add(cashierPasswordField);

        JButton createCashierButton = new JButton("Create Cashier");
        createCashierButton.addActionListener(e -> {
            String cashierUsername = cashierUsernameField.getText().trim();
            String cashierPassword = cashierPasswordField.getText().trim();

            // Check if both fields are filled
            if (cashierUsername.isEmpty() || cashierPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter both username and password.");
            } else {
                manager.createCashierAccount(cashierUsername, cashierPassword, manager.getBags());
                JOptionPane.showMessageDialog(this, "Cashier account created successfully.");

                // Save cashier account to the cashier.dat file
                List<Cashier> cashiers = FileHandler.loadCashiersFromDatFile("cashier.dat"); // Load existing cashiers
                cashiers.add(new Cashier(cashierUsername, cashierPassword)); // Add new cashier
                FileHandler.saveCashiersToDatFile(cashiers, "cashier.dat"); // Save updated list
            }
        });
        createCashierPanel.add(new JLabel()); // Empty cell
        createCashierPanel.add(createCashierButton);
        tabbedPane.addTab("Create Cashier Account", createCashierPanel);


        // Exit Tab
        JPanel exitPanel = new JPanel();
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
           // FileHandler.saveBagsToFile(manager.getBags(), "bags.txt");

            //List<Bag> bags = manager.getBags();
            FileHandler.saveBagsToDatFile(bags, "bags.dat");


            UserHandler.saveUsers();
            System.out.println("Exiting...");
            dispose();
        });
        exitPanel.add(exitButton);
        tabbedPane.addTab("Exit", exitPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//    }
}

