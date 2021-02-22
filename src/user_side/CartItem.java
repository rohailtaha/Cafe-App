package user_side;

public class CartItem {
	int itemID;
	String name;
	int quantity;
	// Total Cost Calculated By Multiplying Quantity With Cost of One Item
	double totalCost;
	// Next CartItem
	CartItem next;

	CartItem(int id, String name, double cost, int quantity) {
		this.itemID = id;
		this.name = name;
		this.quantity = quantity;
		
		this.totalCost = quantity * cost;
		this.next = null;
	}

	CartItem(int id, String name, int quantity, double totalCost) {
		this.itemID = id;
		this.name = name;
		this.quantity = quantity;
		this.totalCost = totalCost;
		this.next = null;
	}
}
