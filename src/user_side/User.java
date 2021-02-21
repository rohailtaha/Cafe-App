package user_side;

import java.util.Scanner;

public class User {
	String name;
	String username;
	String email;
	String password;
	String phoneNumber;
	PastOrders pastOrders;
	User left;		// left child
	User right; 	// right child
	
	
	public User(String name, String username, String password, String email, String phoneNumber) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.pastOrders = new PastOrders(this.username);
	}
	
	
	public PastOrders getPastOrders() {
		return this.pastOrders;
	}
	
}
