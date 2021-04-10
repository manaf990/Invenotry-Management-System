package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * This Class Manages the Modify Part Form.
 * @author Manaf
 */
public class ModifyPartController implements Initializable {
    private int index; // index of the part in list
    Stage stage;
    Parent scene;

    // Private fields for components
    @FXML
    private RadioButton modifyPartInHouse;
    @FXML
    private RadioButton modifyPartOutsourced;
    @FXML
    private TextField modifyPartIdtxt;
    @FXML
    private TextField modifyPartNametxt;
    @FXML
    private TextField modifyPartInvtxt;
    @FXML
    private TextField modifyPartPricetxt;
    @FXML
    private TextField modifyPartmaxtxt;
    @FXML
    private TextField modifyPartSourcetxt;
    @FXML
    private Label modifyPartSourcelbl;
    @FXML
    private TextField modifyPartMintxt;

    /**
     * Sets the part source label to Machine ID
     */
    @FXML
    public void onActionSetPartSourceMachineID() {
        modifyPartSourcelbl.setText("Machine ID");
    }

    /**
     * Sets the part source label to Company Name
     */
    @FXML
    public void onActionSetPartSourceICompanyName() {
        modifyPartSourcelbl.setText("Company Name");
    }
    /**
     * Takes the user inputs and saves the changes made on the selected part, then switches to main menu.
     * @param  actionEvent handles the Save button event.
     * @throws IOException if inputs cause errors
     */
    @FXML
    public void onActionModifyPartSave(ActionEvent actionEvent) throws IOException {
        // creates a warning for invalid inputs
        Alert alert = new Alert((Alert.AlertType.WARNING));
        alert.setResizable(true);
        alert.setHeaderText("Invalid Input");

        try {
            // checks that inputs are not null
            if (modifyPartNametxt.getText().isEmpty()) throw new IOException("Please input Name");
            if (modifyPartInvtxt.getText().isEmpty()) throw new IOException("Please input Inv");
            if (modifyPartPricetxt.getText().isEmpty()) throw new IOException("Please input Price");
            //getting inputs from text fields and assign them to variables
            int id = Integer.parseInt(modifyPartIdtxt.getText());
            String name = modifyPartNametxt.getText();
            int stock = Integer.parseInt(modifyPartInvtxt.getText());
            double price = Double.parseDouble(modifyPartPricetxt.getText());
            int max = Integer.parseInt(modifyPartmaxtxt.getText());
            int min = Integer.parseInt(modifyPartMintxt.getText());
            int machineId=0;
            String companyName="";
            //if Inhouse radio button is selected, get machine Id.
            if (modifyPartInHouse.isSelected()) {
                machineId = Integer.parseInt(modifyPartSourcetxt.getText());
            }
            // if outsourced radio button is selected, get company name
            else if (modifyPartOutsourced.isSelected())
                companyName = modifyPartSourcetxt.getText();
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
                // if inHouse radio button is selected, create new inHouse part and add it to inventory.
                if (modifyPartInHouse.isSelected()) {
                    InHouse inHouse= new InHouse(id, name, price, stock, min, max, machineId );
                    Inventory.updatePart(index, inHouse);
                }
                else
                {
                    // if Outsourced radio button is selected, create new Outsourced part and add it to inventory.
                    Outsourced outsourced = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(index, outsourced);
                }
                // return to main menu form
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
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
    public void onActionModifyPartCancel(ActionEvent actionEvent) throws IOException {
        // calling the cancel method from addPartController class.
        AddPartController AdC = new AddPartController();
        AdC.onActionAddPartCancel(actionEvent);
    }

    /**
     * Receives the selected part
     * and present it in the modify part form
     * @param part the part to be presented in the modify part form
     */
    public void sendPart(Part part){
            //checks if the part is inHouse part
            if (part instanceof InHouse) {
                //selects the inHouse radio button
                modifyPartInHouse.setSelected(true);
                //sets the part source label to Machine ID
                modifyPartSourcelbl.setText("Machine ID");
                //gets the value of machine ID present it in form
                modifyPartSourcetxt.setText(String.valueOf(((InHouse) part).getMachineId()));
            } else {
                //selects the Outsourced radio button
                modifyPartOutsourced.setSelected(true);
                //sets the part source label to Company Name
                modifyPartSourcelbl.setText("Company Name");
                //get the name company and present it in form
                modifyPartSourcetxt.setText(((Outsourced) part).getCompanyName());

            }
            // gets the part fields and present them in form
            modifyPartIdtxt.setText(String.valueOf(part.getId()));
            modifyPartNametxt.setText(part.getName());
            modifyPartInvtxt.setText(String.valueOf(part.getStock()));
            modifyPartPricetxt.setText(String.valueOf(part.getPrice()));
            modifyPartmaxtxt.setText(String.valueOf(part.getMax()));
            modifyPartMintxt.setText(String.valueOf(part.getMin()));
            // gets the index of part in the list in inventory and assign in to the index field
            index = Inventory.getAllParts().indexOf(part);



    }

    /**
     * This method implements initialize from Initializable interface method,
     * and initializes the modifyPartController Class.
     * @param url not used
     * @param resourceBundle not used
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
