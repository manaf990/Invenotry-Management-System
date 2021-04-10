package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This Class manages Products and their associated parts and it is dependant on the Part Class
 * @author Manaf
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    //private fields for product properties
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.max = max;
        this.min = min;

    }

    /**
     * This method returns the ID
     * @return the ID
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the ID
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * This method returns the name
     * @return The name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the Name
     * @param name the Name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     *This method returns the Price
     * @return The Price
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets the Price
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     *This method returns the Stock
     * @return Stock
     */
    public int getStock() {
        return stock;
    }
    /**
     * sets the stock
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     *This method returns the Minimum value
     * @return The Minimum Value
     */
    public int getMin() {
        return min;
    }
    /**
     * Sets the Minimum Value
     * @param min the Minimum Value to set
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * This method returns the Maximum Value
     * @return The Maximum Value
     */
    public int getMax() {
        return max;
    }
    /**
     * Sets the maximum value
     * @param max the maximum value to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Adds Associated Part to the Product
     * @param part to add to list of associatedParts
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    /**
     * Removes Associated Part from the Product
     * @param selectedAssociatedPart Part to remove from list of associatedParts
     * @return true if Associated Part is removed, false if not removed.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        //checks if selectedAssociatedPart is not null
        if (!selectedAssociatedPart.equals(null)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }
    /**
     * This method returns all Associated parts with a product
     * @return All the Associated Parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
