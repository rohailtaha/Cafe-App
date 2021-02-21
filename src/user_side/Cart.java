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
		root_cartItem = tail_cartItem = null;
		totalCost = 0;
		this.cafeID= cafeID;
	}
	
	
	void addItem(CartItem cartItem) {
		// Add the cost of current cart item to total cost.
		this.totalCost += cartItem.totalCost;
		if(root_cartItem == null) {
			root_cartItem = tail_cartItem = cartItem;
			return;
		}
		tail_cartItem.next = cartItem; 
		tail_cartItem = tail_cartItem.next;
	}
	
	
	void removeItem(int id) {
		CartItem temp = root_cartItem;
		CartItem prev = root_cartItem;
		while(temp!=null && temp.itemID != id) {
			prev = temp;
			temp = temp.next;
		}
		// If cart is empty
		if(isEmpty()) {
			System.out.println("Item was not found in the cart");
			return;
		}
		prev.next = temp.next;
		// If only one item in cart
		if(temp==tail_cartItem && temp == root_cartItem) root_cartItem = tail_cartItem = null;
		// If first item in list is to be deleted.
		else if(temp==root_cartItem) root_cartItem = root_cartItem.next;
		// If last item in list is to be deleted.
		else if(temp==tail_cartItem) tail_cartItem = prev;
	}
	
	
	/** Print All Items Added To Cart **/
	void printItems() {
		// If cart is empty
		if(isEmpty()) {
			System.out.println("Cart is Empty.");
		}
		CartItem temp = root_cartItem;
		System.out.printf("%-9s%-35s%-17s%12s%n","Item ID", "Item Name", "Quantity Ordered", "Total Cost");
		while(temp!=null) {
			System.out.printf("%-9d%-35s%-17d%12.1f%n",temp.itemID, temp.name, temp.quantity, temp.totalCost);
			temp = temp.next;
		}
	}
	
	
	/** Check If An Item Exists In Cart Whose ID Is Passed As Parameter **/
	boolean hasItem(int itemID) {
		if(isEmpty()) {
			return false;
		}
		CartItem temp = root_cartItem;
		while(temp!=null && temp.itemID != itemID) {
			temp = temp.next;
		}
		if(temp == null) return false;
		else return true;
	}
	
	
	/** Get The List Of All CartItem's  **/
	CartItem getCartItemList() {
		return root_cartItem;
	}
	
	
	/** Checks If The Cart Is Empty **/
	boolean isEmpty() {
		return root_cartItem == null;
	}
	
	
	/** Empty Cart **/
	void empty() {
		root_cartItem = tail_cartItem = null;
	}
}
