package model;

/**
 * This Class Manages InHouse parts and inherits from the Part Class
 * @author Manaf
 */
public class InHouse extends Part{

    private int machineId;

    /**
     * Constructor
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * This method returns the machine ID
     * @return machine ID
     */
    public int getMachineId() {
        return machineId;
    }
    /**
     * This method sets the machine ID.
     * @param  machineId to set machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
