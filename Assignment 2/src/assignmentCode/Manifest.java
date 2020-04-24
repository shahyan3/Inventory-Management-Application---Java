package assignmentCode;

import java.util.ArrayList;
/**
 * For holding truck objects
 * @author Shahyan
 * @version 1
 *
 */
public class Manifest {
	
	
	private ArrayList<Truck> trucks;
	
	/**
	 * Constructor for manifest class. Initialises internal storage of truck objects
	 */
	public Manifest() {
		trucks = new ArrayList<Truck>();
	}
	/**
	 * Adds a truck to the manifest
	 * @param truck The truck to be added
	 */
	public void add(Truck truck) {
		trucks.add(truck);
	}
	/**
	 * Given an index, return a corresponding truck
	 * @param index the index of the truck to get
	 * @return the truck that corresponds to the index
	 */
	public Truck get(Integer index){
		return trucks.get(index);
	}
	
	/**
	 * Get the number of trucks in the manifest
	 * @return the size of the manifest
	 */
	public Integer size() {
		return trucks.size();
	}
}