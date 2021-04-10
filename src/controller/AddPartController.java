package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 *This Class Manages Add part form.
 * @author Manaf
 */
public class AddPartController implements Initializable {
    Stage stage; //the stage
    Parent scene; //the scene
    private static int id=0; // the ID

    // Private fields for components
    @FXML
    private RadioButton addPartInHouse;
    @FXML
    private RadioButton addPartOutsourced;
    @FXML
    private TextField addPartNametxt;
    @FXML
    private TextField addPartInvtxt;
    @FXML
    private TextField addPartPricetxt;
    @FXML
    private TextField addPartmaxtxt;
    @FXML
    private TextField addPartSourcetxt;
    @FXML
    private Label addPartSourcelbl;
    @FXML
    private TextField addPartMintxt;

    /**
     * This method sets the part source label to Machine ID when In-House radio button is selected.
     * */
    @FXML
    public void onActionSetPartSourceMachineID() {
        addPartSourcelbl.setText("Machine ID");
    }
    /**
     * This method sets the part source label to Company Name when Outsourced radio button is selected.
     * */
    @FXML
    public void onActionSetPartSourceICompanyName() {
        addPartSourcelbl.setText("Company Name");
    }

    /**
     * This method takes the user inputs and saves the part in the inventory if the specified conditions are met,
     * and returns to the main menu form after that.
     * @param actionEvent handles the save button event.
     * @throws IOException if inputs cause errors
     * */
    @FXML
    public void onActionAddPartSave(ActionEvent actionEvent) throws IOException {
        //creates a warning
        Alert alert = new Alert((Alert.AlertType.WARNING));
        alert.setResizable(true);
        alert.setHeaderText("Invalid Input");

        try {
            // checks if the conditions are met.
            if (addPartNametxt.getText().isEmpty()) throw new IOException("Please input Name");
            if (addPartInvtxt.getText().isEmpty()) throw new IOException("Please input Inv");
            if (addPartPricetxt.getText().isEmpty()) throw new IOException("Please input Price");
            //getting inputs from text fields and assign them to variables
            String name = addPartNametxt.getText();
            int stock = Integer.parseInt(addPartInvtxt.getText());
            double price = Double.parseDouble(addPartPricetxt.getText());
            int max = Integer.parseInt(addPartmaxtxt.getText());
            int min = Integer.parseInt(addPartMintxt.getText());
            int machineId=0;
            String companyName="";
            //if Inhouse radio button is selected, get machine Id.
            if (addPartInHouse.isSelected()) {
                machineId = Integer.parseInt(addPartSourcetxt.getText());
            }
            // if outsourced radio button is selected, get company name
            else if (addPartOutsourced.isSelected())
                companyName = addPartSourcetxt.getText();
            // check if conditions are met
            if (stock > max || stock < min || stock < 0) {

                alert.setContentText("Inv should be between min and max and not less than zero");
                alert.showAndWait();
            }
            else if (max <= min || max <= 0 || min < 0) {
                alert.setContentText("Min should not be less than zero \n and Max value should be more than min");
                alert.showAndWait();
            }
            else {
                id++; //increment the ID
                // if inHouse radio button is selected, create new inHouse part and add it to inventory.
                if (addPartInHouse.isSelected()) {
                    InHouse inHouse= new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(inHouse);
                }
                // if Outsourced radio button is selected, create new Outsourced part and add it to inventory.
                else
                {
                    Outsourced outsourced = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(outsourced);
                }
                // create a stage
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                // load the Main menu FXML file
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                // set the scene in stage
                stage.setScene(new Scene(scene));
                //show stage
                stage.show();
            }
        }

        catch(NumberFormatException e)
        {
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (IOException name) {
            JOptionPane.showMessageDialog(null,
                    name.getMessage());
        }

    }

    /**
     * This method cancels the part form inputs after confirming with the user,
     * and returns to the main menu form after that.
     * @param actionEvent handles the cancel button event.
     * @throws IOException if inputs cause errors
     * */
    @FXML
    public void onActionAddPartCancel(ActionEvent actionEvent) throws IOException {
        //if cancel button is clicked, show a warning.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "info will be lost, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        //if OK button is clicked go back to main menu
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
             stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
             scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
             stage.setScene(new Scene(scene));
             stage.show();
        }
    }

    /**
     * This method implements initialize from Initializable interface method,
     * and initializes the addPartController Class.
     * @param url not used
     * @param resourceBundle not used
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //select the inhouse radio button
        addPartInHouse.setSelected(true);
        // set part source label to Machine ID
        addPartSourcelbl.setText("Machine ID");
    }
}
