package net.mrjaywilson.memoryvault.services;

import java.util.ArrayList;

import net.mrjaywilson.memoryvault.models.Company;
import net.mrjaywilson.memoryvault.models.Password;

public interface IDatabase {
	// Company Contracts
	public void createNewCompany(Company company);
	public void updateCompanyInfo(int cmopanyId, Company company);
	public Company getCompanyRecord(int companyId);
	public ArrayList<Company> getCompanyList();
	public ArrayList<Company> searchCompanyByName(String CompanyName);
	
	// Password Contracts
	public void createNewPassword(int companyId, Password password);
	public void updatePasswordInfo(int passwordId, Password password);
	public Password getPasswordRecordById(int passwordId);
	public Password getLastPasswordRecord();
	public ArrayList<Password> getPasswordList();
	public ArrayList<Password> getPasswordListByCompanyId(int companyId);
	public ArrayList<Password> searchPasswordByCompanyName(String companyName);
	
	
	// General Database Contracts
	public void deleteCompanyRecord(int companyId);
	public void deletePasswordRecord(int passwordId);
	
	// Usertable
	//	add new username / password
	//	used for logging into software
	//	also to login to the database
	//	after decrypting the database file

	// Add new company

	// Add new Username
	// Add new password

	// delete company
	// delete username / password

	// update password
	// update company name
	// update user name

	// get all from company table
	// get all from user table using join with company table
}
