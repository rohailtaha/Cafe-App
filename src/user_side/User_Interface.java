package user_side;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class User_Interface {

	private static User user;
	private static Cart cart;

	static void display() {
//		while (!authenticate()) {
//			System.out.println("Invalid Login\n");
//		}
//		System.out.println("You are Logged In");
		user = UserTable.getUser("nasir77", "nmabc");
		showMainMenu();
	}

	/**
	 * Login The User.
	 * 
	 * @return: User Object If Login Is Successfull, Null If No Such User Was Found
	 **/
	private static User login() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nEnter Username: ");
		String username = sc.nextLine();
		System.out.print("Enter Password: ");
		String password = sc.nextLine();
		return UserTable.getUser(username, password);
	}

	/**
	 * Signup A New User
	 * 
	 * @return: The New User
	 **/
	private static User signUp() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nSelect a Username: ");
		String username = sc.nextLine();
		System.out.print("Select a Password: ");
		String password = sc.nextLine();
		System.out.print("Full name: ");
		String name = sc.nextLine();
		System.out.print("Email: ");
		String email = sc.nextLine();
		System.out.print("Phone Number: ");
		String phoneNumber = sc.nextLine();
		User newUser = new User(name, username, password, email, phoneNumber);
		UserTable.saveNewUser(newUser);
		return UserTable.getUser(username, password);
	}

	/**
	 * Authenticate The User By Login Or SignUP(For New Members). Returns True If
	 * The Authentication Was Successfull, Else Returns False
	 **/
	static boolean authenticate() {
		System.out.println("Press 1: Login");
		System.out.println("Press 2: Signup");
		Scanner sc = new Scanner(System.in);
		int choice = Integer.parseInt(sc.nextLine());
		if (choice == 1) {
			user = login();
			return user == null ? false : true;
		} else if (choice == 2) {
			user = signUp();
			return true;
		} else {
			// On invalid input
			return false;
		}
	}

	
	private static boolean isCartEmpty() {
		return cart == null || cart.isEmpty();
	}

	
	static void showMainMenu() {
		System.out.printf(
				"\nPress 1: Add items to cart%nPress 2: View your cart%nPress 3: Remove items from cart%nPress 4: Empty cart%n");
		System.out.printf("Press 5: View status of orders%n");
		if (!isCartEmpty()) {
			System.out.printf("Press 6: ** CONFIRM ORDER **%n");
		}
		detectActions();
	}

	private static void detectActions() {
		Scanner sc = new Scanner(System.in);
		switch (sc.nextInt()) {
		case 1:
			showCafes();
			break;
		case 2:
			showCart();
			showMainMenu();
			break;
		case 3:
			showRemoveItemsUI();
			showMainMenu();
			break;
		case 4:
			emptyCart();
			showMainMenu();
			break;
		case 5:
			user.getPastOrders().print();			
			showMainMenu();
			break;
		case 6:
			confirmOrder();
			showMainMenu();
			break;
		default:
			System.out.println("Invalid Input");
			showMainMenu();
			break;
		}
	}
	
	
	private static void showCafes() {
		System.out.println("\nSelect a cafe to view menu:");
		System.out.printf("Press 1: Cafe1%nPress 2: Cafe2%nPress 3: Cafe3%nPress 4: Cafe4%nPress 5: Cafe5%n"
				+ "(-1 to go back)%n");
		detectCafeChoice();
	}

	
	private static void detectCafeChoice() {
		Scanner sc = new Scanner(System.in);
		switch (sc.nextInt()) {
		case 1:
			showCafeMenu(3224);
			askForOrder(3224);
			break;
		case 2:
			showCafeMenu(2209);
			askForOrder(2209);
			break;
		case 3:
			showCafeMenu(3256);
			askForOrder(3256);
			break;
		case 4:
			showCafeMenu(5543);
			askForOrder(5543);
			break;
		case 5:
			showCafeMenu(7658);
			askForOrder(7658);
			break;
		case -1:
			showMainMenu();
			break;
		default:
			System.out.println("Enter Valid Choice.");
			showCafes();
			break;
		}
	}

	
	/** Show Menu of A Cafe Whose ID Is Passed As Parameter **/
	static void showCafeMenu(int cafeID) {
		System.out.println("\nMenu:");
		System.out.printf("%-12s%-34s%6s%n", "Item ID", "Item Name", "Cost");
		readItems(cafeID);
	}

	
	private static void readItems(int cafeID) {
		try {
			FileReader fr = new FileReader("items_data.csv");
			Scanner inFile = new Scanner(fr);
			while (inFile.hasNext()) {
				String record = inFile.nextLine();
				// if cafeId matches, only then display.
				if(isCafeItem(cafeID, record))
					printItem(record);
			}
			inFile.close();
			System.out.println();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	
	private static boolean isCafeItem(int cafeID, String record) {
		// split the line into individual fields of the row in an array.
		// Index : Value
		// 4 : cafeID (int)
		return Integer.parseInt(record.split(",")[4]) == cafeID;
	}
	
	
	private static void printItem(String record) {
		String[] fields = record.split(",");
		// Index : Value
		// 0 : itemId (int)
		// 1 : Item Name (String)
		// 2 : amount (String)
		// 3 : cost (double)
		System.out.printf("%-12s%-34s%6s%n", fields[0], fields[1] + " " + fields[2], fields[3]);
	}
	
	
	/** Ask The User To Add Items To Cart For Order **/
	static private void askForOrder(int cafeID) {
		System.out.println("Enter Item ID's to add to cart\n(-1 to go back)");
		Scanner sc = new Scanner(System.in);
		String[] ids = sc.nextLine().split(" ");
		if (ids[0].equals("-1")) {
			showCafes();
			return;
		}
		addItemsToCart(ids, cafeID);
	}
	
	
	private static void addItemsToCart(String ids[], int cafeID) {
		try {
			// Initialize the cart
			cart = new Cart(cafeID);
			for (String id : ids) {
				FileReader fr = new FileReader("items_data.csv");
				Scanner inFile = new Scanner(fr);
				while (inFile.hasNext()) {
					String[] fields = inFile.nextLine().split(",");
					// Index : Value
					// 0 : itemId (int)
					// 1 : Item Name (String)
					// 2 : amount (String)
					// 3 : cost (double)
					// 4 : cafeID (int)
					if (Integer.parseInt(fields[0]) == Integer.parseInt(id) && Integer.parseInt(fields[4]) == cafeID) {
						
						CartItem cartItem = new CartItem(Integer.parseInt(fields[0]), fields[1] + " " + fields[2],
								Double.parseDouble(fields[3]), getQuantity(fields[1] + " " + fields[2]));
						cart.addItem(cartItem);
						break;
					}
				}
			}
			System.out.println("Items added to cart");
			showMainMenu();

		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	
	static private int getQuantity(String itemName) {
		System.out.print("Enter Quantity for " + itemName + ": ");
		return new Scanner(System.in).nextInt();
	}
	
	
	private static void confirmOrder() {
		Order order = new Order(cart, user);
		order.giveConfirmation();
		cart.empty();
	}

	
	private static void showCart() {
		if (isCartEmpty()) System.out.println("Cart is Empty.");
		else cart.printItems();
	}

	
	/** UI When User Wants To Empty Cart **/
	private static void emptyCart() {
		if (isCartEmpty()) {
			System.out.println("Cart is already empty.");
		} else {
			cart.empty();
			System.out.println("Cart is now empty.");
		}
	}

	
	/** UI When User Wants To Remove An Item From Cart **/
	private static void showRemoveItemsUI() {
		if (isCartEmpty()) {
			System.out.println("Cart is Empty.");
		} else {
			System.out.println("Enter Item Id to remove\n(-1 to go back)");
			Scanner sc = new Scanner(System.in);
			removeCartItem(sc.nextInt());
		}
	}
	
	
	private static void removeCartItem(int itemID) {
		if (itemID == -1) {
			return;
		}
		if (cart.hasItem(itemID)) {
			cart.removeItem(itemID);
			System.out.println("Item Removed From Cart\n");
		} else {
			System.out.println("No item with id " + itemID + " is in the cart.");
		}
		showRemoveItemsUI();
	}
	

	static void printOrderDetails(OrderDetails orderDetails) {
		System.out.println("Order ID: " + orderDetails.orderId);
		System.out.println("Cafe ID: " + orderDetails.cafeID);
		System.out.println("Username: " + orderDetails.username);
		System.out.println("Phone Number: " + orderDetails.phoneNumber);
		System.out.println("Delivery Address: " + orderDetails.address);
		System.out.println("Order Time: " + orderDetails.orderTime);
		System.out.println("Total Cost: " + orderDetails.totalCost);
		printOrderedItems(orderDetails.root_OrderedItems);
	}

	static void printOrderedItems(CartItem orderedItem) {
		System.out.println("Items Ordered:");
		while (orderedItem != null) {
			System.out.printf("Item ID: %d		Item Name: %s		Quantity: %d		Total Cost: %.2f%n",
					orderedItem.itemID, orderedItem.name, orderedItem.quantity, orderedItem.totalCost);
			orderedItem = orderedItem.next;
		}
		System.out.println();
	}
}
