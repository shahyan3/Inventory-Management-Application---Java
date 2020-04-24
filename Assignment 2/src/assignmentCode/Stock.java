package assignmentCode;

import java.util.ArrayList;
/** Stock class. Stock represents a generic list of items. It can be used to represent a trucks load,
 * the inventory of a store, items in a sales log. 
 * 
 * @author Locke
 * @version 1.0
 */
public class Stock {

	private ArrayList<Item> itemArray;
	
	/**
	 * Initialises the class and it's internal item storage
	 */
	public Stock() {
		this.itemArray = new ArrayList<Item>();
	}
	/**
	 * Stores an Item object inside of stock.
	 * @param item	The item to be stored
	 */
	public void add(Item item) {
		this.itemArray.add(item);
	}
	/**
	 * Returns the internal arraylist used to store the objects. This gives whatever calls it 
	 * an easy way to iterate over all stored items. 
	 * @return itemArray 	The array of Item objects. 
	 */
	public ArrayList<Item> get(){
		return this.itemArray;
	}
	
	public void changeQuantity(Integer index,Integer amount) {
		itemArray.get(index).addQuantity(amount);
	}
}
