/**
 * 
 * Main, Version 0.3.2 (Alpha)
 * 
 * @author Jay Wilson
 *
 * Latest Update: (Minor) Removed refresh methods.
 * 
 */
package net.mrjaywilson.memoryvault.views;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import net.mrjaywilson.memoryvault.controls.AppLabel;
import net.mrjaywilson.memoryvault.controls.CompanyList;
import net.mrjaywilson.memoryvault.controls.Grid;
import net.mrjaywilson.memoryvault.controls.PasswordTable;
import net.mrjaywilson.memoryvault.controls.SearchBox;
import net.mrjaywilson.memoryvault.main.MemoryVault;
import net.mrjaywilson.memoryvault.models.Company;
import net.mrjaywilson.memoryvault.models.Password;
import net.mrjaywilson.memoryvault.services.DatabaseService;

public class Main extends Pane {

	private static ArrayList<Company> companies = null;
	public static ObservableList<Company> companyList = null;
	private static ObservableList<Password> passwords = null;
	private Alert alert = null;
	
	public Main() {
		GridPane grid = new Grid();
		DatabaseService dbs = new DatabaseService();
		companies = dbs.getCompanyList();

		// For testing
		// grid.setGridLinesVisible(true);
		
		// Main labeling
		Label searchTitle = new AppLabel("Search For Or Choose A Company", 630, 24);
		grid.add(searchTitle, 2, 0, 18, 2);
		GridPane.setValignment(searchTitle, VPos.CENTER);
		GridPane.setHalignment(searchTitle, HPos.LEFT);

		// Input box for searching company name
		TextField searchBox = new SearchBox();
		grid.add(searchBox, 1, 2, 18, 1);
		GridPane.setValignment(searchBox, VPos.CENTER);
		GridPane.setHalignment(searchBox, HPos.CENTER);

		// List for all the companies to choose from
		ListView<Company> companyListView = new CompanyList();
		grid.add(companyListView, 1, 3, 18, 5);
		GridPane.setValignment(companyListView, VPos.CENTER);
		GridPane.setHalignment(companyListView, HPos.CENTER);

		// Password Title
		Label passwordTitle = new AppLabel("Available Passwords", 630, 24);
		grid.add(passwordTitle, 2, 8, 18, 2);
		GridPane.setValignment(searchTitle, VPos.CENTER);
		GridPane.setHalignment(searchTitle, HPos.LEFT);
		
		// List for all the passwords associated with selected company
		TableView<Password> passwordList = new PasswordTable();
		grid.add(passwordList, 1, 10, 18, 6);
		GridPane.setValignment(passwordList, VPos.CENTER);
		GridPane.setHalignment(passwordList, HPos.CENTER);

		// fill company list
		companyList = FXCollections.<Company>observableArrayList(companies);
		companyListView.getItems().addAll(companyList);
		companyListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		// Setup context menu for company list
		ContextMenu contextCompanyMenu = new ContextMenu();
		MenuItem edit = new MenuItem("Edit List");
		MenuItem deleteCompany = new MenuItem("Delete Company");
		MenuItem newCompany = new MenuItem("New Company");

		contextCompanyMenu.getItems().addAll(edit, newCompany, deleteCompany);
		
		// What to do when clicking edit list
		edit.setOnAction(e -> {
			MemoryVault.root.setCenter(
					MemoryVault.viewController.editCompany());
		});
		
		deleteCompany.setOnAction(e-> {
			alert = new Alert(AlertType.WARNING, "WARNING: this will delete company and all associated passwords. Cotinue?", ButtonType.YES, ButtonType.CANCEL);
			
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {
				alert = new Alert(AlertType.WARNING, "Are you sure?", ButtonType.YES, ButtonType.CANCEL);
				
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.YES) {
					// Do third dialog and ask for master password
					dbs.deleteCompanyRecord(companyListView.getSelectionModel().getSelectedItem().getCompanyId());
					MemoryVault.root.setCenter(
							MemoryVault.viewController.main());
				} else {
					return;
				}
			} else {
				return;
			}
		});
		
		// What to do when clicking edit list
		newCompany.setOnAction(e -> {
			MemoryVault.root.setCenter(
					MemoryVault.viewController.newCompany());
		});

		// Fill the table view with user data when company is clicked
		companyListView.setCellFactory(lv -> {
			ListCell<Company> cell = new ListCell<Company>() {
				@Override
				protected void updateItem(Company company, boolean empty) {
					super.updateItem(company, empty);

					if (empty) {
						setText(null);
					} else {
						setText(company.toString());
					}
				}
			};
			cell.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
				if (e.getButton() == MouseButton.PRIMARY && (!cell.isEmpty())) {
					passwords = FXCollections
							.observableArrayList(dbs.getPasswordListByCompanyId(cell.getItem().getCompanyId()));

					passwordList.setItems(passwords);
				}
				
				if (e.getButton() == MouseButton.SECONDARY) {
					contextCompanyMenu.show(passwordList, e.getScreenX(), e.getScreenY());
				}
			});
			return cell;
		});
		
		// Setup context menu for table view
		ContextMenu contextMenu = new ContextMenu();
		MenuItem details = new MenuItem("Details");
		MenuItem copyPassword = new MenuItem("Copy Password");
		MenuItem delete = new MenuItem("Delete Password");

		contextMenu.getItems().addAll(details, copyPassword, delete);

		// Add Mouse event to table view
		passwordList.addEventHandler(
				MouseEvent.MOUSE_CLICKED, 
				new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.PRIMARY) {
					// do nothing at the moment
				} else if (e.getButton() == MouseButton.SECONDARY && !passwordList.getItems().isEmpty()
						&& passwordList.getSelectionModel().getSelectedItem() != null) {
					contextMenu.show(passwordList, e.getScreenX(), e.getScreenY());
				}
			}
		});

		details.setOnAction(e -> {
			// TODO: Go to details view
			Password password = passwordList.getSelectionModel().getSelectedItem();
			MemoryVault.root.setCenter(
					MemoryVault.viewController.detailView(password));
/*			Password pass = passwordList.getSelectionModel().getSelectedItem();
			Alert detailsAlert = new Alert(AlertType.INFORMATION,
					"TEMP INFO: \n" + pass.getCompanyId() + "\n" + pass.getUserEmail() + "\n" + pass.getUserPassword(),
					ButtonType.OK);
			detailsAlert.show();
*/		});

		copyPassword.setOnAction(e -> {
			// TODO: copy selected password to clipboard
			Alert detailsAlert = new Alert(AlertType.INFORMATION, "TODO: copy selected password to clipboard",
					ButtonType.OK);
			detailsAlert.show();
		});

		delete.setOnAction(e -> {
			alert = new Alert(AlertType.INFORMATION, "DANGER: If you delete this password, is cannot be recovered! Continue?",
					ButtonType.OK, ButtonType.CANCEL);
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.OK) {
				alert = new Alert(AlertType.INFORMATION, "DANGER: Are you sure?",
						ButtonType.YES, ButtonType.CANCEL);
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.YES) {
					DatabaseService databaseService = new DatabaseService();
					databaseService.deletePasswordRecord(passwordList.getSelectionModel().getSelectedItem().getPasswordId());
					
					passwords = FXCollections
							.observableArrayList(dbs.getPasswordListByCompanyId(companyListView.getSelectionModel().getSelectedItem().getCompanyId()));

					passwordList.setItems(passwords);
				} else {
					return;
				}
			} else {
				return;
			}
			
		});

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
		
		// New view when details pressed for password
		// Copy button to quick-copy decrypted password
		// View password to decrypt right there, opens
		// a new "textinputdialog" to allow copying/viewing
		// password
		this.getChildren().add(grid);
	}
}
