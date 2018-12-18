package net.mrjaywilson.memoryvault.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The purpose of this class is to speed up the application by
 * creating only one connection and using that connection
 * during the life-cycle of the application for all database
 * services.
 * 
 * @author Jay Wilson
 * @version 0.010
 *
 */
public class ConnectionService {
	
	public static Connection connection = null;
	
	static String dbURL = "jdbc:mysql://localhost:4406/MemoryVault";
	static String dbUser = "root";
	static String dbPassword = "root";

	public static void StartService() {
		try {
			connection = DriverManager.getConnection(
					dbURL, dbUser, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
