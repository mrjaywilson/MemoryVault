/**
 * 
 * CompanyList, Version 0.1.0 (Alpha)
 * 
 * @author Jay Wilson
 *
 */
package net.mrjaywilson.memoryvault.controls;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.mrjaywilson.memoryvault.main.Global;
import net.mrjaywilson.memoryvault.models.Company;

public class CompanyList extends ListView<Company> {
	// Class for basic visual settings related to the company list view
	public CompanyList() {
		this.setMaxHeight(Global.COMPANY_LIST_MAX_HEIGHT);
		this.setPrefHeight(Global.COMPANY_LIST_PREF_HEIGHT);
		this.setPrefWidth(Global.COMPANY_LIST_PREF_WIDTH);
		this.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		
		setPlaceholder(new Label("No company has been added.\nClick \"File > New Company\" to enter your first company."));
	}
	
	
}
