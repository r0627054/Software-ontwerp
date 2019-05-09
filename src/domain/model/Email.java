package domain.model;

/**
 * 
 * The email contains all the checks of having a valid email.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class Email {

	/**
	 * Variable storing the email String.
	 */
	private String email;

	/**
	 * Initialise a new email with the given email string.
	 * 
	 * @param email The email string.
	 * @post The email string is set.
	 *       | setEmail(email)
	 */
	public Email(String email) {
		setEmail(email);
	}

	/**
	 * Returns the email string.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email string.
	 * If the email string equals null or it does not contain exactly one at sign it will throw an illegalArgumentException.
	 * @param email The email string.
	 * @throws IllegalArgumentException if the email equals null or the string does not have exactly 1 at sign.
	 *         | email == null || !Email.hasOneAtSign(email)
	 */
	public void setEmail(String email) {
		if(email == null || !Email.hasOneAtSign(email)) {
			throw new IllegalArgumentException("The email string cannot equal null");
		}
		this.email = email;
	}

	/**
	 * Checks whether the email string contains one at sign or equals null.
	 * @param emailString The string representation of the email.
	 * @return false when the emailString equals null or when it does not contain exactly one at sign. Otherwise it returns true.
	 *        | emailString != null && emailString.indexOf("@") >= 0 && (emailString.indexOf("@") == emailString.lastIndexOf("@"))
	 */
	public static boolean hasOneAtSign(String emailString) {
		return  emailString != null && emailString.indexOf("@") >= 0 && (emailString.indexOf("@") == emailString.lastIndexOf("@"));
	}

	/**
	 * Returns whether or not the email is empty.
	 * An email is empty when the email string is empty or when the string equals null.
	 * @return true when the email string is empty or when the string equals null. Otherwise false.
	 *         | result == this.getEmail() == null || this.getEmail().isEmpty()
	 */
	public boolean isEmpty() {
		return this.getEmail() == null || this.getEmail().isEmpty();
	}
	
	/**
	 * The string representation of the email.
	 * @return the String representation of the email (the email variable).
	 */
	@Override
	public String toString() {
		return this.getEmail();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Email) {
			Email castedMail = (Email) obj;
			if(this.getEmail() == null && castedMail.getEmail() == null) {
				return true;
			}
			if(this.getEmail() != null && castedMail.getEmail() != null && this.getEmail().equals(castedMail.getEmail())) {
				return true;
			}
		}
		return false;
	}

}
