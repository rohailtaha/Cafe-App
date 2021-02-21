package user_side;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/** A Linked List(Stack) Implementation Of All Past Orders Of A User **/
public class PastOrders {

	// username whose past orders are to be fetched
	private String username;
	
	// List Of All Orders In Descending Order Of Order Time 
	private PastOrder head;

	
	PastOrders(String username) {
		this.username = username;
		this.loadPastOrders();
	}

	
	/** Load All The Past Orders Of The User In A Tree **/
	private void loadPastOrders() {
		try {
			FileReader fr = new FileReader("orderDetails.csv");
			Scanner inFile = new Scanner(fr);
			// Until End Of File(EOF) is reached
			while (inFile.hasNext()) {
				// Read the next line.
				String row = inFile.nextLine();
				// split the row and store the individual fields of the row in an array.
				// Index : Value
				// 0 : orderId (int)
				// 1 : cafeId (int)
				// 4 : address (String)
				// 5 : orderTime (String)
				// 6 : totalCost (double)
				// 7 : orderStatus
				String[] fields = row.split(",");
				//
				if (fields[2].equals(this.username)) {
					PastOrder pastOrder = new PastOrder(fields[0], fields[5], Integer.parseInt(fields[1]), fields[4],
							Double.parseDouble(fields[6]), fields[7]);
					pastOrder.loadOrderedItems(fields[0]);
					this.add(pastOrder);
				}
			}
			inFile.close();

		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	
	void add(PastOrder pastOrder) {
		if (head == null) {
			head = pastOrder;
			return;
		} 
		pastOrder.next = head;
		head = pastOrder;
	}
	
	
	void print() {
		if (head == null)
			return;
		PastOrder temp = head;
		while(temp!=null) {
			System.out.printf("\nOrder ID: %-14sOrder Time: %-21sOrder Status: %-13sCost: %-9sDelivery Address: %s%n",
					temp.orderId, temp.orderTime, temp.getOrderStatus(), temp.totalCost, temp.address);
			// print all ordered items for this order.
			temp.printItems();
			temp = temp.next;
		}
	}

}
