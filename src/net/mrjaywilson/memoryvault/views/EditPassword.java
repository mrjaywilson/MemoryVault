/**
 * 
 * EditPassword, Version 0.1.0 (Alpha)
 * 
 * @author Jay Wilson
 *
 * Latest Update: (Minor) None.
 * 
 */
package net.mrjaywilson.memoryvault.views;

import javafx.scene.layout.Pane;
import net.mrjaywilson.memoryvault.models.Password;

public class EditPassword extends Pane {
	
	private Password password;
	
	public EditPassword(Password password) {
		this.password = password;		
	}
}
