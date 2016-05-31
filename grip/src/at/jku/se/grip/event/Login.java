package at.jku.se.grip.event;

import at.jku.se.grip.GripUI;

public class Login {	
	
	public static boolean verifyPassword(String name, String password){
		
    	if(name.equals("Max") && password.equals("123")){ 		
    		
          	return true;	
    	}

	return false;
	}

}
