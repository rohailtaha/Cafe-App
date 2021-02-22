package user_side;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Order {
	// Order Details
	private OrderDetails orderDetails;
	// Order Status : EITHER  0(PENDING), 1(ACCEPTED) OR 2(DELIVERED)
	private int orderStatus;
	String orderID;

	
	/** Construct Order **/
	Order(Cart cart, User user) {
		// To format the date in specific format
		DateTimeFormatter date_time_formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		// Format the current date in format specified above
		String orderTime = date_time_formatter.format(LocalDateTime.now());
		// Get a unique order ID
		this.orderID = CommonFunctionalities.getId2();
		
		this.orderDetails = new OrderDetails(this.orderID, cart.cafeID, cart.totalCost, orderTime, user.username,
				user.phoneNumber, this.getAddress(), cart.getCartItemList());
		// set order status to pending
		this.orderStatus = 0;
		addAsPastOrder(cart, user);
		this.saveOrderDetails();
	}

	
	private String getAddress() {
		System.out.printf("Enter Address Of Delivery: ");
		return new Scanner(System.in).nextLine();
	}
	
	
	private void addAsPastOrder(Cart cart, User user) {
		// save the order as a past order
		PastOrder pastOrder = new PastOrder(this.orderDetails.orderId, this.orderDetails.orderTime, this.orderDetails.cafeID,
				this.orderDetails.address, this.orderDetails.totalCost, "0");
		pastOrder.addItems(cart.getCartItemList());
		user.getPastOrders().add(pastOrder);
	}
	
	
	/** Save The Order Details In The "orderDetails.csv" File **/
	private void saveOrderDetails() {
		try {
			FileWriter fw = new FileWriter("orderDetails.csv", true);
			PrintWriter out = new PrintWriter(fw);
			// Write the record with "," as a delimiter.
			out.println(this.orderDetails.orderId + "," + this.orderDetails.cafeID + "," + this.orderDetails.username
					+ "," + this.orderDetails.phoneNumber + "," + this.orderDetails.address + ","
					+ this.orderDetails.orderTime + "," + this.orderDetails.totalCost + "," + this.orderStatus);
			out.close();
			// Save all the ordered Items for this order in a separate file. OrderId will link both files.
			saveOrderedItems();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	
	/** Save The Ordered Items In The "orderedItems.csv" File **/
	private void saveOrderedItems() {
		try {
			FileWriter fw = new FileWriter("orderedItems.csv", true);
			PrintWriter out = new PrintWriter(fw);
			CartItem temp = this.orderDetails.root_OrderedItems;
			while (temp != null) {
				// Write the record with "," as a delimiter.
				out.println(this.orderDetails.orderId + "," + temp.itemID + "," + temp.name + "," + temp.quantity + ","
						+ temp.totalCost);
				temp = temp.next;
			}
			out.close();
			
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	
	OrderDetails getOrderDetails() {
		return this.orderDetails;
	}
	
	
	int getStatus() {
		return this.orderStatus;
	}
	
	
	void setStatus(int status) {
		this.orderStatus = status;
	}

	
	static void readOrderDetails() {
		try {
			FileReader fr = new FileReader("orderDetails.csv");
			Scanner inFile = new Scanner(fr);
			// Until End Of File(EOF) is reached
			while(inFile.hasNext()) {
				// Read the next line.
	            String row = inFile.nextLine();
	         // split the row and store the individual fields of the row in an array.
	            // Index : Value
	            //     0 : orderId (int)
	            //	   1 : cafeId (int)
	            //	   2 : username (String)
	            //	   3 : phoneNumber (String)
	            //	   4 : address (String)
	            //	   5 : orderTime (String)
	            //	   6 : totalCost (double)
	            //     7 : orderStatus
	            String[] fields = row.split(",");
	            // Display the row.
	            System.out.println(fields[0] + "	 " +  fields[1] + "   " + fields[2] + "   " + fields[3] + "   " + fields[4] + "	  " + 
	            		fields[5] + "   " + fields[6] + "   " + fields[7]);
	            // read the ordered items for this order from a separate file. Use OrderId to get items only for this order.
	            readOrderedItems(Integer.parseInt(fields[0]));
			}
			
			inFile.close();
			
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	private static void readOrderedItems(int orderId) {
		try {
			FileReader fr = new FileReader("orderedItems.csv");
			Scanner inFile = new Scanner(fr);
			System.out.println("Ordered Items for order id = " + orderId + " are:");
			while (inFile.hasNext()) {
				// Read the next line.
				String row = inFile.nextLine();
				// split the line and store the individual fields of the row in an array.
				// Index : Value
	            //     0 : orderId (int)
	            //	   1 : ItemId (int)
	            //	   2 : name (String)
	            //	   3 : quantity (int)
	            //	   4 : totalCost (double)
	            String[] fields = row.split(",");
	            // if orderId matches, only then display.
	            if(Integer.parseInt(fields[0])== orderId) {
	            	System.out.println(fields[0] + "	 " +  fields[1] + "   " + fields[2] + "   " + fields[3] + "   " + fields[4]);
	            }
			}
			inFile.close();
			System.out.println();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

}
