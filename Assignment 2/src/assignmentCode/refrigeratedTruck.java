package assignmentCode;
/**
 * Refrigerated trucks hold dry and cool goods.
 * @author Shahyan
 * @version 1.0
 *
 */
public class refrigeratedTruck extends Truck {
	/**
	 * Cconstructor. Sets the capacity variables to their initial values
	 */
	public refrigeratedTruck() {
		initialCapacity=800;
		capacity=800;
	}

	@Override
	/**
	 * Returns the cost of the truck based on the temperature it needs to be
	 * @param temperature The coldest temperature of the truck
	 * @return The cost of the truck
	 */
	public double getCost(Integer temperature) {
		
		return 900.0+200.0*(Math.pow(0.7, (temperature)/5.0));
	}
	
	/**
	 * Verifies the temperature of the tested item is between -20 and 10 inclusive
	 * Also allows null temperatures
	 * @param item The item to be validated
	 */
	@Override
	public boolean validateTemps(Item item) {
		//if the temperature is null
		if (item.getItemTemp()==null) {
			return true;
		}
		//if the temperature is within the allowed range
		if ((item.getItemTemp()<=10)&&(item.getItemTemp()>=-20)) {
			return true;
		}
		//if its outside the range and not null
		else {
			return false;
		}
	}

}