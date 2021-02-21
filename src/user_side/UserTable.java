package user_side;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


/* Class That Implements A Hash Table */
public class UserTable {
	private static User[] users = new User[26];

	/** Hash Function To Return Key **/
	private static int hash(String username) {
		int n = CommonFunctionalities.getId(username);
		return n % 26;
	}
	
	
	/** Print All Users **/
	static void printUsers() {
		for(User user : users) {
			// if index is not null
			if(user != null) {
				// print all users in the tree at current index
				printTree(user);
			}
		}
	}
	
	
	/** Print All Users In A Tree **/
	private static void printTree(User root) {
		if(root == null) return;
		printTree(root.left);
		System.out.printf("%s	%s	 %s	  %s   %s%n", root.name, root.username, root.password, root.email, root.phoneNumber);
		printTree(root.right);
	}

	
	/** Add User To BST Tree **/
	private static User addToUserTree(User root, User newUser) {
		if (root == null) {
			root = newUser;
		} else if (newUser.username.equals(root.username)) {
			System.out.println("Username " + newUser.username + " is already taken. Choose another.");
		} else if (newUser.username.compareTo(root.username) < 0)
			root.left = addToUserTree(root.left, newUser);
		else
			root.right = addToUserTree(root.right, newUser);
		return root;
	}

	
	/** Add User **/
	static void addUser(User newUser) {
		int key = hash(newUser.username);
		// if the key index is empty, make it the root
		if (users[key] == null) {
			users[key] = newUser;
			return;
		}
		// else add it to the tree at key index
		users[key] = addToUserTree(users[key], newUser);
	}

	
	/** Search And Get A User **/
	private static User getUser(User root, String username, String password) {
		if (root == null)
			return root;
		// If username matches, check password
		else if (root.username.equals(username)) {
			if (root.password.equals(password))
				return root;
			// if password is incorrect
			else
				return null;
		} else if (username.compareTo(root.username) < 0)
			return getUser(root.left, username, password);
		else
			return getUser(root.right, username, password);
	}

	
	/** Search And Get A User **/
	static User getUser(String username, String password) {
		// get key for the user
		int key = hash(username);
		return getUser(users[key], username, password);
	}

	
	/** Copy User Node u1 To User Node u2 **/
	private static void copy(User u1, User u2) {
		u2.username = u1.username;
		u2.name = u1.name;
		u2.password = u1.password;
		u2.email = u1.email;
		u2.phoneNumber = u1.phoneNumber;
	}

	
	/** Get User With Max Username **/
	private static User getMax(User root) {
		while (root.right != null)
			root = root.right;
		return root;
	}

	
	/** Remove A User From Tree **/
	private static User removeUser(User root, String username) {
		if (root == null)
			return root;
		// If the User to be removed is found in the tree
		if (root.username.equals(username)) {
			// Three cases for the User node:
			// 1) If it's a leaf node
			if (root.right == null && root.left == null) {
				root = null;
			}
			// 2) If it has one child
			else if (root.right == null || root.left == null) {
				if (root.right != null)
					root = root.right;
				else
					root = root.left;
			}
			// 3) If it has two children
			else {
				// Get maximum node from it's left subtree
				User max = getMax(root.left);
				// Copy the maximum node to current node
				copy(max, root);
				// Remove the maximum node
				root.left = removeUser(root.left, max.username);
			}
		} else if (username.compareTo(root.username) < 0)
			root.left = removeUser(root.left, username);
		else
			root.right = removeUser(root.right, username);
		return root;
	}

	
	/**
	 * Save The User Record In The "Users.csv" File Upon Signup And Add To Hash
	 * Table
	 **/
	static void saveNewUser(User newUser) {
		try {
			FileWriter fw = new FileWriter("Users.csv", true);
			PrintWriter out = new PrintWriter(fw);
			// Write the record with "," as a delimiter.
			out.println(newUser.name + "," + newUser.username + "," + newUser.password + "," + newUser.email + ","
					+ newUser.phoneNumber);
			out.close();
			addUser(newUser);
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	/** Get Updated Content Of File After Removing The Record Of 'username' **/
	private static String getUpdatedFileString(String username) {
		String fileContent = "";
		try {
			FileReader fr = new FileReader("Users.csv");
			Scanner inFile = new Scanner(fr);
			while (inFile.hasNext()) {
				String row = inFile.nextLine();
				// Read the next line.
				// split the line and store the individual fields of the row in an array.
				// Index : Value
				//     1 : username (String)
				String[] fields = row.split(",");
				if(!fields[1].equals(username)) {
					fileContent += row;
					fileContent += "\n";
				}
			}
			inFile.close();			
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return fileContent;
	}

	
	/** Remove User From Users.csv File **/
	private static void deleteFromFile(String username) {
		try {
			String updatedContent = getUpdatedFileString(username);
			PrintWriter out = new PrintWriter("Users.csv");
			out.println(updatedContent);
			out.close();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
	}
	
	
	/** Remove A User **/
	static void removeUser(String username) {
		// get key for the user
		int key = hash(username);
		// remove the user from tree at key index
		users[key] = removeUser(users[key], username);
		// remove the user from "Users.csv" file 
		deleteFromFile(username);
	}
	
	
	/** Update Item Name in "items_data.csv" using an item id **/
	static void updateItem(int id, String newName) {
		try {
			FileReader fr = new FileReader("items_data.csv");
			Scanner inFile = new Scanner(fr);
			// fileContent will hold all the content of updated file
			String fileContent = "";
			while (inFile.hasNext()) {
				String row = inFile.nextLine();
				// Read the next line.
				// split the line and store the individual fields of the row in an array.
				// Index : Value
				// 0 : id (String)
				// 1 : name (String)
				// 2 : quantity (String)
				// 3 : cost (double)
				// 4 : ? (?)
				String[] fields = row.split(",");
				// if the item to be updated is found
				if(Integer.parseInt(fields[0])== id) {
					// update the item name to new item name
					fields[1] = newName;
					row = (fields[0] + "," + fields[1] + "," + fields[2] + "," + fields[3] + ","
							+ fields[4]);
				}
				fileContent += row;
				fileContent += "\n";
			}
			inFile.close();
			// write file from scratch with updated item name
			PrintWriter out = new PrintWriter("items_data.csv");
			out.println(fileContent);
			out.close();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
	}

	
	/** Load All The Users From "Users.csv" File Into The HashTable Data Structure **/
	static void loadUsers() {
		try {
			FileReader fr = new FileReader("Users.csv");
			Scanner inFile = new Scanner(fr);
			while (inFile.hasNext()) {
				// Read the next line.
				String row = inFile.nextLine();
				// split the line and store the individual fields of the row in an array.
				// Index : Value
				// 0 : name (String)
				// 1 : username (String)
				// 2 : password (String)
				// 3 : email (String)
				// 4 : phoneNumber (String)
				String[] fields = row.split(",");
				User user = new User(fields[0], fields[1], fields[2], fields[3], fields[4]);
				// get key for the user
				addUser(user);
			}
			inFile.close();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}

	}

}