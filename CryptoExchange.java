
/**
 * Author: Madison Pettipas
 * Description: Reads an input file and simulates cryptocurrency exchange by
 *              reading commands and info from the file. Includes methods to
 *              insert, remove, display, execute, change, and cancel buy 
 *              and sell orders.
 */
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
public class CryptoExchange {

    /** Void method with parameters a String, Scanner, MaxHeap, and MinHeap.
     * Determines what method to call based on the given String parameter */
    public static void chooseMethod(String method, Scanner scnr,
                        MaxHeap buyerHeap, MinHeap sellerHeap) {
        switch(method) {
            case ("EnterBuyOrder"): //calls ExecuteBSO after every insertion or removal
                EnterBuyOrder(scnr, buyerHeap);
                ExecuteBuySellOrders(buyerHeap, sellerHeap);
                break;
            case ("EnterSellOrder"):
                EnterSellOrder(scnr, sellerHeap);
                ExecuteBuySellOrders(buyerHeap, sellerHeap);
                break;
            case ("DisplayHighestBuyOrder"):
                DisplayHighestBuyOrder(scnr, buyerHeap);
                break;
            case ("DisplayLowestSellOrder"):
                DisplayLowestSellOrder(scnr, sellerHeap);
                break;
            case ("ChangeBuyOrder"):
                ChangeBuyOrder(scnr, buyerHeap);
                ExecuteBuySellOrders(buyerHeap, sellerHeap);
                break;
            case ("ChangeSellOrder"):
                ChangeSellOrder(scnr, sellerHeap);
                ExecuteBuySellOrders(buyerHeap, sellerHeap);
                break;
            case ("CancelBuyOrder"):
                CancelBuyOrder(scnr, buyerHeap);
                ExecuteBuySellOrders(buyerHeap, sellerHeap);
                break;
            case ("CancelSellOrder"):
                CancelSellOrder(scnr, sellerHeap);
                ExecuteBuySellOrders(buyerHeap, sellerHeap);
                break;
        }
    }
    
    /** Void method with parameters a Scanner and MaxHeap. Reads the given
     * information and saves it to variables. Calls MaxHeap's method change
     * to search for a Node that shares the given name, then if found, updates 
     * the order to match the new information. */
    public static void ChangeBuyOrder(Scanner scnr, MaxHeap buyers) {
        String time = scnr.next();
        String buyer = scnr.next();
        double price = scnr.nextDouble();
        int quantity = scnr.nextInt();
        
        String p; //for formatting
        if (price == Math.floor(price)) p = String.format("%.0f", price);
        else p = String.format("%.2f", price);
        
        boolean b = buyers.change(buyer, time, price, quantity);
        if (b == false) { //if the buyer wasnt found
            System.out.printf("ChangeBuyOrder %s %s %s %d noBuyerError\n", time, buyer, p, quantity);
            return;
        }
        System.out.printf("ChangeBuyOrder %s %s %s %d\n", time, buyer, p, quantity);
    }
    
    /** Void method with parameters a Scanner and MinHeap. Reads the given
     * information and saves it to variables. Calls MinHeap's method change
     * to search for a Node that shares the given name, then if found, updates 
     * the order to match the new information. */
    public static void ChangeSellOrder(Scanner scnr, MinHeap sellers) {
        String time = scnr.next();
        String seller = scnr.next();
        double price = scnr.nextDouble();
        int quantity = scnr.nextInt();
        
        String p; //for formatting
        if (price == Math.floor(price)) p = String.format("%.0f", price);
        else p = String.format("%.2f", price);
        
        boolean b = sellers.change(seller, time, price, quantity);
        if (b == false) { //if the seller wasnt found
            System.out.printf("ChangeSellOrder %s %s %s %d noSellerError\n", time, seller, p, quantity);
            return;
        }
        
        System.out.printf("ChangeSellOrder %s %s %s %d\n", time, seller, p, quantity);
    }
    
    /** Void method with parameters a Scanner and MaxHeap. Reads the given
     * time and name, then calls MaxHeap's cancel method to search for a 
     * Node that shares the name, then if found, cancels the order by 
     * removing it. */
    public static void CancelBuyOrder(Scanner scnr, MaxHeap buyers) {
        String time = scnr.next();
        String buyer = scnr.next();
        
        boolean b = buyers.cancel(buyer);
        if (b == false) { //if the buyer wasnt found
            System.out.println("CancelBuyOrder " + time +" "+ buyer + " noBuyerError");
            return;
        }
        System.out.println("CancelBuyOrder " + time +" "+ buyer);
    }
    
    /** Void method with parameters a Scanner and MinHeap. Reads the given
     * time and name, then calls MinHeap's cancel method to search for a 
     * Node that shares the name, then if found, cancels the order by 
     * removing it. */
    public static void CancelSellOrder(Scanner scnr, MinHeap sellers) {
        String time = scnr.next();
        String seller = scnr.next();
        
        boolean b = sellers.cancel(seller);
        if (b == false) { //if the seller wasnt found
            System.out.println("CancelSellOrder " + time +" "+ seller + " noSellerError");
            return;
        }
        System.out.println("CancelSellOrder " + time +" "+ seller);
    }
    
    /** Void method with parameters a Scanner and MaxHeap. Reads a buyer's
     * given information and inserts their order into the MaxHeap, then 
     * prints the given information */
    public static void EnterBuyOrder(Scanner scnr, MaxHeap buyers) {
        String time = scnr.next();
        String buyer = scnr.next();
        double price = scnr.nextDouble();
        int quantity = scnr.nextInt();
        //create Node to hold all info, then add Node to heap
        MaxHeap.Node buyOrder = buyers.new Node(buyer, price, time, quantity);
        buyers.insert(buyOrder);
        
        String p; //for formatting
        if (price == Math.floor(price)) p = String.format("%.0f", price);
        else p = String.format("%.2f", price);
        System.out.printf("EnterBuyOrder %s %s %s %d\n", time, buyer, p, quantity);
    }
    
    /** Void method with parameters a Scanner and MinHeap. Reads a seller's
     * given information and inserts their order into the MinHeap, then 
     * prints the given information */
    public static void EnterSellOrder(Scanner scnr, MinHeap sellers) {
        String time = scnr.next();
        String seller = scnr.next();
        double price = scnr.nextDouble();
        int quantity = scnr.nextInt();
        //create Node to hold all info, then add Node to heap
        MinHeap.Node sellOrder = sellers.new Node(seller, price, time, quantity);
        sellers.insert(sellOrder);
        
        String p; //for formatting
        if (price == Math.floor(price)) p = String.format("%.0f", price);
        else p = String.format("%.2f", price);
        System.out.printf("EnterSellOrder %s %s %s %d\n", time, seller, p, quantity);
    }
    
    /** Void method with parameters a Scanner and MaxHeap. Saves all info
     * from the buy order at the root of the heap into variables, then prints */
    public static void DisplayHighestBuyOrder(Scanner scnr, MaxHeap buyers) {
        String time = scnr.next();
        
        if (buyers.size == 0) { //if heap is empty
            System.out.println("DisplayHighestBuyOrder " + time + " none");
            return;
        }
        
        MaxHeap.Node buyOrder = buyers.getMax();
        String buyer = buyOrder.buyer;
        String orderTime = buyOrder.time;
        double price = buyOrder.price;
        int quantity = buyOrder.quantity;
        
        String p; //for formatting
        if (price == Math.floor(price)) p = String.format("%.0f", price);
        else p = String.format("%.2f", price);
        System.out.printf("DisplayHighestBuyOrder %s %s %s %s %d\n", time, buyer, orderTime, p, quantity);
    }
    
    /** Void method with parameters a Scanner and MaxHeap. Saves all info
     * from the buy order at the root of the heap into variables, then prints */
    public static void DisplayLowestSellOrder(Scanner scnr, MinHeap sellers) {
        String time = scnr.next();
        
        if (sellers.size == 0) { //if heap is empty
            System.out.println("DisplayLowestSellOrder " + time + " none");
            return;
        }
        
        MinHeap.Node sellOrder = sellers.getMin();
        String seller = sellOrder.seller;
        String orderTime = sellOrder.time;
        double price = sellOrder.price;
        int quantity = sellOrder.quantity;
        
        String p; //for formatting
        if (price == Math.floor(price)) p = String.format("%.0f", price);
        else p = String.format("%.2f", price);
        System.out.printf("DisplayLowestSellOrder %s %s %s %s %d\n", time, seller, orderTime, p, quantity);
    }
    
    /** Void method with parameters a MaxHeap and MinHeap. Is called every
     * time a new Node is inserted or removed from either heap. If at any
     * time the max buy price is equal to or greater than the smallest
     * seller price, a purchase is simulated. */
    public static void ExecuteBuySellOrders(MaxHeap buyers, MinHeap sellers) {
        //while the heaps arent empty & buyers max is >= sellers min
        while (buyers.size > 0 && sellers.size > 0 &&
                buyers.getMaxValue() >= sellers.getMinValue()) {
            MaxHeap.Node maxBuyer = buyers.removeMax();
            MinHeap.Node minSeller = sellers.removeMin();
            
            double price = maxBuyer.price;
            if (price > minSeller.price) { //averages prices if they arent equal
                price = (price + minSeller.price) / 2;
            }
            
            int buyerQuan = maxBuyer.quantity;
            int sellerQuan = minSeller.quantity;
            int remainingBuyQuan = 0;
            int remainingSellQuan = 0;
            int lowerQuan = buyerQuan;
            //check for remaining quantity
            if (buyerQuan < sellerQuan) { 
                remainingSellQuan = sellerQuan-buyerQuan;
                
                MinHeap.Node updatedSellOrder = sellers.new Node(minSeller.seller, 
                minSeller.price, minSeller.time, remainingSellQuan);
                sellers.insert(updatedSellOrder);
            } else if (buyerQuan > sellerQuan) {
                lowerQuan = sellerQuan;
                remainingBuyQuan = buyerQuan-sellerQuan;
                
                MaxHeap.Node updatedBuyOrder = buyers.new Node(maxBuyer.buyer, 
                maxBuyer.price, maxBuyer.time, remainingBuyQuan);
                buyers.insert(updatedBuyOrder);
            }
            
            String p; //for formatting
            if (price == Math.floor(price)) p = String.format("%.0f", price);
            else p = String.format("%.2f", price);
            System.out.printf("ExecuteBuySellOrders %s %d\n", p, lowerQuan);
            System.out.println("Buyer: " + maxBuyer.buyer +" "+ remainingBuyQuan);
            System.out.println("Seller: " + minSeller.seller +" "+ remainingSellQuan);
        }
    }
    
    /** Main void method that may throw a FileNotFoundException. Reads the
     * input file, calling the correct methods and saving information based
     * on what is written in the file. */
    public static void main(String[]args) throws FileNotFoundException {
        if (args.length != 1 || !args[0].endsWith(".txt")) { //makes sure exactly one .txt file was entered
            System.out.println("Input Error");
            return;
        }
        
        File inFile = new File(args[0]);
        Scanner scnr = new Scanner(inFile);
        //initiaize heaps
        MaxHeap buyerHeap = new MaxHeap();
        MinHeap sellerHeap = new MinHeap();
        //read file + call correct methods
        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            if (!line.isEmpty()) {
                Scanner lineScnr = new Scanner(line);
                String method = lineScnr.next(); //save first String as method
                chooseMethod(method, lineScnr, buyerHeap, sellerHeap);
            }
        }
        System.out.println(); //to match expected output
    }
}
