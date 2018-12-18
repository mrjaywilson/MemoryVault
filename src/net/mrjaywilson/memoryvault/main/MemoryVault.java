/**
 * 
 * VaultMenu, Version 0.4.6 (Alpha)
 * 
 * @author Jay Wilson
 *
 * Latest Update: (Minor) Various updates throughout the program.
 * 
 */
package net.mrjaywilson.memoryvault.main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.mrjaywilson.memoryvault.controllers.ViewController;
import net.mrjaywilson.memoryvault.controls.VaultMenu;
import net.mrjaywilson.memoryvault.services.ConnectionService;

public class MemoryVault extends Application {
	
	// TODO: Make the database Embedded.
	
	// TODO: Connection
	//	X Look into connecting once, and closing at end
	//	X Double check to make sure the connection is
	//		open. If not, open a new one.

	// TODO: General
	//	X Create Interface for encryption service
	//	X Create Encryption service
	//	X Create Interface for database access
	//	X Create Database Service
	
	// TODO: Security
	//	- Allow user to save private key to USB
	//	- If user loses private key, all access is
	//		lost to the security service and database
	//	- private key is located anywhere, and as
	//		long as the user has it, he or she will
	//		have access to the service and database
	//	C even if the user has the private key
	//		he or she will still need their user name
	//		and password to access the memory vault.
	//	- The function of the private key is to
	//		decrypt access to the database so the
	//		user name and password can be verified
	//		from the persistent outside xml file
	//		so that the private key can then be used
	//		to decrypt the passwords and user names
	//		stored in the database.
	
	// TODO: Important goal
	//	C Ensure that the software is written in such
	//		a way that no matter who has access to
	//		the code for the program (it will be open
	//		source), that no one can use it to hack
	//		anyone's local vault.
	
	// TODO: Databases
	// - Consider encrypting the database with the
	//		key.
	// X There will be one table for user name, 
	//		the company, and a foreign key to 
	//		another table storing the
	//		passwords.
	// X The password table will contain all the
	//		password linked only though the foreign
	//		key.
	// X Note, you don't need two tables. However, it
	//		having two tables seems to be a good idea
	//		to help increase security by no making
	//		information readily available. On other
	//		words, if you have a gold statue on your
	//		shelf, you would not store you money bag
	//		on the shelf next to it.
	// X The user can store multiple accounts and 
	//		passwords for each company. That way
	//		if they have multiple email accounts or
	//		facebook accounts, they will be able to 
	//		keep track of all the passwords.
	// X Database: MemoryVault
	//		usertable:
	//			username - PK
	//			password
	//		companytable:
	//			company_id - FK
	//			companyname - PK
	//		Passwordtable:
	//			company_id - FK
	//			username
	//			encrypted password (private key)
	
	// TODO: User Interface
	//	X User will not be able to see more than one
	//		company profile at a time. Obviously, 
	//		this would be a risk if you could.
	// X Allow user to scroll through the user names
	//		and passwords.
	// - Can select one from the data grid and press
	//		copy button to copy the password.
	
	// TODO: Additional UI feature
	// - Allow user to click a button, set parameters
	// 		and then click a button to generate a
	//		password for a company.
	
	// TODO: Future
	// - Allow user to export encrypted password list
	//		to import into android/iOS app.
	// TODO: Logger
	// - Program the logger for error messages and
	//		reporting.
	// - Do this in a separate project, and include
	//		this program.
	
	public static void main(String[] args) {
		
		// Open Database Connection
		ConnectionService.StartService();
		
		launch(args);
	}
	
	Stage primaryStage = new Stage();
	public static BorderPane root = new BorderPane();
	public static ViewController viewController;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		// Basic settings
		stage = primaryStage;		
		root.setPrefWidth(Global.APP_WIDTH);
		root.setPrefHeight(Global.APP_HEIGHT);
		
		// Declare and initialize controls / views
		viewController = new ViewController();
		VaultMenu menu = new VaultMenu();
		
		// Start off on the main view
		root.setTop(menu);
		
		menu.EditCompany.setOnAction(e -> {
			root.setCenter(viewController.editCompany());
		});
		
		menu.Exit.setOnAction(e -> {
			System.exit(0);
		});
		
		menu.NewCompany.setOnAction(e -> {
			root.setCenter(viewController.newCompany());
		});
		
		menu.NewPassword.setOnAction(EventHandler -> {
			root.setCenter(viewController.newPassword());
		});
		
		root.setCenter(viewController.main());

		Scene scene = new Scene(root, Global.APP_WIDTH, Global.APP_HEIGHT);
		
		stage = new Stage();
		stage.setScene(scene);
		//stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		
	}

}
