package assignmentCode;


/**Abstract truck class
 * 
 * @author Shahyan
 * @version 1.0
 */
public abstract class Truck {
	
	protected Integer capacity;
	protected Integer initialCapacity;
	Stock stock;
	/**
	 * Truck Constructor
	 */
	public Truck() {
		
		stock = new Stock();
	}
	/**
	 * Returns the remaining capacity of the truck, how many more items can fit
	 * @return capacity The remaining capacity of the truck
	 */
	public Integer getCapacity() {
		return capacity;
	}
	/**
	 * Adds item to the truck. Throws an exception if the item will put the truck over capacity
	 * 
	 * @param item The item to be added
	 * @throws truckException
	 */
	public void add(Item item) throws truckException {		
		// If there isn't enough space left
		if (validateTemps(item)==true) {
			if (capacity<item.getItemQuantity()) {
				// Throw exception
				throw new truckException();
			}
			else {
				//Otherwise add the item to the truck and reduce capacity
				stock.add(item);
				this.capacity-=item.getItemQuantity();
			}
		}
		else {
			throw new truckException();
		}
	}
	/**
	 * Gets the trucks internal stock object
	 * @return stock The trucks stock object
	 */
	public Stock get() {
		return stock;
	}
	public abstract double getCost(Integer temperature);
	
	public abstract boolean validateTemps(Item item);
	
	
}