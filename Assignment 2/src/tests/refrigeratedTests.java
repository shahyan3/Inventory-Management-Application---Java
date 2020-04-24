package tests;
import org.junit.Test;

import assignmentCode.Item;
import assignmentCode.Stock;
import assignmentCode.refrigeratedTruck;
import assignmentCode.truckException;

import org.junit.Before;
import static org.junit.Assert.*;

/**
 * 
 * @author Locke
 * @version 1.1
 * 
 */
public class refrigeratedTests {


	
	/**Test 0: Declare Stock Object
	 */
	
	refrigeratedTruck truck;
	
	/**
	 * Test 1: Make a truck object
	 */
	@Before @Test public void setUpTruck() {
		truck = new refrigeratedTruck();
	}
	
	/**
	 * Test 2: Get the capacity of the truck and ensure it's initial value is correct
	 */
	
	@Test public void getInitialCapacity() {
		//initial capacity of a refrigerated truck is required to be 800
		assertEquals("Could not get inital truck capacity correctly",
				(Integer)800,truck.getCapacity());
	}
	
	/**
	 * Test 3: Items are correctly added to internal stock object
	 */
	
	@Test public void addItemsToStock() throws truckException {
		//Declare objects
		Stock testStock = new Stock();
		Item item = new Item("milk",2,4,100,125,4,125);
		//Add the item to the test stock and the truck
		testStock.add(item);
		truck.add(item);
		//get the trucks stock object
		Stock truckStock = truck.get();
		//ensure they both have the same items
		assertEquals("Could not correctly add an item to the truck",
				testStock.get(),truckStock.get());
				
	}
	
	/**
	 * Test 4: Reduces capacity when items are added
	 */
	@Test public void reduceTruckCapacity()throws truckException {
		Item item = new Item("milk",2,4,100,125,4,125);
		truck.add(item);
		//The truck has a max capacity of 800, and has had 125 items loaded onto it.
		//Therefore its remaining capacity is 675
		assertEquals("Could not correctly reduce capacity",
			(Integer)675,truck.getCapacity());	
	}
	
	/**
	 * Test 5: Can't add more than initial truck capacity 
	 */
	@Test (expected = truckException.class)
	public void overloadTruck() throws truckException{
		Item item = new Item("milk",2,4,100,125,4,801);
		truck.add(item);
	}
	
	/**
	 * Test 6: Can't add more than remaining truck capacity
	 */
	@Test (expected = truckException.class)
	public void overloadTruck2() throws truckException{
		Item item = new Item("milk",2,4,100,125,4,400);
		Item item1 = new Item("rice",2,4,100,125,null,401);
		truck.add(item);
		truck.add(item1);
	}
	
	/**
	 * Test 7: Get the cost of the truck
	 */
	@Test public void getTruckCost() throws truckException{
		Item item = new Item("milk",2,4,100,125,4,400);
		Item item1 = new Item("ice",2,4,100,125,-1,200);
		Item item3 = new Item("ice",2,4,100,125,null,200);
		truck.add(item1);
		truck.add(item);
		truck.add(item3);
		//The cost of a refrigerated truck is related to the temperature it needs to be at
		//It therefore needs to calculate cost as 900 +200*.7^(coldestTemperature/5)
		double expectedCost =900.0+200.0*(Math.pow(0.7, (-1)/5.0));

		assertEquals("Could not correctly calculate cost of truck",
				expectedCost,truck.getCost(-1),.00001);
	}
	
	/**
	 * Test 8: Can't add item with temperature higher than 10 degrees
	 */
	
	@Test (expected = truckException.class)
	public void tempCieling() throws  truckException{
		Item item = new Item("ice",2,4,100,125,11,400);
		truck.add(item);
	}
	/**
	 * Test 9: Can't add item below -20 degrees
	 * 
	 */
	@Test (expected = truckException.class)
	public void tempFloor() throws  truckException{
		Item item = new Item("ice",2,4,100,125,-21,400);
		truck.add(item);
	}
}
