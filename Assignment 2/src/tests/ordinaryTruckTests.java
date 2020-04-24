package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import assignmentCode.Item;
import assignmentCode.Stock;
import assignmentCode.ordinaryTruck;
import assignmentCode.truckException;


/**
 * 
 * @author Locke
 * @version 1.1
 */
public class ordinaryTruckTests {
	/**Test 0: Declare Stock Object
	 */
	
	ordinaryTruck truck;
	
	/**
	 * Test 1: Make a truck object
	 */
	@Before @Test public void setUpTruck() {
		truck = new ordinaryTruck();
	}
	
	/**
	 * Test 2: Get the capacity of the truck and ensure it's initial value is correct
	 */
	
	@Test public void getInitialCapacity() {
		// An ordinary trucks initial capacity is 1000
		Integer capacity=1000;
		assertEquals("Could not get inital truck capacity correctly",
				capacity,truck.getCapacity());
	}
	
	/**
	 * Test 3: Items are correctly added to internal stock object
	 */
	
	@Test public void addItemsToStock() throws truckException {
		// Declare objects
		Stock testStock = new Stock();
		Item item = new Item("rice",2,4,100,125,null,125);
		//add item to the truck and test stock objects
		testStock.add(item);
		truck.add(item);
		//get the trucks stock object
		Stock truckStock = truck.get();
		//make sure the stock objects both have the item
		assertEquals("Could not correctly add an item to the truck",
				testStock.get(),truckStock.get());
				
	}
	
	
	/**
	 * Test 4: Reduces capacity when items are added
	 */
	@Test public void reduceTruckCapacity() throws truckException{
		Item item = new Item("rice",2,4,100,125,null,500);
		truck.add(item);
		//As the trucks initial capacity is 500, adding an item of 500 means the remaining is 1000-500 = 500
		assertEquals("Could not correctly reduce capacity",
			(Integer)500,truck.getCapacity());	
	}
	
	/**
	 * Test 5: Can't add more than initial truck capacity 
	 */
	@Test (expected = truckException.class)
	public void overloadTruck() throws truckException{
		Item item = new Item("rice",2,4,100,125,null,1001);
		truck.add(item);
	}

	/**
	 * Test 6: Can't add more than remaining truck capacity
	 */
	@Test (expected = truckException.class)
	public void overloadTruck2() throws truckException{
		Item item = new Item("banana",2,4,100,125,null,400);
		Item item1 = new Item("rice",2,4,100,125,null,601);
		truck.add(item);
		truck.add(item1);
	}
	/**
	 * Test 7: Get the cost of the truck
	 */
	@Test public void getTruckCost() throws truckException {
		Item item = new Item("banana",2,4,100,125,null,400);
		Item item1 = new Item("rice",2,4,100,125,null,400);
		truck.add(item);
		truck.add(item1);
		//After adding the items, the truck needs to calculate and return the cost based on the formula below
		//750+.25*(items in truck)
		//note that getCost should require an Integer parameter. This is for refrigerated trucks
		double expectedCost = 750+0.25*800;
		assertEquals("Could not correctly calculate cost of truck",
				expectedCost,truck.getCost(null),.00001);
	}
	
	/**
	 * Test 8: Can't add items with a non null temperature to an ordinary truck
	 */
	@Test (expected = truckException.class)
	public void cantLoadColdItems() throws truckException {
		Item item = new Item("ice",2,4,100,125,-1,400);
		truck.add(item);
	}
	
}
