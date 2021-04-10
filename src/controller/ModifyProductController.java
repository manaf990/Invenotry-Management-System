package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * This Class Manages the Modify Product Form.
 * @author Manaf
 */
public class ModifyProductController implements Initializable {
    private int index=0; // index of product in list
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList(); // a list to hold associated parts
    private Alert alert;

    // Private fields for components
    @FXML
    private TextField ProductIDtxt;
    @FXML
    private TextField addProductNametxt;
    @FXML
    private TextField addProductInvtxt;
    @FXML
    private TextField addProductPricetxt;
    @FXML
    private TextField addProductMaxtxt;
    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField searchPartText;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn <Part, Double>partPriceCol;

    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol1;
    @FXML
    private TableColumn<Part, String> partNameCol1;
    @FXML
    private TableColumn<Part, String> partInvCol1;
    @FXML
    private TableColumn<Part, Double> partPriceCol1;

    /**
     * This method implements initialize from Initializable interface method,
     * and initializes the modifyProductController Class.
     * @param url not used
     * @param resourceBundle not used
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // gets all parts from inventory and populate the parts table in form.
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Takes user input and searches for a part in inventory using either part ID or Name.
     */
    @FXML
    public void onActionSearchPart() {
        //get the input from search text field.
        String  input= searchPartText.getText();
        // check if part is available in inventory, and present it in part table
        if (!Inventory.lookupPart(input).isEmpty())
        {
            partTableView.setItems(Inventory.lookupPart(input));
        }
        //check if input is an Integer, and if the part ID is available in inventory,
        // and present the part in parts table
        else if  (MainMenuController.isParsable(input) && Inventory.lookupPart(Integer.parseInt(input)) != null) {
            int partId= Integer.parseInt(input);
            partTableView.getSelectionModel().select(Inventory.lookupPart(partId));
        }
        else {
            // show a warning that part is not found
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Not Found");
            alert.showAndWait();
        }
    }

    /**
     * Adds the selected part to the associated parts of the product.
     */
    @FXML
    public void onActionAddAssociatedPart() {
        // if add button is clicked, add the selected part to associated parts list.
        if (!partTableView.getSelectionModel().isEmpty())
        {
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
            associatedParts.add(selectedPart);
            // present the added part in associated parts table
            associatedPartsTableView.setItems(associatedParts);
            partIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        }
        else {
            // show a warning that no item is selected
            alert.setContentText("No Item is selected");
            alert.showAndWait();
        }


    }

    /**
     * Removes the selected part from the associated parts of the product.
     */
    @FXML
    public void onActionRemoveAssociatedPart() {
        // if remove button is clicked, remove the selected part from the associated parts list.
        if (!associatedPartsTableView.getSelectionModel().isEmpty())
        {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove part?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Part part = associatedPartsTableView.getSelectionModel().getSelectedItem();
                associatedParts.remove(part);
            }

        }
        // show a warning that no item is selected
        else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No Item is selected");
            alert.showAndWait();
        }
    }

    /**
     * Saves the changes made on a product and switches to the main menu
     * @param actionEvent handles the save button event.
     * @throws IOException if inputs cause errors
     */
    @FXML
    public void onActionSaveProduct(ActionEvent actionEvent) throws IOException {
        // create a warning for invalid inputs
        Alert alert = new Alert((Alert.AlertType.WARNING));
        alert.setResizable(true);
        alert.setHeaderText("Invalid Input");

        try {
            // check if inputs are not null
            if (addProductNametxt.getText().isEmpty()) throw new IOException("Please input Name");
            if (addProductInvtxt.getText().isEmpty()) throw new IOException("Please input Inv");
            if (addProductPricetxt.getText().isEmpty()) throw new IOException("Please input Price");
            //getting inputs from text fields and assign them to variables
            String name = addProductNametxt.getText();
            int id = Integer.parseInt(ProductIDtxt.getText());
            int stock = Integer.parseInt(addProductInvtxt.getText());
            double price = Double.parseDouble(addProductPricetxt.getText());
            int max = Integer.parseInt(addProductMaxtxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());

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
                // create new product and add it and its associated parts to inventory.
                Product product = new Product(id, name, price, stock, min, max);
                product.getAllAssociatedParts().addAll(associatedParts);
                Inventory.updateProduct(index, product);

                // return to main menu
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(NumberFormatException e) {
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (IOException i) {
            JOptionPane.showMessageDialog(null,
                    i.getMessage());
        }
    }

    /**
     * Receives the selected product and its associated parts
     * and present them in the modify product form
     * @param product the product to be presented in the modify part form
     * @param itsAssociatedParts the product's associated parts to be presented in the modify part form
     */
    public void sendProduct(Product product, ObservableList<Part> itsAssociatedParts){
        // gets the product fields and present them in form
        ProductIDtxt.setText(String.valueOf(product.getId()));
        addProductNametxt.setText(product.getName());
        addProductInvtxt.setText(String.valueOf(product.getStock()));
        addProductPricetxt.setText(String.valueOf(product.getPrice()));
        addProductMaxtxt.setText(String.valueOf(product.getMax()));
        addProductMinTxt.setText(String.valueOf(product.getMin()));
        // gets the index of product in the list in inventory and assign in to the index field
        index = Inventory.getAllProducts().indexOf(product);
        //letting the associated parts list reference the list of associated parts of the product
        //which was received.
        associatedParts = itsAssociatedParts;
        // populating the associated parts table with all associated parts from the list.
        associatedPartsTableView.setItems(associatedParts);
        partIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));



    }

    /**
     * This method cancels the part form inputs after confirming with the user,
     * and returns to the main menu form after that.
     * @param actionEvent handles the cancel button event.
     * @throws IOException if inputs cause errors
     * */
    @FXML
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        // calling the cancel method from addPartController class.
        AddPartController ADC = new AddPartController();
        ADC.onActionAddPartCancel(actionEvent);
    }


}
