package user_side;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println("Hello this is commit 1");
		UserTable.loadUsers();
		User_Interface.display();
	}
	
}

