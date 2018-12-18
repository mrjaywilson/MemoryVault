/**
 * 
 * NewCompany, Version 0.1.0 (Alpha)
 * 
 * @author Jay Wilson
 *
 * Latest Update: (Minor) Removed code as this class may be removed in favor of having only one.
 * 
 */
package net.mrjaywilson.memoryvault.views;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.xml.internal.bind.v2.model.core.NonElement;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.mrjaywilson.memoryvault.controls.AppLabel;
import net.mrjaywilson.memoryvault.controls.Grid;
import net.mrjaywilson.memoryvault.main.MemoryVault;
import net.mrjaywilson.memoryvault.models.Company;
import net.mrjaywilson.memoryvault.services.DatabaseService;

public class NewCompany extends Pane {
	protected Company company = null;

	GridPane grid = new Grid();
	DatabaseService dbs = new DatabaseService();

	public NewCompany() {
		// Function Title TEMP: NEED OWN STYLING
		Label title = new AppLabel("New Company", 500, 25);
		title.setTextAlignment(TextAlignment.CENTER);
		grid.add(title, 6, 0, 18, 2);

		// Label Company Name
		Label companyNameLabel = new AppLabel("Company Name", 285, 24);
		grid.add(companyNameLabel, 2, 2, 18, 1);

		// TextField Company Name
		TextField companyNameText = new TextField();
		companyNameText.setPromptText("New Company Name");
		companyNameText.setPrefSize(285, 35);
		grid.add(companyNameText, 1, 3, 18, 1);

		// Label Company Web Address
		Label companyUrlLabel = new AppLabel("Company URL (address)", 400, 24);
		grid.add(companyUrlLabel, 2, 5, 18, 1);

		// TextField Company Web Address
		TextField companyUrlText = new TextField();
		companyUrlText.setPromptText("New Company URL");
		companyUrlText.setPrefSize(285, 35);
		grid.add(companyUrlText, 1, 6, 18, 1);

		// Button Update
		Button addNew = new Button("Add New Company");
		addNew.setPrefSize(310, 20);
		addNew.minWidth(310);
		grid.add(addNew, 1, 8, 9, 1);
		GridPane.setHalignment(addNew, HPos.CENTER);
		GridPane.setValignment(addNew, VPos.CENTER);

		// Button Cancel
		Button done = new Button("Done");
		done.setPrefSize(310, 20);
		done.minWidth(310);
		grid.add(done, 11, 8, 8, 1);
		GridPane.setHalignment(done, HPos.CENTER);
		GridPane.setValignment(done, VPos.CENTER);

		// Label Company Name
		Label statusLabel = new AppLabel("Status", 285, 24);
		grid.add(statusLabel, 2, 10, 18, 1);

		// Status
		TextArea status = new TextArea();
		status.setPromptText("Status...");
		status.setEditable(false);
		status.setPrefSize(285, 35);
		grid.add(status, 1, 11, 18, 5);

		// Button Actions
		// Need to check if name exists before adding
		// Company already exists, would you like to update record?
		addNew.setOnAction(e -> {
			Alert alert = null;

			if (!companyNameText.getText().isEmpty()) {
				if (companyUrlText.getText().isEmpty()) {
					alert = new Alert(AlertType.WARNING,
							"Warning: Company Address not required, but it is recommended. 'OK' to continue, 'CANCEL' to add address.",
							ButtonType.OK, ButtonType.CANCEL);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.CANCEL) {
						return;
					}
				}

				// Check if company already exists
				ArrayList<Company> companyCheck = dbs.searchCompanyByName(companyNameText.getText());
				StringBuilder sBuilder = new StringBuilder();

				if (!companyCheck.isEmpty()) {
					
					company = dbs.getCompanyByName(companyNameText.getText());
					
					if (company != null) {
						sBuilder.append("Warning: Possible company record(s) already found.\n\n");
						
						sBuilder.append(company.getCompanyName() + "\n");
						
						sBuilder.append("\nWould you like to update company with new info?");

						alert = new Alert(AlertType.WARNING, sBuilder.toString(), ButtonType.YES, ButtonType.NO);
						alert.showAndWait();
						
						if (alert.getResult() == ButtonType.YES) {
							company.setCompanyName(companyNameText.getText());
							company.setCompanyUrl(companyUrlText.getText());
							
							dbs.updateCompanyInfo(company.getCompanyId(), company);
							
							return;
						} else {
							status.appendText("\n\nCompany list not updated or altered.\n\n");
							return;
						}
					}
						sBuilder.append("Possible conflict with another company found. Review records below and choose 'OK' to add new company or 'CANCEL' to review company information again.\n\n");

						for (Company company : companyCheck) {
							sBuilder.append(company.getCompanyName() + "\n");
						}

						alert = new Alert(AlertType.WARNING, sBuilder.toString(), ButtonType.OK, ButtonType.CANCEL);
						alert.showAndWait();

						if (alert.getResult() == ButtonType.CANCEL) {
							return;
						}
				}

				company = new Company(null, companyNameText.getText(), companyUrlText.getText());

				dbs.createNewCompany(company);
				String address = "";

				if (companyUrlText.getText().isEmpty()) {
					address = ("(none selected)");
				} else {
					address = company.getCompanyUrl();
				}
				
				status.appendText("New Company Added: " + company.getCompanyName() + '\n' + "With Address: ");
				
				if (address.isEmpty()) {
					status.appendText("(no address)\n\n");
				} else {
					status.appendText(address + "\n\n");
				}

				alert = new Alert(AlertType.INFORMATION, "Company added sucessfully!", ButtonType.CLOSE);
				alert.showAndWait();
			} else {
				alert = new Alert(AlertType.ERROR, "Company Name Required.", ButtonType.OK);
				alert.showAndWait();
			}
		});

		done.setOnAction(e -> {
			MemoryVault.root.setCenter(MemoryVault.viewController.main());
		});
		
		this.getChildren().add(grid);
	}
}
