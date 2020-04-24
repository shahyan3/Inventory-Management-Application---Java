package assignmentCode;

/**
 * Ordinary trucks hold dry goods
 * @author Shahyan
 * @version 1.0
 */

public class ordinaryTruck extends Truck {

	/**
	 *  Constructor. Sets the capacity variables to their initial values
	 */
	public ordinaryTruck() {
		initialCapacity = 1000;
		capacity=1000;
	}
	
	
	@Override
	/**
	 * Returns the cost of the truck based on how many items it has stored
	 * 
	 * @return 750+0.25*itemCount Returns the cost of the truck in dollars using the formula provided
	 */
	public double getCost(Integer temperature) {
		double itemCount =  (double) initialCapacity - (double)capacity;
		return 750+0.25*itemCount;
	}
	@Override
	/**
	 *  Validates the temperature of the tested item is null
	 * @param item The item to be validated
	 */
	public boolean validateTemps(Item item) {
		if (item.getItemTemp()==null) {
			return true;
		}
		else {
			return false;
		}
	}
}