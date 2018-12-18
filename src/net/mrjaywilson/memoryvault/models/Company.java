package net.mrjaywilson.memoryvault.models;

public class Company {
	private Integer companyId;
	private String companyName;
	private String companyUrl;
	
	public Company (
			Integer companyId,
			String companyName,
			String companyUrl) {
		
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyUrl = companyUrl;
	}
	
/*	@Override
	public String toString() {
		return "Company ID: " + this.companyId + "\n" +
				"Company Name: " + this.companyName + "\n" +
				"Company ID: " + this.companyUrl;
	}
*/	
	@Override
	public String toString() {
		return this.companyName;
	}

	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
}
