/**
 * 
 * NewPassword, Version 0.1.0 (Alpha)
 * 
 * @author Jay Wilson
 *
 * Latest Update: None.
 * 
 */
package net.mrjaywilson.memoryvault.views;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import net.mrjaywilson.memoryvault.controls.AppLabel;
import net.mrjaywilson.memoryvault.controls.CompanyList;
import net.mrjaywilson.memoryvault.controls.Grid;
import net.mrjaywilson.memoryvault.controls.SearchBox;
import net.mrjaywilson.memoryvault.main.MemoryVault;
import net.mrjaywilson.memoryvault.models.Company;
import net.mrjaywilson.memoryvault.models.Password;
import net.mrjaywilson.memoryvault.services.DatabaseService;
import net.mrjaywilson.memoryvault.services.SecurityService;

public class NewPassword extends Pane {
	private static ArrayList<Company> companies = null;
	public static ObservableList<Company> companyList = null;

	public NewPassword() {
		GridPane grid = new Grid();
		DatabaseService dbs = new DatabaseService();
		companies = dbs.getCompanyList();
		
		// Password label
		Label passwordLabel = new AppLabel("NEW PASSWORD", 630, 24);
		passwordLabel.setAlignment(Pos.CENTER);
		passwordLabel.setFont(Font.font("Century Gothic", 32));
		grid.add(passwordLabel, 1, 0, 18, 2);
		GridPane.setValignment(passwordLabel, VPos.CENTER);
		GridPane.setHalignment(passwordLabel, HPos.CENTER);

		// Company Label
		Label searchTitle = new AppLabel("Company", 630, 24);
		grid.add(searchTitle, 2, 2, 18, 1);
		GridPane.setValignment(searchTitle, VPos.CENTER);
		GridPane.setHalignment(searchTitle, HPos.LEFT);

		// Input box for searching company name
		TextField searchBox = new SearchBox();
		grid.add(searchBox, 1, 3, 18, 1);
		GridPane.setValignment(searchBox, VPos.CENTER);
		GridPane.setHalignment(searchBox, HPos.CENTER);

		// List for all the companies to choose from
		ListView<Company> companyListView = new CompanyList();
		companyListView.setMaxHeight(95);
		grid.add(companyListView, 1, 4, 18, 3);
		GridPane.setValignment(companyListView, VPos.CENTER);
		GridPane.setHalignment(companyListView, HPos.CENTER);
		
		// Username Label
		Label usernameLabel = new AppLabel("Username*", 280, 24);
		grid.add(usernameLabel, 2, 8, 8, 1);
		GridPane.setValignment(usernameLabel, VPos.CENTER);
		GridPane.setHalignment(usernameLabel, HPos.LEFT);

		// TextField Username
		TextField usernameText = new TextField();
		usernameText.setPrefSize(280, 35);
		grid.add(usernameText, 1, 9, 8, 1);

		// Email Label
		Label emailLabel = new AppLabel("User Email*", 300, 24);
		grid.add(emailLabel, 2, 11, 8, 1);
		GridPane.setValignment(emailLabel, VPos.CENTER);
		GridPane.setHalignment(emailLabel, HPos.LEFT);

		// TextField Email
		TextField emailText = new TextField();
		emailText.setPrefSize(280, 35);
		grid.add(emailText, 1, 12, 8, 1);

		// Verify Email Label
		Label verifyEmailLabel = new AppLabel("Verify Email*", 300, 24);
		grid.add(verifyEmailLabel, 2, 13, 8, 1);
		GridPane.setValignment(verifyEmailLabel, VPos.CENTER);
		GridPane.setHalignment(verifyEmailLabel, HPos.LEFT);

		// TextField Verify Email
		TextField verifyEmailText = new TextField();
		verifyEmailText.setPrefSize(280, 35);
		grid.add(verifyEmailText, 1, 14, 8, 1);

		// Length Picker Label
		Label lengthPickerLabel = new AppLabel("Min Length: ", 210, 24);
		grid.add(lengthPickerLabel, 11, 8, 6, 1);
		GridPane.setValignment(lengthPickerLabel, VPos.CENTER);
		GridPane.setHalignment(lengthPickerLabel, HPos.LEFT);
		
		// Length Picker Drop down
		ComboBox<Integer> lengthPickerBox = new ComboBox<Integer>();
		lengthPickerBox.setMinWidth(105);
		grid.add(lengthPickerBox, 16, 8, 5, 1);
		GridPane.setValignment(lengthPickerBox, VPos.CENTER);
		GridPane.setHalignment(lengthPickerBox, HPos.LEFT);

		// Button Generate Password
		Button generatePassword = new Button("Generate Password");
		generatePassword.setPrefSize(280, 20);
		generatePassword.minWidth(280);
		grid.add(generatePassword, 11, 9, 8, 1);
		GridPane.setHalignment(generatePassword, HPos.CENTER);
		GridPane.setValignment(generatePassword, VPos.CENTER);
		
		// Password label
		Label passwordSecureLabel = new AppLabel("Secure Password", 300, 24);
		grid.add(passwordSecureLabel, 11, 11, 8, 1);
		GridPane.setValignment(passwordSecureLabel, VPos.CENTER);
		GridPane.setHalignment(passwordSecureLabel, HPos.LEFT);

		// TextField Password
		TextField passwordText = new TextField();
		passwordText.setPrefSize(280, 35);
		passwordText.setEditable(false);
		grid.add(passwordText, 11, 12, 8, 1);

		// Password label
		Label passwordHashLabel = new AppLabel("Password Hash", 300, 24);
		grid.add(passwordHashLabel, 11, 13, 8, 1);
		GridPane.setValignment(passwordHashLabel, VPos.CENTER);
		GridPane.setHalignment(passwordHashLabel, HPos.LEFT);

		// Password Hash
		TextField passwordHash = new TextField();
		passwordHash.setPrefSize(280, 35);
		passwordHash.setEditable(false);
		grid.add(passwordHash, 11, 14, 8, 1);

		// Button Update
		Button addPassword = new Button("Add Password to Selected Company");
		addPassword.setPrefSize(420, 20);
		addPassword.minWidth(310);
		grid.add(addPassword, 2, 15, 15, 2);
		GridPane.setHalignment(addPassword, HPos.CENTER);
		GridPane.setValignment(addPassword, VPos.CENTER);

		// Button Update
		Button cancel = new Button("Cancel");
		cancel.setPrefSize(110, 20);
		addPassword.minWidth(310);
		grid.add(cancel, 16, 15, 3, 2);
		GridPane.setHalignment(cancel, HPos.CENTER);
		GridPane.setValignment(cancel, VPos.CENTER);
		
		/**************
		 * Operations *
		 **************/
		
		// fill company list
		companyList = FXCollections.<Company>observableArrayList(companies);
		companyListView.getItems().addAll(companyList);
		companyListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		// Fill Password Length Box
		ObservableList<Integer> lengthList = FXCollections.observableArrayList(
				8, 16, 32, 64, 128, 240);
		lengthPickerBox.getItems().addAll(lengthList);
		lengthPickerBox.getSelectionModel().select(1);
		
		// Update company list when user types into search box
		searchBox.textProperty().addListener((observable, oldval, newval) -> {
			if (searchBox.getText() == "" || searchBox.getText() == null) {
				companies = dbs.getCompanyList();
			} else {
				companies = dbs.searchCompanyByName(searchBox.getText());
			}

			companyListView.getItems().clear();
			companyList = FXCollections.<Company>observableArrayList(companies);
			companyListView.getItems().addAll(companyList);
		});
		
		// Button Actions
		generatePassword.setOnAction(e-> {
			SecurityService securityService = new SecurityService();
			
			passwordText.setText(
					securityService.generateSecurePassword(
							lengthPickerBox.getSelectionModel().getSelectedItem()));
			passwordHash.setText(securityService.encrypt(
					passwordText.getText(), 
					securityService.loadPrivateKey()));
		});
		
		addPassword.setOnAction(e-> {
			
			Alert alert = null;
			
			/*
			 * Check if user meets the following requirements before
			 * adding new password to database.
			 * 
			 * 	1. Company is selected.
			 * 	2. Username entered.
			 * 	3. Email entered.
			 * 	4. Verify Email entered.
			 * 	5. Password generated.
			 * 
			 */
			
			if (companyListView.getSelectionModel().isEmpty()) {
				alert = new Alert(AlertType.ERROR, "Must choose a company to add the new password.", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			
			if (usernameText.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR, "Must enter a username.", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			
			if (emailText.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR, "Must enter an email address.", ButtonType.OK);
				alert.showAndWait();
				return;
			} else {
				if (!emailText.getText().contains("@")) {
					alert = new Alert(AlertType.ERROR, "Must enter a valid email address.", ButtonType.OK);
					alert.showAndWait();
					return;
				}
			}
			
			if (passwordText.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR, "Must generate a password before trying to add a password.", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			
			/*
			 * Now that the checks have assured smooth insertion, add new password to database.
			 * When the operation completes, ask user if they want to add another. If so,
			 * clear the fields.
			 * 
			 * We don't want to just reload the view for two reasons:
			 * 
			 * 	1. Cut down on number of scene objects waiting for GC.
			 * 	2. To keep the same company selected in case user needs to add
			 * 		multiple passwords for the same site. 
			 * 
			 */
			DatabaseService databaseService = new DatabaseService();
			
			Password password = new Password(
					null, 
					companyListView.getSelectionModel().getSelectedItem().getCompanyId(), 
					usernameText.getText(), 
					emailText.getText(), 
					passwordHash.getText(), 
					"");
			
			databaseService.createNewPassword(password.getCompanyId(), password);
			
			if (databaseService.getLastPasswordRecord().getUserPassword().equals(passwordHash.getText())) {
				alert = new Alert(AlertType.CONFIRMATION, "Password added sucessfully!\n\nWould you like to add another?", ButtonType.OK, ButtonType.NO);
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.OK) {
					// Clear and do another
					usernameText.clear();
					
					emailText.clear();
					verifyEmailText.clear();

					emailText.setStyle("-fx-control-inner-background: white");
					verifyEmailText.setStyle("-fx-control-inner-background: white");
					
					passwordText.clear();
					passwordHash.clear();
				} else {
					MemoryVault.root.setCenter(
							MemoryVault.viewController.main());
				}
			} else {
				// Passsword not created successfully.
				
			}
		});
		
		// Email verification handled through listener
		verifyEmailText.textProperty().addListener((observable, oldval, newval) -> {
			if (!emailText.getText().equals(verifyEmailText.getText())) {
				verifyEmailText.setStyle("-fx-control-inner-background: lightcoral;");
				emailText.setStyle("-fx-control-inner-background: lightcoral;");
			} else {
				verifyEmailText.setStyle("-fx-control-inner-background: lightgreen;");
				emailText.setStyle("-fx-control-inner-background: lightgreen;");
			}
		});

		emailText.textProperty().addListener((observable, oldval, newval) -> {
			if (!emailText.getText().equals(verifyEmailText.getText())) {
				verifyEmailText.setStyle("-fx-control-inner-background: lightcoral;");
				emailText.setStyle("-fx-control-inner-background: lightcoral;");
			} else {
				verifyEmailText.setStyle("-fx-control-inner-background: lightgreen;");
				emailText.setStyle("-fx-control-inner-background: lightgreen;");
			}
		});
		
		cancel.setOnAction(e-> {
			MemoryVault.root.setCenter(
					MemoryVault.viewController.main());
		});

		this.getChildren().add(grid);
	}
}