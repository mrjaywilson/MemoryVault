/**
 * 
 * PasswordTable, Version 0.1.0 (Alpha)
 * 
 * @author Jay Wilson
 *
 */
package net.mrjaywilson.memoryvault.controls;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.mrjaywilson.memoryvault.main.Global;
import net.mrjaywilson.memoryvault.models.Password;

public class PasswordTable extends TableView<Password> {
	// 13C x 5R && 2, 6
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PasswordTable() {
		this.setMaxHeight(Global.PASSWORD_TABLE_MAX_HEIGHT);
		this.setPrefHeight(Global.PASSWORD_TABLE_PREF_HEIGHT);
		
		this.setMinWidth(Global.PASSWORD_TABLE_MIN_WIDTH);
		this.setMaxWidth(Global.PASSWORD_TABLE_MAX_WIDTH);
		
		TableColumn<Password, String> userNameCol = new TableColumn<Password, String>();
		userNameCol.setText("User Name");
		userNameCol.setMinWidth(Global.USERNAME_COL_WIDTH);
		userNameCol.setCellValueFactory(new PropertyValueFactory("UserName"));

		TableColumn<Password, String> emailCol = new TableColumn<Password, String>();
		emailCol.setText("User Email");
		emailCol.setMinWidth(Global.EMAIL_COL_WIDTH);
		emailCol.setCellValueFactory(new PropertyValueFactory("UserEmail"));

		TableColumn<Password, String> passwordCol = new TableColumn<Password, String>();
		passwordCol.setText("Password");
		passwordCol.setMinWidth(Global.PASSWORD_COL_WIDTH);
		passwordCol.setCellValueFactory(new PropertyValueFactory("UserPassword"));
		
		this.getColumns().addAll(userNameCol, emailCol, passwordCol);
		
		setPlaceholder(new Label("No passwords for selected company."));
	}
}
