/**
 * 
 * CompanyTable, Version 0.1.0 (Alpha)
 * 
 * @author Jay Wilson
 *
 */
package net.mrjaywilson.memoryvault.controls;

import com.sun.org.apache.bcel.internal.generic.NEW;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import net.mrjaywilson.memoryvault.models.Company;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CompanyTable extends TableView {
	
	public CompanyTable() {
		this.setMaxHeight(210);
		this.setPrefHeight(210);
		
		this.setMinWidth(150);
		this.setMaxWidth(350);
		
		TableColumn<Company, String> nameCol = new TableColumn<Company, String>();
		nameCol.setText("Company Name");
		nameCol.setMinWidth(125);
		nameCol.setCellValueFactory(new PropertyValueFactory("CompanyName"));

		TableColumn<Company, String> urlCol = new TableColumn<Company, String>();
		urlCol.setText("Company URL");
		urlCol.setMinWidth(215);
		urlCol.setCellValueFactory(new PropertyValueFactory("CompanyUrl"));
		
		setPlaceholder(new Label("No company has been added.\nGo to \"New Company\" to enter your first company."));

		this.getColumns().addAll(nameCol, urlCol);
	}
}
