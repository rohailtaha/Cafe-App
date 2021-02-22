package user_side;

import java.util.UUID;

public class CommonFunctionalities {
	/*
	 * Generate A Hashed Key For A User
	 */
	static int getId(String username) {
    	int l = username.length();  
        int sum = 0; 
        // sum the ascii values of all characters in the string "nameLowerCase"
        for (int i = 0; i < l; i++) { 
        	if (username.charAt(i) == ' ') { 
                sum += 1; 
            } 
            else {
            	sum += username.charAt(i);             
            }
        }
        return sum;
	}

	static String getId() {
		String uniqueKey = UUID.randomUUID().toString().replace("-", "").substring(20, 32);
		return uniqueKey;
	}
}
