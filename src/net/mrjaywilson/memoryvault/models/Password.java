package net.mrjaywilson.memoryvault.models;

public class Password {
	private Integer passwordId;
	private int companyId;
	private String userName;
	private String userEmail;
	private String userPassword;
	private String passwordNotes;
	
	public Password (
			Integer passwordId,
			int companyId,
			String userName,
			String userEmail,
			String userPassword,
			String passwordNotes) {
		
		this.passwordId = passwordId;
		this.companyId = companyId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.passwordNotes = passwordNotes;
	}
	
	@Override
	public String toString() {
		return "Password ID: " + this.passwordId + "\n" +
				"Company ID: " + this.companyId  + "\n" +
				"username: " + this.userName + "\n" +
				"user email: " + this.userEmail  + "\n" +
				"password: " + this.userPassword  + "\n" +
				"notes: " + this.passwordNotes;
	}

	public Integer getPasswordId() {
		return passwordId;
	}

	public void setPasswordId(Integer passwordId) {
		this.passwordId = passwordId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getPasswordNotes() {
		return passwordNotes;
	}

	public void setPasswordNotes(String passwordNotes) {
		this.passwordNotes = passwordNotes;
	}
}
