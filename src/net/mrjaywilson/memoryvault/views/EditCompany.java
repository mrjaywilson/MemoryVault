/**
 * 
 * EditCompany, Version 0.1.0 (Alpha)
 * 
 * @author Jay Wilson
 *
 * Latest Update: (Minor) None.
 * 
 */
package net.mrjaywilson.memoryvault.views;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.mysql.cj.xdevapi.Result;
import com.sun.corba.se.spi.orbutil.fsm.Action;
import com.sun.javafx.scene.control.skin.ButtonSkin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import net.mrjaywilson.memoryvault.controls.AppLabel;
import net.mrjaywilson.memoryvault.controls.CompanyTable;
import net.mrjaywilson.memoryvault.controls.Grid;
import net.mrjaywilson.memoryvault.main.MemoryVault;
import net.mrjaywilson.memoryvault.models.Company;
import net.mrjaywilson.memoryvault.services.DatabaseService;
import sun.management.counter.Variability;

public class EditCompany extends Pane {
	
	private GridPane grid = new Grid();
	private DatabaseService dbs = new DatabaseService();
	
	protected TextField companyNameText;
	protected TextField companyUrlText;
	protected ObservableList<Company> companies;
	protected Company company;
	
	@SuppressWarnings("unchecked")
	public EditCompany() {
		
		// For testing
		//grid.setGridLinesVisible(true);
		
		// Function Title TEMP: NEED OWN STYLING
		Label title = new AppLabel("Edit Company List", 500, 25);
		title.setTextAlignment(TextAlignment.CENTER);
		grid.add(title, 6, 0, 18, 2);

		// Table of data
		TableView<Company> companyList = new CompanyTable();
		companyList.setMinHeight(490);
		grid.add(companyList, 1, 2, 9, 14);
		GridPane.setValignment(companyList, VPos.CENTER);
		GridPane.setHalignment(companyList, HPos.CENTER);
		
		// Fill the Table with data
		companies = FXCollections.observableArrayList(dbs.getCompanyList());
		
		companyList.setItems(companies);
		
		// Set what to do when the company list is clicked
		companyList.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				
				company = dbs.getCompanyRecord(
						companyList.getSelectionModel().getSelectedItem().getCompanyId()); 
				companyNameText.setText(
						company.getCompanyName());
				companyUrlText.setText(
						company.getCompanyUrl());
			}
		});
		
		// Label Company Name
		Label companyNameLabel = new AppLabel("Company Name", 285, 25);
		grid.add(companyNameLabel, 11, 2, 8, 2);
		
		// TextField Company Name
		companyNameText = new TextField();
		companyNameText.setPromptText("New Company Name");
		companyNameText.setPrefSize(285, 35);
		grid.add(companyNameText, 11, 4, 8, 1);
		
		// Label Company Web Address
		Label companyUrlLabel = new AppLabel("Company URL", 285, 25);
		grid.add(companyUrlLabel, 11, 5, 8, 2);
		
		// TextField Company Web Address
		companyUrlText = new TextField();
		companyUrlText.setPromptText("New Company URL");
		companyUrlText.setPrefSize(285, 35);
		grid.add(companyUrlText, 11, 7, 8, 1);
		
		// Button Update
		Button update = new Button("Update Selected Record");
		update.setPrefSize(285, 20);
		update.minWidth(285);
		grid.add(update, 11, 9, 8, 1);
		GridPane.setHalignment(update, HPos.CENTER);
		GridPane.setValignment(update, VPos.CENTER);
		
		// Button Cancel
		Button done = new Button("Done");
		done.setPrefSize(310, 20);
		done.minWidth(310);
		grid.add(done, 11, 10, 8, 1);
		GridPane.setHalignment(done, HPos.CENTER);
		GridPane.setValignment(done, VPos.CENTER);
		
		// Button Actions
		update.setOnAction(e -> {
			Alert alert = null;
			
			if (companyNameText.getText().length() < 1) {
				alert = new Alert(AlertType.ERROR, "Company must have a name. Please choose a name to continue.", ButtonType.OK);
				alert.showAndWait();
				
				return;
			}
			
			if (companyUrlText.getText() == "") {
				alert = new Alert(AlertType.WARNING, "Company address not required, but it is recommended. Continue anyway?", ButtonType.OK, ButtonType.CANCEL);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.CANCEL) {
					return;
				}
			}
			
			if (company == null) {
				company = new Company(
						null,
						companyNameText.getText(),
						companyUrlText.getText());
				
				dbs.createNewCompany(company);
			} else {
				company.setCompanyName(
						companyNameText.getText());
				company.setCompanyUrl(
						companyUrlText.getText());
				
				dbs.updateCompanyInfo(
						company.getCompanyId(), 
						company);
			}
			
			companyList.getItems().clear();
			
			companies = FXCollections.observableArrayList(
							dbs.getCompanyList());
			
			companyList.setItems(companies);
		});
		
		done.setOnAction(e-> {
			MemoryVault.root.setCenter(
					MemoryVault.viewController.main());
		});
		
		this.getChildren().add(grid);
	}
}
