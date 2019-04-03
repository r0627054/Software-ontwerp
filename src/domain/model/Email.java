package domain.model;

public class Email {

	private String email;

	public Email(String email) {
		setEmail(email);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean hasOneAtSign() {
		return this.getEmail().indexOf("@") >= 0 && (this.getEmail().indexOf("@") == this.getEmail().lastIndexOf("@"));
	}

	public boolean isEmpty() {
		return this.getEmail().isEmpty() || this.getEmail() == null;
	}
	
	@Override
	public String toString() {
		return this.getEmail();
	}

}
