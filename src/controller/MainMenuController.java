package controller;

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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 *This Class Manages the Main Menu form
 * @author Manaf
 */
public class MainMenuController implements Initializable {

    Stage stage; // the stage
    Parent scene; // the scene
    Alert alert; // Alert

    // Private fields for components
    @FXML
    private TextField searchProductText;
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
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /**
     * This method implements initialize from Initializable interface method,
     * and initializes the MainMenuController Class.
     * @param url not used
     * @param resourceBundle not used
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate the part table with all parts
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        // populate the product table with all parts
        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * This method switches the user to the Add Part Form.
     * @param actionEvent handles the add part button event
     * @throws IOException if inputs cause errors
     */
    @FXML
    public void onActionAddPart(ActionEvent actionEvent) throws IOException {
        // switch to add part form if add part button is clicked
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * This method takes the selected part and switches the user to the Modify Part Form.
     * @param actionEvent handles the modify part button event
     * @throws IOException if inputs cause errors
     */
    @FXML
    public void onActionModifyPart(ActionEvent actionEvent) throws IOException {
        // load the modify part form
        if(!partTableView.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();
            // send the selected part to the form
            ModifyPartController modify = loader.getController();
            modify.sendPart(partTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        // show a warning that no item is selected
        else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No Item is selected");
            alert.showAndWait();
        }

    }
     /**
     * This method deletes the selected part.
     */
    @FXML
    public void onActionDeletePart() {
        if(!partTableView.getSelectionModel().isEmpty()) {
        // confirms that user want to delete the part selected
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?" );
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            // remove the selected part from inventory
        Inventory.getAllParts().remove(partTableView.getSelectionModel().getSelectedItem());
        }
        else {
            // show a warning that no item is selected
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No Item is selected");
            alert.showAndWait();
        }
    }

    /**
     * This method switches the user to the Add Part Product form.
     * @param actionEvent handles the add product button event
     * @throws IOException if inputs cause errors
     */
    @FXML
    public void onActionAddProduct(ActionEvent actionEvent) throws IOException{
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * This method takes the selected product and switches the user to the Modify Product Form.
     * @param actionEvent handles the modify product button event
     * @throws IOException if inputs cause errors
     */
    @FXML
    public void onActionModifyProduct(ActionEvent actionEvent) throws IOException{
        //checks if a product is selected
        if(!productTableView.getSelectionModel().isEmpty()) {
            // creates reference variables for the selected product and its Associated Parts
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            ObservableList<Part> itsAssociatedParts = selectedProduct.getAllAssociatedParts();
            //load the modify product form
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();
            // sends the selected product to the form.
            ModifyProductController modify = loader.getController();
            modify.sendProduct(selectedProduct, itsAssociatedParts);
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            // show a warning that no item is selected
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No Item is selected");
            alert.showAndWait();
        }
    }

    /**
     * This method deletes the selected product.
     */
    @FXML
    public void onActionDeleteProduct() {
        //checks if a product is selected
        if(!productTableView.getSelectionModel().isEmpty()) {
            //checks if a product has associated parts
            if (!productTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
                //shows a warning that a product can not be removed
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Product can not be removed because there are parts associated \n" +
                        "with it, please remove the association first");
                alert.setResizable(true);
                alert.showAndWait();

            }
            else {
                //confirms if the user wants to delete the selected product
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                    Inventory.getAllProducts().remove(productTableView.getSelectionModel().getSelectedItem());
            }
        }
                else {
            // show a warning that no item is selected
            alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No Item is selected");
                alert.showAndWait();
            }

    }

    /**
     * This method terminates the program.
     */
    @FXML
    public void onActionExit() {
        System.exit(0);
    }

    /**
     * This method takes the user input and searches for a part using either Part ID or Name
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
            else if  ( isParsable(input) && Inventory.lookupPart(Integer.parseInt(input)) != null) {
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
     * This method takes a string and check if it's parsable to integer or not
     * @param input the string to be checked
     * @return true if input is parsable, and false if not parsable.
     */
    public static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch
        (final NumberFormatException e) {
            return false;
        }

    }
    /**
     * This method takes the user input and searches for a product using either Product ID or Name
     */
    @FXML
    public void onActionSearchProduct() {
        //get the input from search text field.
        String  input= searchProductText.getText();
        //check if a product is selected
        if (!Inventory.lookupProduct(input).isEmpty())
        {
            //populate the product table with the found products
            productTableView.setItems(Inventory.lookupProduct(input));
        }
    //checks if the input is an integer(ID), and if it is available in inventory
        else if  ( isParsable(input) && Inventory.lookupProduct(Integer.parseInt(input)) != null) {
            int productId= Integer.parseInt(input);
            //populate the product table with the found product
            productTableView.getSelectionModel().select(Inventory.lookupProduct(productId));
        }
        else {
            // show a warning that product is not found
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Not Found");
            alert.showAndWait();
        }
    }

}
