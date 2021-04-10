package model;

/**
 * This Class Manages Outsourced parts and inherits from the Part Class
 * @author Manaf
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Constructor
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * This method returns the company name
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method sets the company name
     * @param  companyName to set Company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
