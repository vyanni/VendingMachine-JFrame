import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//Name: V.Susanto
//Start to End Date: April 11th - April 16th
//Assignment: VendingMachine
//Purpose: This specific class works as a blueprint for any general product in the vending machine itself, handling all it's properties such as price, quantity, and name

public class VendingItems {
    String productName;
    double productPrice;
    int originalQuantity;
    int productQuantity;
    int dailySales = 0;
    ArrayList<Integer> productSales = new ArrayList<>();
    //Creates all the values needed for the product, such as:
    //Price, name, original quantity and current quantity, the sales for one day and a list of the product's sales daily

    JLabel Jquantity = new JLabel("Amount left: " + productQuantity);
    JPanel productPanel = new JPanel();
    //Creates the panel itself for the product, as well as the label for the quantity to be constantly updated

    public void vendingObj(String nameInsert, double priceInsert, int quantityInsert){
        productName = nameInsert;
        productPrice = priceInsert;
        originalQuantity = quantityInsert;
        productQuantity = originalQuantity;

        Jquantity.setText("Amount left: " + productQuantity);
        //This method allows the main function to call this method and insert the name, price, and quantity through the parameters
    }

    public void buyObj(){
        if(productQuantity > 0){
            productQuantity--;
            dailySales++;
        }if(productQuantity == 0){
            JOptionPane.showMessageDialog(productPanel, "Out of Stock! Empty!", "Empty Stock", JOptionPane.ERROR_MESSAGE);
        }
        //This method is for the buy button, to change the amount of quantity, sales, and the out of stock option
    }

    public void resetDay(){
        productQuantity = originalQuantity;
        productSales.add(dailySales);
        dailySales = 0;

        Jquantity.setText("Amount left: " + productQuantity);
        //This method is for resetting the day, it brings the product back to the original quantity, then adds the daily sales to the list
    }

    public JPanel createPanel(){
        //This method creates the individual panel for the products, with the button, text, and live updated amount

        JPanel outerPanel = new JPanel(new GridBagLayout());
        //Creates an outer panel, which is gonna be used to be able to center the text panel

        productPanel.removeAll();
        productPanel.setLayout(new BorderLayout());
        productPanel.setBackground(new Color(220, 237, 250));

        JLabel Jname = new JLabel(productName);
        JLabel Jprice = new JLabel("$" + String.valueOf(productPrice));
        JButton buyButton = new JButton("Buy");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //This resets the panel, and then declares the name, price, and buy button for set-up
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        Jname.setFont(new Font("Cambria", Font.PLAIN, 16));
        Jprice.setFont(new Font("Cambria", Font.PLAIN, 16));
        Jquantity.setFont(new Font("Cambria", Font.PLAIN, 16));
        Jname.setAlignmentX(Component.CENTER_ALIGNMENT);
        Jprice.setAlignmentX(Component.CENTER_ALIGNMENT);
        Jquantity.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Sets up the text part of the panel, where each label has font cambria and are set up vertically, aligned along the center
    
        textPanel.add(Jname);
        textPanel.add(Jprice);
        textPanel.add(buyButton);
        textPanel.add(Jquantity);
        //Adds each of the components to the panel

        outerPanel.add(textPanel);
        productPanel.add(outerPanel, BorderLayout.CENTER);
        //Adds the text parts of the panel to the complete panel

        buyButton.addActionListener(event -> {
            buyObj();
            Jquantity.setText("Amount left: " + productQuantity);
        });
        //When the buy button is clicked, it executes the buy objects method, and then updates the amount left
        
        return productPanel;
    }
}
