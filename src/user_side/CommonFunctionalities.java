package user_side;

import java.util.UUID;

public class CommonFunctionalities {
	/*
	 * Generate A Random ID For A User
	 */
	static int getId(String name) {
    	int l = name.length();  
        int sum = 0; 
        // sum the ascii values of all characters in the string "nameLowerCase"
        for (int i = 0; i < l; i++) { 
        	if (name.charAt(i) == ' ') { 
                sum += 1; 
            } 
            else {
            	sum += name.charAt(i);             
            }
        }
        return sum;
	}

	static String getId() {
		String uniqueKey = UUID.randomUUID().toString().replace("-", "").substring(20, 32);
		return uniqueKey;
	}
}
