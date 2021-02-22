package user_side;

/* Implements A Linked List For All Items Added To Cart(CartItems) */
public class Cart {
	// Cafe ID To Which The Cart Belongs
	int cafeID;
	// First CartItem
	private CartItem root_cartItem;
	// Last CartItem
	private CartItem tail_cartItem;
	// Total Cost Of All Items Added To Cart
	double totalCost = 0;

	Cart(int cafeID) {
		this.root_cartItem = this.tail_cartItem = null;
		this.totalCost = 0;
		this.cafeID = cafeID;
	}

	void addItem(CartItem cartItem) {
		// Add the cost of current cart item to total cost.
		this.totalCost += cartItem.totalCost;
		if (root_cartItem == null) {
			root_cartItem = tail_cartItem = cartItem;
			return;
		}
		tail_cartItem.next = cartItem;
		tail_cartItem = tail_cartItem.next;
	}

	void removeItem(int id) {
		// If cart is empty
		if (isEmpty()) {
			System.out.println("Cart is empty.");
			return;
		}
		
		CartItem temp = root_cartItem;
		CartItem prev = root_cartItem;
		while (temp != null && temp.itemID != id) {
			prev = temp;
			temp = temp.next;
		}
		
		prev.next = temp.next;
		// If only one item in cart
		if (temp == tail_cartItem && temp == root_cartItem)
			root_cartItem = tail_cartItem = null;
		// If first item in list is to be deleted.
		else if (temp == root_cartItem)
			root_cartItem = root_cartItem.next;
		// If last item in list is to be deleted.
		else if (temp == tail_cartItem)
			tail_cartItem = prev;
	}

	void printItems() {
		// If cart is empty
		if (isEmpty()) {
			System.out.println("Cart is empty.");
		}
		System.out.printf("%-9s%-35s%-17s%12s%n", "Item ID", "Item Name", "Quantity Ordered", "Total Cost");
		CartItem temp = root_cartItem;
		while (temp != null) {
			System.out.printf("%-9d%-35s%-17d%12.1f%n", temp.itemID, temp.name, temp.quantity, temp.totalCost);
			temp = temp.next;
		}
	}

	
	boolean hasItem(int itemID) {
		if (isEmpty()) {
			return false;
		}
		CartItem temp = root_cartItem;
		while (temp != null && temp.itemID != itemID) {
			temp = temp.next;
		}
		return temp == null ? false : true;
	}

	
	/** Get The List Of All CartItem's **/
	CartItem getCartItemList() {
		return this.root_cartItem;
	}

	
	boolean isEmpty() {
		return this.root_cartItem == null;
	}

	
	void empty() {
		this.root_cartItem = this.tail_cartItem = null;
	}
}
