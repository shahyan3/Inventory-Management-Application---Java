package assignmentCode;

/** 
 * Item Class. This is how Items are represented throughout the system. 
 * Stores basic information about the item that will be useful for store operations
 * 
 * @author Locke
 * @version 1.0
 *
 */

public class Item {
	
	private String name;
	private Integer cost;
	private Integer price;
	private Integer restockPoint;
	private Integer restockAmount;
	private Integer temperature;
	private Integer quantity;
	
	/**
	 * Constructor for Item class
	 * 
	 * @param name		The name of the item (ie rice, banana)
	 * @param cost		The cost to make an individual item (ie 1 bag of rice, a single banana)
	 * @param price		The price of an individual item
	 * @param restockPoint	When the quantity drops below this number, more must be ordered
	 * @param restockAmount	The amount to be ordered
	 * @param temperature	The temperature that the item must be kept below. Can be null, 
	 * 					 	which indicates it doesn't need to be temperature controlled
	 * @param quantity		The amount of items this object represents. A value of 50 would 
	 * 						indicate 50 bags of rice, or 50 bananas
	 */
	public Item(String name, Integer cost, Integer price, Integer restockPoint, Integer restockAmount,
			Integer temperature, Integer quantity) {
		 this.name=name;
		 this.cost=cost;
		 this.price=price;
		 this.restockPoint=restockPoint;
		 this.restockAmount=restockAmount;
		 this.temperature=temperature;
		 this.quantity=quantity;
	}
	/**
	 * Getter function for the item's name
	 * @return Name	Name of the Item
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter function for the sale price of the Item
	 * @return price	The sale price of the item
	 */
	public Integer getSellPrice() {
		return this.price;
	}
	
	public Integer getCost() {
		return this.cost;
	}
	
	/**
	 * Getter function for the Items reorder point
	 * @return restockPoint	The Items reorder point
	 */
	public Integer getReorderPoint() {
		return this.restockPoint;
	}
	/**
	 * Getter function for the item's reorder amount
	 * @return restockAmount	The items reorder amount
	 */
	public Integer getReorderAmount() {
		return this.restockAmount;
	}
	/**
	 * Getter function for the items max temperature
	 * @return temperature	The temperature the item must be kept below
	 */
	public Integer getItemTemp() {
		return this.temperature;
	}
	/**
	 * Getter function for the quantity this item object represents
	 * @return quantity	The quantity represented by this object
	 */
	public Integer getItemQuantity() {
		return this.quantity;
	}
	/**
	 * Adds the given integer to the quantity of an item. Negative numbers will reduce quantity
	 * @param Quantity the amount to be added
	 */
	public void addQuantity(Integer Quantity) {
		this.quantity+=Quantity;
	}
}
