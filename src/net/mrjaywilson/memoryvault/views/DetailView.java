package net.mrjaywilson.memoryvault.views;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import net.mrjaywilson.memoryvault.controls.AppLabel;
import net.mrjaywilson.memoryvault.controls.Grid;
import net.mrjaywilson.memoryvault.models.Company;
import net.mrjaywilson.memoryvault.models.Password;
import net.mrjaywilson.memoryvault.services.DatabaseService;
import net.mrjaywilson.memoryvault.services.SecurityService;

public class DetailView extends Pane {
	protected Company company;

	GridPane grid = new Grid();
	DatabaseService dbs = new DatabaseService();
	
	public DetailView(Password password) {
		//grid.setGridLinesVisible(true);
		
		DatabaseService databaseService = new DatabaseService();
		SecurityService securityService = new SecurityService();
		
		// Password label
		Label passwordDetailLabel = new AppLabel("PASSWORD DETAIL", 630, 24);
		passwordDetailLabel.setAlignment(Pos.CENTER);
		passwordDetailLabel.setFont(Font.font("Century Gothic", 32));
		grid.add(passwordDetailLabel, 1, 0, 17, 2);
		GridPane.setValignment(passwordDetailLabel, VPos.CENTER);
		GridPane.setHalignment(passwordDetailLabel, HPos.CENTER);
		
		// Company Labels
		Label companyLabel = new AppLabel("Company:", 600, 24);
		grid.add(companyLabel, 2, 3, 5, 1);
		GridPane.setValignment(companyLabel, VPos.CENTER);
		GridPane.setHalignment(companyLabel, HPos.LEFT);
		
		Label companyNameLabel = new AppLabel(databaseService.getCompanyRecord(password.getCompanyId()).getCompanyName(), 600, 24);
		grid.add(companyNameLabel, 7, 3, 10, 1);
		GridPane.setValignment(companyNameLabel, VPos.CENTER);
		GridPane.setHalignment(companyNameLabel, HPos.LEFT);

		// Company Site Labels
		Label companySiteLabel = new AppLabel("Website:", 600, 24);
		grid.add(companySiteLabel, 2, 5, 5, 1);
		GridPane.setValignment(companySiteLabel, VPos.CENTER);
		GridPane.setHalignment(companySiteLabel, HPos.LEFT);
		
		Label companyUrlLabel = new AppLabel(databaseService.getCompanyRecord(password.getCompanyId()).getCompanyUrl(), 600, 24);
		grid.add(companyUrlLabel, 7, 5, 10, 1);
		GridPane.setValignment(companyUrlLabel, VPos.CENTER);
		GridPane.setHalignment(companyUrlLabel, HPos.LEFT);

		// Username Labels
		Label usernameLabel = new AppLabel("Username:", 600, 24);
		grid.add(usernameLabel, 2, 7, 17, 1);
		GridPane.setValignment(usernameLabel, VPos.CENTER);
		GridPane.setHalignment(usernameLabel, HPos.LEFT);
		
		Label usernameTextLabel = new AppLabel(password.getUserName(), 600, 24);
		grid.add(usernameTextLabel, 7, 7, 10, 1);
		GridPane.setValignment(usernameTextLabel, VPos.CENTER);
		GridPane.setHalignment(usernameTextLabel, HPos.LEFT);
		
		// Email Address Labels
		Label emailLabel = new AppLabel("Email:", 600, 24);
		grid.add(emailLabel, 2, 9, 17, 1);
		GridPane.setValignment(emailLabel, VPos.CENTER);
		GridPane.setHalignment(emailLabel, HPos.LEFT);

		Label emailAddressLabel = new AppLabel(password.getUserEmail(), 600, 24);
		grid.add(emailAddressLabel, 7, 9, 10, 1);
		GridPane.setValignment(emailAddressLabel, VPos.CENTER);
		GridPane.setHalignment(emailAddressLabel, HPos.LEFT);
		
		// Password Label
		Label passwordLabel = new AppLabel("Password: ", 600, 140);
		grid.add(passwordLabel, 2, 11, 5, 1);
		GridPane.setValignment(passwordLabel, VPos.CENTER);
		GridPane.setHalignment(passwordLabel, HPos.LEFT);
		
		// Button to show the password
		Button showPassword = new Button("Show Password");
		showPassword.setPrefSize(105, 20);
		showPassword.minWidth(105);
		grid.add(showPassword, 6, 11, 4, 1);
		GridPane.setHalignment(showPassword, HPos.CENTER);
		GridPane.setValignment(showPassword, VPos.CENTER);

		// Text Area to contain the password
		TextArea passwordText = new TextArea();
		passwordText.setText(password.getUserPassword());
		passwordText.setWrapText(true);
		grid.add(passwordText, 2, 12, 17, 4);
		GridPane.setValignment(passwordText, VPos.CENTER);
		GridPane.setHalignment(passwordText, HPos.CENTER);
		
		showPassword.setOnAction(e-> {
			passwordText.setText(securityService.decrypt(
					password.getUserPassword(), 
					securityService.loadPublicKey()));
		});
		
		this.getChildren().add(grid);
	}
}