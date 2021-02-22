package user_side;


public class OrderDetails {
	String orderId;
	int cafeID;
	double totalCost;
	String orderTime;
	String username;
	String phoneNumber;
	// Delivery Address
	String address;
	// List Of All Ordered Items(CartItems)
	CartItem root_OrderedItems;

	public OrderDetails(String orderId, int cafeID, double totalCost, String orderTime, String username,
			String phoneNumber, String address, CartItem root_CartItem) {
		this.orderId = orderId;
		this.cafeID = cafeID;
		this.totalCost = totalCost;
		this.orderTime = orderTime;
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.root_OrderedItems = root_CartItem;

	}
}
