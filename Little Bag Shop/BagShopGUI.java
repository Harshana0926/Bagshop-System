import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BagShopGUI extends JFrame {
    private Manager manager;

    public BagShopGUI(Manager manager) {
        this.manager = manager;

        setTitle("Bag Shop");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel to View Bags
        JPanel viewBagsPanel = new JPanel(new BorderLayout());
        JTextArea bagsTextArea = new JTextArea();
        bagsTextArea.setEditable(false);
        viewBagsPanel.add(new JScrollPane(bagsTextArea), BorderLayout.CENTER);
        JButton viewBagsButton = new JButton("View Bags");
        viewBagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bagsTextArea.setText("");
                List<Bag> bags = manager.getBags(); // Assuming you have a method to get bags
                for (Bag bag : bags) {
                    bagsTextArea.append(bag.toString() + "\n");
                }
            }
        });
        viewBagsPanel.add(viewBagsButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View Bags", viewBagsPanel);

        // Panel to Add Bag
        JPanel addBagPanel = new JPanel(new GridLayout(5, 2));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField priceField = new JTextField();
        JButton addButton = new JButton("Add Bag");

        addBagPanel.add(new JLabel("ID:"));
        addBagPanel.add(idField);
        addBagPanel.add(new JLabel("Name:"));
        addBagPanel.add(nameField);
        addBagPanel.add(new JLabel("Category:"));
        addBagPanel.add(categoryField);
        addBagPanel.add(new JLabel("Price:"));
        addBagPanel.add(priceField);
        addBagPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idField.getText();
                    String name = nameField.getText();
                    String category = categoryField.getText();
                    double price = Double.parseDouble(priceField.getText());

                    Bag newBag = new Bag(id, name, category, price);
                    manager.addBag(newBag); // Assuming you have an addBag method
                    JOptionPane.showMessageDialog(null, "Bag added successfully.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid price format.");
                }
            }
        });

        tabbedPane.addTab("Add Bag", addBagPanel);

        // Other panels can be added similarly...

        add(tabbedPane, BorderLayout.CENTER);
    }
}
