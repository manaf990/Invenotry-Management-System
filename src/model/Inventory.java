package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This Class manages Inventory of parts and products
 * @author Manaf
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList(); //a list to holds all parts
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList(); // a list to hold all products
    /**
     * This method adds a Part to the list of allParts
     * @param newPart to be added to list of allParts
     */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }
    /**
     * This method adds a Product to the list of allProducts
     * @param newProduct to be added to list of allProducts
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }
    /**
     * This method search for a part using part ID.
     * @param partId used to search for a part.
     * @return the found Part.
     */
    public static Part lookupPart(int partId)
    {
        // find the part that matches partId
        for(Part part: allParts) {
            if (part.getId() == partId)
                return part;
        }
        return null;
    }
    /**
     * This method search for Product using Product ID.
     * @param productId used to search for a Product.
     * @return The found Product.
     */
    public static Product lookupProduct(int productId)
    {
        // find the product that matches the productId
            for (Product product : allProducts) {
                if (product.getId() == productId)
                    return product;
            }

        return null;
    }
//problem with return then instantiated new list
    /**
     * This method search for part using part name.
     * @param name used to search for a part.
     * @return the found parts.
     */
    public static ObservableList<Part> lookupPart(String name)
    {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList(); // a list to hold the found parts
        // if input is null return all parts
        if (name == null) {
            return allParts;
        }
        // find parts and adds them to list
        for(Part part: allParts) {
            if (part.getName().contains(name)) {
                filteredParts.add(part);
            }
        }
        return filteredParts;

    }
    /**
     * This method search for Product using Product name.
     * @param name used to search for a Product.
     * @return the found products
     */
    public static ObservableList<Product> lookupProduct(String name)
    {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList(); // a list to hold the found products
        // if input is null return all products
        if (name == null) {
            return allProducts;
        }
        // find products and adds them to list
        for(Product product: allProducts) {
            if (product.getName().contains(name)) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;

    }
    /**
     * This method updates a Part.
     * @param index used to set the index of a Part.
     * @param selectedPart used to set a Part.
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    /**
     * This method updates a product.
     * @param index used to set the index of a product.
     * @param newProduct used to set a product
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    /**
     * This method returns the parts.
     * @return All Parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**
     * This method returns all Products.
     * @return All Products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}



