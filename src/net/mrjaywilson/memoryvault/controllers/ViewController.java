/**
 * 
 * ViewController, Version 0.2.0 (Alpha)
 * 
 * @author Jay Wilson
 *
 */
package net.mrjaywilson.memoryvault.controllers;

import javafx.scene.layout.Pane;
import net.mrjaywilson.memoryvault.models.*;
import net.mrjaywilson.memoryvault.views.*;

public class ViewController {
	
	public ViewController() { }
	
	public Pane editPassword(Password password) {
		return new EditPassword(password);
	}

	public Pane editCompany() {
		return new EditCompany();
	}
	
	public Pane newCompany() {
		return new NewCompany();
	}
	
	public Pane newPassword() {
		return new NewPassword();
	}
	
	public Pane main() {
		return new Main();
	}
	
	public Pane detailView(Password password) {
		return new DetailView(password);
	}
}
