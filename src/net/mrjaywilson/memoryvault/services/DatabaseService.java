package net.mrjaywilson.memoryvault.services;

import java.util.ArrayList;

import net.mrjaywilson.memoryvault.models.Company;
import net.mrjaywilson.memoryvault.models.Password;

import java.sql.*;

/**
 * This class handles all the services related to database
 * operations via CRUD. This class is designed specifically
 * to work across the board with any other applications that
 * may need to access the same database. (EX. Mobile
 * application that performs the same duties, but for the
 * phone.)
 * 
 * @author Jay Wilson
 *
 */
public class DatabaseService implements IDatabase {
	
	@Override
	public void createNewCompany(Company company) {
		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"INSERT INTO MemoryVault.CompanyTable " +
						"(CompanyId, CompanyName, CompanyUrl)" +
						"VALUES (null, ?, ?)");
				) {
			
			statement.setString(1, company.getCompanyName());
			statement.setString(2, company.getCompanyUrl());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
	}

	@Override
	public void updateCompanyInfo(int companyId, Company company) {
		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"UPDATE MemoryVault.CompanyTable " +
						"SET CompanyName = ?, CompanyUrl = ? " +
						"WHERE CompanyId = ?");
				) {
			
			statement.setString(1, company.getCompanyName());
			statement.setString(2, company.getCompanyUrl());
			statement.setInt(3, companyId);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
	}

	@Override
	public Company getCompanyRecord(int companyId) {
		
		Company company = null;
		
		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"SELECT * FROM MemoryVault.CompanyTable WHERE CompanyId = ?");
				) {
			
			statement.setInt(1, companyId);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				company = new Company(
						resultSet.getInt("CompanyId"),
						resultSet.getString("CompanyName"),
						resultSet.getString("CompanyUrl"));				
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
		
		return company;
	}

	@Override
	public ArrayList<Company> getCompanyList() {
		
		ArrayList<Company> companyList = new ArrayList<Company>();

		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"SELECT * FROM MemoryVault.CompanyTable");
				) {
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				companyList.add(
					new Company(
							resultSet.getInt("CompanyId"),
							resultSet.getString("CompanyName"),
							resultSet.getString("CompanyUrl")));				
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
		
		return companyList;
	}

	@Override
	public ArrayList<Company> searchCompanyByName(String companyName) {
		
		ArrayList<Company> companyList = new ArrayList<Company>();

		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"SELECT * FROM MemoryVault.CompanyTable " +
						" WHERE CompanyName LIKE ?");
				) {
			
			statement.setString(1, "%" + companyName + "%");
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				companyList.add(
					new Company(
							resultSet.getInt("CompanyId"),
							resultSet.getString("CompanyName"),
							resultSet.getString("CompanyUrl")));				
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
		
		return companyList;
	}
	
	public Company getCompanyByName(String name) {
		Company company = null;
		
		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"SELECT * FROM MemoryVault.CompanyTable WHERE CompanyName = ?");
				) {
			
			statement.setString(1, name);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				company = new Company(
						resultSet.getInt("CompanyId"),
						resultSet.getString("CompanyName"),
						resultSet.getString("CompanyUrl"));				
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
		
		return company;

	}

	@Override
	public void createNewPassword(int companyId, Password password) {
		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"INSERT INTO MemoryVault.PasswordTable " +
						"(PasswordId, CompanyId, UserName, UserEmail, UserPassword, PasswordNote)" +
						"VALUES (null, ?, ?, ?, ?, ?)");
				) {
			
			statement.setInt(1, companyId);
			statement.setString(2, password.getUserName());
			statement.setString(3, password.getUserEmail());
			statement.setString(4, password.getUserPassword());
			statement.setString(5, password.getPasswordNotes());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
	}

	@Override
	public void updatePasswordInfo(int passwordId, Password password) {
		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"UPDATE MemoryVault.PasswordTable " +
						"SET CompanyId = ?, UserName = ?, UserEmail = ?, UserPassword = ?, PasswordNote = ? " +
						"WHERE PasswordId = ?");
				) {
			
			statement.setInt(1, password.getCompanyId());
			statement.setString(2, password.getUserName());
			statement.setString(3, password.getUserEmail());
			statement.setString(4, password.getUserPassword());
			statement.setString(5, password.getPasswordNotes());
			statement.setInt(6, passwordId);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
	}

	@Override
	public Password getPasswordRecordById(int passwordId) {
		
		Password password = null;
		
		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"SELECT * FROM MemoryVault.PasswordTable WHERE PasswordId = ?");
				) {
			
			statement.setInt(1, passwordId);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				password = new Password(
						resultSet.getInt("PasswordId"),
						resultSet.getInt("CompanyId"),
						resultSet.getString("UserName"),
						resultSet.getString("UserEmail"),
						resultSet.getString("UserPassword"),
						resultSet.getString("PasswordNote"));
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
		
		return password;
	}

	@Override
	public Password getLastPasswordRecord() {
		
		Password password = null;
		
		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"SELECT * " +
						"FROM MemoryVault.PasswordTable " +
						"WHERE passwordId=(" +
							"SELECT max(passwordId) " +
							"FROM MemoryVault.PasswordTable);");
				) {
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				password = new Password(
						resultSet.getInt("PasswordId"),
						resultSet.getInt("CompanyId"),
						resultSet.getString("UserName"),
						resultSet.getString("UserEmail"),
						resultSet.getString("UserPassword"),
						resultSet.getString("PasswordNote"));
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
		
		return password;
	}

	@Override
	public ArrayList<Password> getPasswordList() {
		
		ArrayList<Password> passwordList = new ArrayList<Password>();

		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"SELECT * FROM MemoryVault.PasswordTable");
				) {
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				passwordList.add(
					new Password(
							resultSet.getInt("PasswordId"),
							resultSet.getInt("CompanyId"),
							resultSet.getString("UserName"),
							resultSet.getString("UserEmail"),
							resultSet.getString("UserPassword"),
							resultSet.getString("PasswordNote")));
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
		
		return passwordList;
	}

	@Override
	public ArrayList<Password> getPasswordListByCompanyId(int companyId) {
		ArrayList<Password> passwordList = new ArrayList<Password>();

		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"SELECT * FROM MemoryVault.PasswordTable " +
						" WHERE CompanyId = ?");
				) {
			
			statement.setInt(1, companyId);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				passwordList.add(
						new Password(
								resultSet.getInt("PasswordId"),
								resultSet.getInt("CompanyId"),
								resultSet.getString("UserName"),
								resultSet.getString("UserEmail"),
								resultSet.getString("UserPassword"),
								resultSet.getString("PasswordNote")));
				}
			
			resultSet.close();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
		
		return passwordList;
	}

	@Override
	public ArrayList<Password> searchPasswordByCompanyName(String companyName) {
		
		ArrayList<Password> passwordList = new ArrayList<Password>();

		try (
				PreparedStatement statement = ConnectionService.connection.prepareStatement(
						"SELECT * FROM MemoryVault.PasswordTable INNER JOIN " +
						"MemoryVault.CompanyTable ON companyName LIKE ? AND " +
						"PasswordTable.companyId = CompanyTable.companyId");
				) {
			
			statement.setString(1, "%" + companyName + "%");
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				passwordList.add(
						new Password(
								resultSet.getInt("PasswordId"),
								resultSet.getInt("CompanyId"),
								resultSet.getString("UserName"),
								resultSet.getString("UserEmail"),
								resultSet.getString("UserPassword"),
								resultSet.getString("PasswordNote")));
				}
			
			resultSet.close();
			
		} catch (SQLException e) {
			// Alert
			// Log
			e.printStackTrace();
		}
		
		return passwordList;
	}

	@Override
	public void deleteCompanyRecord(int companyId) {
		
		try (
				PreparedStatement statementPassword = ConnectionService.connection.prepareStatement(
				"DELETE FROM MemoryVault.PasswordTable " +
				" WHERE CompanyId = ?");
				
				PreparedStatement statementCompany = ConnectionService.connection.prepareStatement(
				"DELETE FROM MemoryVault.CompanyTable " +
				" WHERE CompanyId = ?");
				) {

			statementPassword.setInt(1, companyId);
			statementPassword.execute();

			statementCompany.setInt(1, companyId);
			statementCompany.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletePasswordRecord(int passwordId) {
		
		try (
				PreparedStatement statementPassword = ConnectionService.connection.prepareStatement(
				"DELETE FROM MemoryVault.PasswordTable " +
				" WHERE passwordId = ?");
				) {

			statementPassword.setInt(1, passwordId);
			statementPassword.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
