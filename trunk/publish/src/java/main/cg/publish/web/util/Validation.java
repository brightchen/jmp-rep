package cg.publish.web.util;





public class Validation {
	

	
	/**
	 * check if the value is empty
	 * i.e. length is 0.
	 */
	public static boolean isEmpty(String value){
		if (value.length()<=0)
			return true;
		else 
			return false;
	}
	/**
	 * check if the value is alphabet
	 */
	public static boolean isNotAlpha(String value){
		if  (isEmpty(value))
			return true;	
		String regex = "[_A-Za-z]*";
		return !value.matches(regex);
	}

	/**
	 * check if the value is Integer
	 */
	public static boolean isNotInteger(String value){
		if  (isEmpty(value))
			return true;	
		String regex = "[0-9]*";
		return !value.matches(regex);
	}
	/**
	 * @param email
	 * @return true if email is valid. False otherwise.
	 */
	public static boolean isInvalidEmail (String email) {
		if  (isEmpty(email))
			return true;	
		String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)"; 
		return !email.matches(regex);
	}
}
