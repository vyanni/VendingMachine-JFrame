import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//Start to End Date: April 11th - April 16th
//Assignment: VendingMachine
//Purpose: This program will simulate a vending machine, where there are multiple options for a user to order, and the operator of the vending machine can see their sales

/*
    Side notes:
    My enhancements are: 
    - The vending machine products are dynamic and can be inputted by the Operator, up to 10 different items
    - Password logging between the user and operator pages, (THE PASSWORD IS 'password', all lowercase)
    - File logging the daily revenue + putting it on the operator screen, while we did need to show it, doing both helps for safekeeping
 */

public class Main {
    static ArrayList<VendingItems> vendingProducts = new ArrayList<>();
    static int objAmount;
    static int totalRevenue = 0;
    //Sets up an arraylist of "VendingItems" for the objects/products that the user is going to sell on the vending machine
    //As well as the amount of product through objAmount
    public static void main(String[] args) throws Exception {
        JFrame vendingWindow = new JFrame("The Vending Machine");
        vendingWindow.getContentPane().setLayout(new BorderLayout());
        //Creates the panel itself, named Vending Machine, and sets the layout

        JPanel introPanel = new JPanel();
        introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));
        introPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        introPanel.setBackground(new Color(209, 236, 255));
        //Creates a panel for the intro, where the operator inputs the amount of products he/she wants

        JTextField userInput = new JTextField(1); 
        userInput.setMaximumSize(new Dimension(400, 20));
        userInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        userInput.setHorizontalAlignment(JTextField.CENTER);
        //The text box where the operator inputs the number of products

        JLabel titleLabel = new JLabel("Welcome to your new Vending Machine application! ");
        JLabel productAmount = new JLabel("Mx. Operator, start by entering how many products you want to sell (Enter a number between 1-10): ");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 24));
        productAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        productAmount.setFont(new Font("Cambria", Font.PLAIN, 16));
        //The title of the intro, set to Cambria font and aligned in the center

        JButton submitButton = new JButton("Enter");
        submitButton.setMaximumSize(new Dimension(80, 20));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Enter button, which submits the text from the input box and checks it

        introPanel.add(titleLabel);
        introPanel.add(Box.createVerticalStrut(10));
        introPanel.add(productAmount);
        introPanel.add(Box.createVerticalStrut(25));
        introPanel.add(userInput);
        introPanel.add(Box.createVerticalStrut(10));
        introPanel.add(submitButton);
        //Adds the above components to the intro panel

        vendingWindow.getContentPane().add(introPanel, BorderLayout.CENTER);
        //Adds the intro panel to the main Jframe, called Vending window

        vendingWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vendingWindow.setSize(1920,1080);
        //Finish setting up the main Jframe, for it's default size and how to exit
        
        submitButton.addActionListener(firstevent -> {
            //This is what occurs after the operator presses the enter button
            String numberResult = userInput.getText();
                try{
                    objAmount = Integer.parseInt(numberResult);
                    if(objAmount > 0 && objAmount <= 10){
                        startAddproducts(vendingWindow, 0);
                        //The program checks if the input is a number between 1 - 10, if so, to move to the next stage which adds the products themselves through startAddproducts()
                        //If not, show an error box asking the user to try again
                    }else{
                        JOptionPane.showMessageDialog(vendingWindow, "You cannot have lower than 0 or greater than 10!", "Number Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch(NumberFormatException error){
                    JOptionPane.showMessageDialog(vendingWindow, "Please enter a number!", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            });

        vendingWindow.setVisible(true);
        //Sets everything completely visible
    }

    public static void activateOperatorpanel(JFrame mainFrame) throws IOException{
        //This is the method which shows the operator side of the vending machine application, after the password with the sales breakdown

        BufferedWriter revenueWriter = new BufferedWriter(new FileWriter("src\\reciptFile.txt"));
        //Creates a buffered writer, to write the recipt from the operator panel into a file for safekeeping as well
        //Days 12+ go off the page on the operator screen, as that's mostly a preview while the file will show the full extent

        totalRevenue = 0;
        //Makes sure that totalrevenue is reset beforehand

        mainFrame.getContentPane().removeAll();
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS)); 
        //Resets the main frame, and sets up a new layout for it

        JLabel operatorTitle = new JLabel("Total Sales for each Day: ");
        operatorTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorTitle.setFont(new Font("Cambria", Font.BOLD, 24));
        operatorTitle.setBackground(new Color(220, 237, 250));
        //Title for the sales and top of the day

        JLabel rememberLabel = new JLabel("The day must be finished in order for it to appear. If nothing appears, the day hasn't been reset yet. The full reciept and days 12+ will be written within the recipt file");
        rememberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rememberLabel.setFont(new Font("Cambria", Font.PLAIN, 12));
        rememberLabel.setBackground(new Color(220, 237, 250));
        //Just a small reminder text that the day must end and be reset in order to see it on the recipt screen 

        mainFrame.add(operatorTitle);
        mainFrame.add(rememberLabel);
        //Adds a title, "Total sales for each day", for more clarity

        JLabel totalRevenuetext = new JLabel("Total Revenue: " + String.valueOf(totalRevenue));
        totalRevenuetext.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainFrame.add(totalRevenuetext);
        //Adds the totalrevenutext, which is the revenue of every day added up

        //The page will only show the revenue up to the first 12 days, past that, it will only show the rest on the reciptFile 

        for(int i = 0; i < vendingProducts.get(0).productSales.size(); i++){
            JPanel dayPanel = new JPanel();
            dayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
            //Each day is set up as an individual panel, and this is how it's formatted

            JLabel dayText = new JLabel("Day " + (i+1) + ":");
            dayText.setAlignmentX(Component.CENTER_ALIGNMENT);
            dayText.setFont(new Font("Cambria", Font.ITALIC, 24));
            int dayRevenue = 0;
            //Adds the Day text, was a title

            for(int j = 0; j < objAmount; j++){
                dayRevenue += (vendingProducts.get(j).productSales.get(i) * vendingProducts.get(j).productPrice);
            }

            if(i <= 11){
                totalRevenue += dayRevenue;
                totalRevenuetext.setAlignmentX(Component.CENTER_ALIGNMENT);
                totalRevenuetext.setFont(new Font("Cambria", Font.PLAIN, 12));
                totalRevenuetext.setText("Total Revenue: " + String.valueOf(totalRevenue));
                //Adds the total revenue text

                JLabel dayRevenuetext = new JLabel("Revenue: $" + String.valueOf(dayRevenue));
                dayRevenuetext.setFont(new Font("Cambria", Font.PLAIN, 12));
                //Writes the content into the reciptFile

                revenueWriter.write("Day " + (i+1) + " Revenue: " + dayRevenue + "\n");
                dayPanel.add(Box.createVerticalStrut(8));
                dayPanel.add(dayText);
                dayPanel.add(dayRevenuetext);
                dayPanel.add(Box.createVerticalStrut(8));
                //Adds everything to the final day panel
                mainFrame.add(dayPanel);

            }else if(i >= 13){
                //If more than 12 days have passed, stop creating panels for each indvidual day, and instead remind the operator that days 12+ will be on recipt
                totalRevenue += dayRevenue;
                revenueWriter.write("Day " + (i+1) + " Revenue: " + dayRevenue + "\n");
                JLabel twelvereminder = new JLabel("Days after 12 will be shown on the recipt. ");
                twelvereminder.setFont(new Font("Cambria", Font.PLAIN, 12));
                twelvereminder.setAlignmentX(Component.CENTER_ALIGNMENT);
                mainFrame.add(twelvereminder);

                //There's no reason this block of code should occur again, so break the loop when there's 14 more days or greater
                if(i >= 14){
                    break;
                }
            }
        }
        /*
         Essentially for each product in the array that the operator originally inputted, it takes each of the sales that
         each product made, and adds them up for the daily revenue. Then, sets that number into the JLabel and adds it into
         a panel for each separate day, which is then added on to the full window
         */

        JPanel buttonPaneloperator = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back");
        buttonPaneloperator.add(backButton, BorderLayout.SOUTH);
        backButton.addActionListener(backEvent -> showVendingMachine(mainFrame));
        //Adds a back button, as well as a panel for the bottom section to make it cleaner

        buttonPaneloperator.setBorder(BorderFactory.createEmptyBorder(20, 750, 50, 750));
        buttonPaneloperator.setBackground(new Color(220, 237, 250));
        //The panel for the back button is set up, and placed at the bottom

        mainFrame.add(buttonPaneloperator, BorderLayout.SOUTH);
        mainFrame.revalidate();
        mainFrame.repaint();
        //Adds the panels to the main frame, then asks the main frame to update itself

        revenueWriter.close();
    }

    public static void showVendingMachine(JFrame mainFrame){
        //This is the method which actually shows the vending machine page on it's own, as well as with all the buttons and each of the products with their panels

         mainFrame.getContentPane().removeAll();
         mainFrame.setLayout(new BorderLayout());
         //Resets the main frame, and then sets up a new panel for itself

         JPanel productShowcase = new JPanel();
         productShowcase.setLayout(new GridLayout((int) Math.ceil(objAmount/2), 2, 450, 30));
         productShowcase.setBackground(new Color(220, 237, 250));
         productShowcase.setBorder(BorderFactory.createEmptyBorder(25, 50, 50, 25));
         //Sets up a panel where all the products are going to be displayed

        for(VendingItems products : vendingProducts){
            productShowcase.add(products.createPanel());
        }
        //For each of the products that will be displayed, add a separate panel for each of them using a for loop

        mainFrame.add(productShowcase, BorderLayout.CENTER);
        //Adds the panel with all the products on it

        JPanel mainButtonPanel = new JPanel(new BorderLayout());
        JButton resetButton = new JButton("Re-Stock; Set New Day");
        mainButtonPanel.add(resetButton, BorderLayout.WEST);
        //Creates another panel for the buttons at the bottom of the screen to clean it up

        resetButton.addActionListener(resetEvent ->{
            for(VendingItems products : vendingProducts){
                products.resetDay();
            }
        });
        //When the reset button is pressed, activate the resetDay method on the vending class for each product (Resets stock)

        JPanel passwordSide = new JPanel();
        passwordSide.setLayout(new BoxLayout(passwordSide, BoxLayout.Y_AXIS));
        //Then creates another even smaller panel for the password button and textfield 
        
        JPasswordField operatorPassword = new JPasswordField(1);
        operatorPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        operatorPassword.setMaximumSize(new Dimension(250, 20));
        operatorPassword.setHorizontalAlignment(JTextField.CENTER);
        //Creates a password field, similar to a textfield but hides the string and is more secure

        JButton operatorButton = new JButton("Enter Password to See Operator Panel");
        operatorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Creates the button which checks if the password is right, and if so, to allow to operator to the operator page

        passwordSide.add(operatorButton);
        passwordSide.add(Box.createVerticalStrut(15));
        passwordSide.add(operatorPassword);
        //Adds both the password field as well as the button the submit it to the password panel

        mainButtonPanel.add(passwordSide, BorderLayout.EAST);
        //Adds the password panel to the panel for the buttons at the bottom
        
        operatorButton.addActionListener(operatorEvent -> {
            //THE PASSWORD IS 'password', all lowercase
            String passwordChecker = new String(operatorPassword.getPassword());
            if(passwordChecker.equals("password")){
                try{
                    activateOperatorpanel(mainFrame);
                }catch(Exception IOException){
                    JOptionPane.showMessageDialog(mainFrame, "Recipt File Unloaded", "File Loading Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(mainFrame, "Password is Incorrect", "Incorrect Password", JOptionPane.ERROR_MESSAGE);
            }
        });
        //On press, the password button checks if the strings are the same, and if so to execute the operator panel method, and if not, to show an error message

        mainButtonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        mainFrame.add(mainButtonPanel, BorderLayout.SOUTH);

        mainFrame.revalidate();
        mainFrame.repaint();
        //Adds everything to the main frame, then asks the main frame to update
    }

    public static void startAddproducts(JFrame vendingWindow, int index){
        addproducts(vendingWindow, index);
        //This method starts the adding products method, and is necessary as they call each other to start.
        //Using a 'for-loop' often caused bugs during testing, this way was much more optimal without any glitches or breaks 
    }

    public static void addproducts(JFrame mainFrame, int index){
        //This method adds each individual product, by asking one by one the name, then price, then quantity for every single product

        if(index >= objAmount){
            JOptionPane.showMessageDialog(mainFrame, "All Vending Products added!");
            showVendingMachine(mainFrame);
            return;
        }
        //Checks if the index/counter is higher than the desired amount of times to loop, which is the amount of products objAmount

        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setLayout(new BorderLayout());
        //Resets the main frame, and sets up a new layout to start to ask for the name of the product

        JTextField nameAsk = new JTextField(10); 
        JButton nameEnter = new JButton("Enter");
        JPanel nameaskPanel = createInputPanel("What's the name of product " + (index + 1) + "?: ", nameAsk, nameEnter);
        //This is all the components which asks the user for the name of the products that they're adding
        //Creates the text area, the button, and the panel, then creates it using the createInputPanel method

        mainFrame.getContentPane().add(nameaskPanel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
        //Asks the main frame to update itself
  
        nameEnter.addActionListener(secondevent -> {
            //Once the user submits the name, saves the name and moves to the next step

            String productnewName = nameAsk.getText();
            mainFrame.getContentPane().removeAll();
            //Resets the main frame again, sets up the next step to ask for price

            JTextField priceAsk = new JTextField(10); 
            JButton priceEnter = new JButton("Enter");
            JPanel priceAskPanel = createInputPanel("What's the price of product " + (index + 1) + "?: ", priceAsk, priceEnter);
            //Creates all the components again, this time to ask for the price of the products they're making, using the createInputPanel method
    
            mainFrame.getContentPane().add(priceAskPanel, BorderLayout.CENTER);
            mainFrame.revalidate();
            mainFrame.repaint();
            //Asks the main frame to update itself

            priceEnter.addActionListener(thirdevent -> {
                //Once the user submits the price, moves on to the next step
                
                String priceResult = priceAsk.getText();
                double[] pricehelp = new double[1];
                //A normal double won't work, as it must be declared final, but we need the value of the double to change
                //So as a work-around, an array for a double is used, with only 1 slot
                try{
                    pricehelp[0] = Double.parseDouble(priceResult);
                    if(pricehelp[0] > 0 && pricehelp[0] < 1000000){
                        mainFrame.getContentPane().removeAll();
                        //Resets the main frame, and checks if the price is valid (Between 1 and 1 million). If so, save it, then move onto to the next step

                        JTextField quantityAsk = new JTextField(10); 
                        JButton quantityEnter = new JButton("Enter");
                        JPanel quantityAskPanel = createInputPanel("What's the quantity of product " + (index + 1) +"?: ", quantityAsk, quantityEnter);
                        //Creates all the components needed to ask for the quantity of a product, same method as before

                        mainFrame.getContentPane().add(quantityAskPanel, BorderLayout.CENTER);
                        mainFrame.revalidate();
                        mainFrame.repaint();
                        //Asks the main frame to update itself

                        quantityEnter.addActionListener(fourthevent -> {
                            //Once the user presses submit for the quantity in the textbox, move on to the last step
                            String quantityResult = quantityAsk.getText();
                            int quantity = 0;
                            try{
                                quantity = Integer.parseInt(quantityResult);
                                //Double checks if quantity is a valid number (Between 1 and 1 million), if so do the final step
                                if(quantity > 0 && quantity < 1000000){
                                    mainFrame.getContentPane().removeAll();    
                                    mainFrame.revalidate();
                                    mainFrame.repaint();

                                    //Once all the information has been gathered from the user, inputs it into the method which
                                    //actually creates the product, as an object from the vendingitems class
                                    createVendingObj(productnewName, pricehelp[0], quantity, mainFrame);
                                    addproducts(mainFrame, (index + 1));
                                }else{
                                    JOptionPane.showMessageDialog(mainFrame, "Please enter a number greater than 0 and less than 1 million!", "Input Error", JOptionPane.ERROR_MESSAGE);
                                    //Error catching, if the user doesn't input a number within the bounds
                                }
                            }catch(NumberFormatException error){
                                JOptionPane.showMessageDialog(mainFrame, "Please enter a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
                                //Makes sure that the user doesn't just submit it blank
                            }
                        });
                    }else{
                        JOptionPane.showMessageDialog(mainFrame, "Please enter a number greater than 0 and less than 1 million!", "Input Error", JOptionPane.ERROR_MESSAGE);
                        //Another error catching part, makes sure it's within the bounds
                    }
                }catch(NumberFormatException error){
                    JOptionPane.showMessageDialog(mainFrame, "Please enter a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    //Makes sure the user doesn't submit the text box blank again
                }
            });
        });
    }

    public static void createVendingObj(String name, double price, int quantity, JFrame mainFrame){
        //This is the method which actually creates the products/objects from the class vendingitems
        
        VendingItems product = new VendingItems();
        product.vendingObj(name, price, quantity);
        //Creates it, inputting name, price, and quantity

        vendingProducts.add(product);
        //Puts it into the original arraylist at the top for each product
    }

    public static JPanel createInputPanel(String textLine, JTextField objAsk, JButton objEnter){
        //This is the method for creating the input panels in the beginning when asking the user for their products
        JPanel objAskPanel = new JPanel();
        objAskPanel.setBackground(new Color(209, 236, 255));
        objAskPanel.setLayout(new BoxLayout(objAskPanel, BoxLayout.Y_AXIS));
        objAskPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        //Actually creates the panels themselves, as well as with padding, colour, format
        
        JLabel objLabel = new JLabel(textLine);
        objLabel.setMaximumSize(new Dimension(400, 20));
        objLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        objLabel.setFont(new Font("Cambria", Font.PLAIN, 16));
        //Creates the label, set to Cambria and size 16

        objAsk.setMaximumSize(new Dimension(400, 20));
        objAsk.setAlignmentX(Component.CENTER_ALIGNMENT);
        objAsk.setHorizontalAlignment(JTextField.CENTER);
        //Creates the textbox for the input panel
                
        objEnter.setMaximumSize(new Dimension(80, 20));
        objEnter.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Creates the button for the input panel
                
        objAskPanel.add(objLabel);
        objAskPanel.add(Box.createVerticalStrut(15));
        objAskPanel.add(objAsk);
        objAskPanel.add(Box.createVerticalStrut(10));
        objAskPanel.add(objEnter);
        //Adds all of it to the main panel, then returns it

        return objAskPanel;
    }
}

