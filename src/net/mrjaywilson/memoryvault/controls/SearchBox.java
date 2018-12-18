/**
 * 
 * SearchBox, Version 0.2.3 (Alpha)
 * 
 * @author Jay Wilson
 *
 * Latest Update: (Minor) Removed code for unused methods.
 * 
 */
package net.mrjaywilson.memoryvault.controls;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import net.mrjaywilson.memoryvault.main.Global;

public class SearchBox extends TextField {
	public SearchBox() {
		setMinHeight(Global.SEARCH_BOX_MIN_HEIGHT);
		setPrefSize(
				Global.SEARCH_BOX_PREF_WIDTH,
				Global.SEARCH_BOX_PREF_WIDTH);
		setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		
		this.setPromptText("Search Companies");
	}
}