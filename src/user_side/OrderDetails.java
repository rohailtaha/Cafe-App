package user_side;

import java.time.LocalDateTime;

public class OrderDetails {
	String orderId;
	int cafeID;
	// Total Cost Of The Order
	double totalCost;	
	// Time At Which Order Was Made
	String orderTime;
	String username;
	String phoneNumber;
	// Delivery Address
	String address;
	// List Of All Ordered Items(CartItems)
	CartItem root_OrderedItems;

	// Construct Order Details
	public OrderDetails(String orderId, int cafeID, double totalCost, String orderTime, String username, String phoneNumber, String address,
			CartItem root_CartItem) {
		this.orderId = orderId;
		this.cafeID = cafeID;
		this.totalCost = totalCost;
		this.orderTime = orderTime;
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.root_OrderedItems = root_CartItem;
		
		PastOrder pastOrder = new PastOrder(this.orderId, this.orderTime, this.cafeID, this.address, this.totalCost,
				"0");
	}
}
