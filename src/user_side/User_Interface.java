package user_side;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class User_Interface {

	private static User user;
	private static Cart cart;

	static void display() {
		while (!authenticate()) {
			System.out.println("Invalid Login\n");
		}
		System.out.println("You are Logged In");
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
			if (user == null) {
				return false;
			}
			return true;
		} else if (choice == 2) {
			user = signUp();
			return true;
		} else {
			// On invalid input
			return false;
		}
	}

	static void showMainMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.printf(
				"\nPress 1: Add items to cart%nPress 2: View your cart%nPress 3: Remove items from cart%nPress 4: Empty cart%n");
		System.out.printf("Press 5: View status of orders%n");
		if (cart != null && !cart.isEmpty()) {
			System.out.printf("Press 6: ** CONFIRM ORDER **%n");
		}
		int option = sc.nextInt();
		switch (option) {
		case 1:
			showCafes();
			break;
		case 2:
			showViewCartUI();
			break;
		case 3:
			showRemoveItemsUI();
			break;
		case 4:
			showEmptyCartUI();
			break;
		case 5:
			PastOrders pastOrders = user.getPastOrders();
			pastOrders.print();
			showMainMenu();
			break;
		case 6:
			Order order = new Order(cart, user);
			order.saveOrderDetails();
			// save the order as a past order
			OrderDetails orderDetails = order.getOrderDetails();
			PastOrder pastOrder = new PastOrder(orderDetails.orderId, orderDetails.orderTime, orderDetails.cafeID,
					orderDetails.address, orderDetails.totalCost, "0");
			pastOrder.addItems(cart.getCartItemList());
			cart.empty();
			PastOrders pastOrders_ = user.getPastOrders();
			pastOrders_.add(pastOrder);
			showMainMenu();
			break;
		default:
			System.out.print("Invalid Input");
			showMainMenu();
			break;
		}
	}

	/** UI When User Wants To View Cart Items **/
	private static void showViewCartUI() {
		if (cart == null || cart.isEmpty()) {
			System.out.println("Cart is Empty.");
		} else {
			cart.printItems();
		}
		showMainMenu();
	}

	/** UI When User Wants To Empty Cart **/
	private static void showEmptyCartUI() {
		if (cart == null || cart.isEmpty()) {
			System.out.println("Cart is already empty.");
		} else {
			cart.empty();
			System.out.println("Cart is now empty");
		}
		showMainMenu();
	}

	/** UI When User Wants To Remove An Item From Cart **/
	private static void showRemoveItemsUI() {
		if (cart == null || cart.isEmpty()) {
			System.out.println("Cart is Empty");
			showMainMenu();
		} else {
			Scanner sc = new Scanner(System.in);
			sc = new Scanner(System.in);
			System.out.println("Enter Item Id to remove\n(-1 to go back)");
			int itemID = sc.nextInt();
			if (itemID == -1) {
				showMainMenu();
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
	}

	private static void showCafes() {
		System.out.println("\nSelect a cafe to view menu:");
		Scanner sc = new Scanner(System.in);
		System.out.printf("Press 1: Cafe1%nPress 2: Cafe2%nPress 3: Cafe3%nPress 4: Cafe4%nPress 5: Cafe5%n"
				+ "(-1 to go back)%n");
		int option = sc.nextInt();
		switch (option) {
		case 1:
			showMenu(3224);
			askForOrder(3224);
			break;
		case 2:
			showMenu(2209);
			askForOrder(2209);
			break;
		case 3:
			showMenu(3256);
			askForOrder(3256);
			break;
		case 4:
			showMenu(5543);
			askForOrder(5543);
			break;
		case 5:
			showMenu(7658);
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
	static void showMenu(int cafeID) {
		try {
			System.out.println("\nMenu:");
			System.out.printf("%-12s%-34s%6s%n", "Item ID", "Item Name", "Cost");
			FileReader fr = new FileReader("items_data.csv");
			Scanner inFile = new Scanner(fr);
			while (inFile.hasNext()) {
				// Read the next line.
				String row = inFile.nextLine();
				// split the line and store the individual fields of the row in an array.
				// Index : Value
				// 0 : itemId (int)
				// 1 : Item Name (String)
				// 2 : amount (String)
				// 3 : cost (double)
				// 4 : cafeID (int)
				String[] fields = row.split(",");
				// if cafeId matches, only then display.
				if (Integer.parseInt(fields[4]) == cafeID) {
					System.out.printf("%-12s%-34s%6s%n", fields[0], fields[1] + " " + fields[2], fields[3]);
				}
			}
			inFile.close();
			System.out.println();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	/** Ask The User To Add Items To Cart For Order **/
	static private void askForOrder(int cafeId) {
		System.out.println("Enter Item ID's to add to cart\n(-1 to go back)");
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		String[] ids = line.split(" ");
		if (ids[0].equals("-1")) {
			showCafes();
			return;
		}
		try {
			cart = new Cart(cafeId);
			for (String id : ids) {
				FileReader fr = new FileReader("items_data.csv");
				Scanner inFile = new Scanner(fr);
				while (inFile.hasNext()) {
					String row = inFile.nextLine();
					String[] fields = row.split(",");
					if (Integer.parseInt(fields[0]) == Integer.parseInt(id) && Integer.parseInt(fields[4]) == cafeId) {
						int quantity = getQuantity(fields[1] + " " + fields[2]);
						CartItem cartItem = new CartItem(Integer.parseInt(fields[0]), fields[1] + " " + fields[2],
								Double.parseDouble(fields[3]), quantity);
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

	/**
	 * Ask The Quantity Of A CartItem To Be Ordered
	 * 
	 * @return: Quantity
	 **/
	static private int getQuantity(String itemName) {
		System.out.print("Enter Quantity for " + itemName + ": ");
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
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
