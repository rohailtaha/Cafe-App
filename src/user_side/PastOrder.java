package user_side;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/** A Node In The PastOrders List **/
public class PastOrder {
	// KEY : Time At Which Order Was Made
	String orderTime;
	String orderStatus;
	String orderId;
	int cafeID;
	// Total Cost Of The Order
	double totalCost;
	// Delivery Address
	String address;
	// left child
	PastOrder next;
	// List Of All Ordered Items(CartItems)
	private CartItem root_OrderedItems;
	private CartItem tail_orderedItems;

	
	public PastOrder(String orderId, String orderTime, int cafeID, String address, double totalCost, String orderStatus) {
		this.orderTime = orderTime;
		this.orderId = orderId;
		this.cafeID = cafeID;
		this.totalCost = totalCost;
		this.address = address;
		this.orderStatus = orderStatus;
	}

	
	/** Load All The Ordered Items Of A Particular Order **/
	void loadOrderedItems(String orderId) {
		try {
			FileReader fr = new FileReader("orderedItems.csv");
			Scanner inFile = new Scanner(fr);
			while (inFile.hasNext()) {
				// Read the next line.
				String row = inFile.nextLine();
				// split the line and store the individual fields of the row in an array.
				// Index : Value
				// 1 : ItemId (int)
				// 2 : name (String)
				// 3 : quantity (int)
				// 4 : totalCost (double)
				String[] fields = row.split(",");
				// if orderId matches
				if (fields[0].equals(orderId)) {
					CartItem orderedItem = new CartItem(Integer.parseInt(fields[1]), fields[2],
							Integer.parseInt(fields[3]), Double.parseDouble(fields[4]));
					this.addItem(orderedItem);
				}
			}
			inFile.close();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	void addItems(CartItem cartItem) {
		while(cartItem != null) {
			this.addItem(cartItem);
			cartItem = cartItem.next;
		}
	}
	
	private void addItem(CartItem orderedItem) {
		if (root_OrderedItems == null) {
			root_OrderedItems = tail_orderedItems = orderedItem;
			return;
		}
		tail_orderedItems.next = orderedItem;
		tail_orderedItems = tail_orderedItems.next;
	}

	void printItems() {
		CartItem temp = root_OrderedItems;
		System.out.println("Items Ordered:");
		System.out.printf("%-35s%-10s%s%n", "Item Name", "Quantity", "Cost");
		while (temp != null) {
			System.out.printf("%-35s%-10d%.1f%n", temp.name, temp.quantity, temp.totalCost);
			temp = temp.next;
		}
	}
	
	String getOrderStatus() {
		if(this.orderStatus.equals("0")) return "PENDING";
		else if(this.orderStatus.equals("1")) return "ACCEPTED";
		else return "DELIVERED";
	}

}
