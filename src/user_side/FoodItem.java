package user_side;

/* This Class Represents A Node of The FoodItem's Tree For A Particular Cafe */
public class FoodItem {
	int id;
	String name;
	double cost;
	double rating;
	// Left Child 
	FoodItem left;
	// Right Child
	FoodItem right;
	
	// FoodItem Constructor
	FoodItem(String name, double cost, int id) {
		this.name = name;
		this.cost = cost;
		this.id = id;
		this.rating = 0;
		left = right = null;
	}
}