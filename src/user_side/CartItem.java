package user_side;

public class CartItem {
	int itemID;
	String name;
	int quantity;
	double totalCost;
	// Next CartItem In Linked List For Cart
	CartItem next;

	// CartItem Constructor
	CartItem(int id, String name, double cost, int quantity) {
		this.itemID = id;
		this.name = name;
		this.quantity = quantity;
		// Calculate total cost by multiplying quantity with cost of one item
		this.totalCost = quantity * cost;
		this.next = null;
	}

	// CartItem Constructor
	CartItem(int id, String name, int quantity, double totalCost) {
		this.itemID = id;
		this.name = name;
		this.quantity = quantity;
		// Calculate total cost by multiplying quantity with cost of one item
		this.totalCost = totalCost;
		this.next = null;
	}
}
