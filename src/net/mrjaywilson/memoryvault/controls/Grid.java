/**
 * 
 * Grid, Version 0.1.0 (Alpha)
 * 
 * @author Jay Wilson
 *
 */
package net.mrjaywilson.memoryvault.controls;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import net.mrjaywilson.memoryvault.main.Global;

public class Grid extends GridPane {
	public Grid() {
		for (int i = 0; i < (Global.APP_WIDTH / 35); i++) {
			getColumnConstraints().add(new ColumnConstraints(35));
		}

		for (int i = 0; i < (Global.APP_HEIGHT / 35); i++) {
			getRowConstraints().add(new RowConstraints(35));
		}
	}
}
