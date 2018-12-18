/**
 * 
 * VaultMenu, Version 0.1.2 (Alpha)
 * 
 * @author Jay Wilson
 *
 * Latest Update: (Minor) Removed style code to add later with CSS file See comments below.
 * 
 */
package net.mrjaywilson.memoryvault.controls;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class VaultMenu extends MenuBar {
	
	public Menu menu;
	public Menu help;
	public MenuItem About;
	public MenuItem ViewLog;
	public MenuItem Unlock;
	public MenuItem Exit;
	public MenuItem EditCompany;
	public MenuItem NewPassword;
	public MenuItem NewCompany;
	
	public VaultMenu() {
		
		// File menu
		menu = new Menu("File");
		Unlock = new MenuItem("Unlock Database");
		EditCompany = new MenuItem("Edit Company");
		NewCompany = new MenuItem("New Company");
		NewPassword = new MenuItem("New Password");
		Exit = new MenuItem("Exit");
		
		menu.getItems().addAll(
				Unlock, 
				EditCompany, 
				NewCompany,
				NewPassword, 
				Exit);
		
		// Help Menu
		help = new Menu("Help");
		ViewLog = new MenuItem("View Log");
		About = new MenuItem("About");
		
		help.getItems().addAll(
				ViewLog, 
				About);
		
		this.getMenus().addAll(menu, help);
		// Need to setup style in the resources
		// Black Bar with black letters and gold buttons
		//this.setStyle("");
		this.setPrefWidth(200);
		this.setMinHeight(35);
		this.setPrefHeight(35);
		this.setStyle("-fx-font-size: 15px");
	}
}
