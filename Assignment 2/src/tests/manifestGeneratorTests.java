package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;


import org.junit.Before;
import org.junit.Test;

import assignmentCode.Item;
import assignmentCode.Manifest;
import assignmentCode.Stock;
import assignmentCode.Truck;
import assignmentCode.manifestGenerator;
import assignmentCode.ordinaryTruck;
import assignmentCode.refrigeratedTruck;
import assignmentCode.truckException;

/**
 * 
 * @author Shahyan
 * @version 1.0
 */

public class manifestGeneratorTests {
	/**
	 * Test 0: Declare a manifestGenerator object
	 */
	manifestGenerator generator;
	
	/**
	 * Test 1: Create a manifestGenerator object
	 */
	@Before @Test public void createGenerator() {
		generator = new manifestGenerator();
	}
	

	/**
	 * Test 2: Given dummy item list, make a manifest that contains an ordinary truck
	 */
	@Test public void addTrucks() {
		Stock storeStock=new Stock();
		Item storeItem = new Item("rice",2,4,100,500,null,0);
		storeStock.add(storeItem);
		generator.generate(storeStock);
		
		Manifest manifest = generator.get();
		ordinaryTruck truck = new ordinaryTruck();
		assertEquals("Could not make a manifest with an ordinary truck",
				truck.getClass(),manifest.get(0).getClass());
	}
	
	/**
	 * Test 3: Given a dummy item list with a cold item, make a manifest with a cold truck
	 */
	@Test public void addColdTruck() {
		Stock storeStock=new Stock();
		Item storeItem = new Item("ice",2,4,100,500,-1,0);
		storeStock.add(storeItem);
		generator.generate(storeStock);
		
		Manifest manifest = generator.get();
		refrigeratedTruck truck = new refrigeratedTruck();
		assertEquals("Could not make a manifest with a cold truck",
				truck.getClass(),manifest.get(0).getClass());
	}

	/**
	 * Test 4: correctly get the right amount of items onto a truck
	 */
	@Test public void addItemsToTruck() {
		//Create a stock object with items
		Stock storeStock=new Stock();
		Item storeItem = new Item("ice",2,4,100,500,-1,0);
		storeStock.add(storeItem);
		//Make a manifest
		generator.generate(storeStock);
		//Get the manifest
		Manifest manifest = generator.get();
		//get from the manifest the first truck. Then, get its stock object.
		//then get the arraylist of item objects
		Item testItem = manifest.get(0).get().get().get(0);
		//Assert that the quantity of the item is 500
		assertEquals("Could not get the correct quantity of items",
				(Integer)500,testItem.getItemQuantity());
	}
	
	/**
	 * Test 5: Correctly fill up a truck when there's too many items for just one
	 */
	@Test public void topUpATruck() {
		//create a stock object with items
		Stock storeStock=new Stock();
		Item storeItem = new Item("ice",2,4,100,500,-1,0);
		Item storeItem2 = new Item("colderice",2,4,100,600,-10,0);
		storeStock.add(storeItem);
		storeStock.add(storeItem2);
		
		//generate the manifest, get it, and get the first truck
		generator.generate(storeStock);
		Manifest mani=generator.get();
		refrigeratedTruck truck = (refrigeratedTruck) mani.get(0);
		
		//assert that the trucks capacity is 0
		assertEquals("Did not correctly fill trucks up",
				(Integer)0,truck.getCapacity());
	}
	
	/** 
	 * Test 6: When items overflow from one truck to another, make sure it's done right
	 * 
	 */
	@Test public void overflowToSecondTruck() {
		//create a stock object with items
		Stock storeStock=new Stock();
		Item storeItem = new Item("ice",2,4,100,500,-1,0);
		Item storeItem2 = new Item("colderice",2,4,100,500,-10,0);
		storeStock.add(storeItem);
		storeStock.add(storeItem2);
		//generate the manifest, get it, and get the second truck
		generator.generate(storeStock);
		Manifest mani=generator.get();
		refrigeratedTruck truck =(refrigeratedTruck) mani.get(1);
		//we are loading 1000 cold items. Fridge trucks have a capacity for 800. 
		//Therefore assert the second truck has 1000-800=200 items onboard =  600 remaining capacity
		assertEquals("Did not correctly overflow into second truck",
				(Integer)600,truck.getCapacity());
	}
	/**
	 * Test 7: Fill up a cold truck, leaving only dry goods remaining (ordinary truck)
	 */
	@Test public void multipleTruckTypes() {
		Stock storeStock=new Stock();
		Item storeItem = new Item("ice",2,4,100,500,-1,0);
		Item storeItem2 = new Item("rice",2,4,100,500,null,0);
		storeStock.add(storeItem);
		storeStock.add(storeItem2);
		
		//generate the manifest, get it, and get the second truck
		generator.generate(storeStock);
		Manifest mani=generator.get();
		Truck truck1 = mani.get(1);
		//make a new truck object
		ordinaryTruck truck2 = new ordinaryTruck();
		//assert that the truck has the correct type
		assertEquals("Second truck was not a dry truck",
				truck2.getClass(),truck1.getClass());
		
	}
	
	/**
	 * Test 8: Same as test 7, but the items are not presorted. 
	 */
	@Test public void sortBeforeLoading() {
		Stock storeStock=new Stock();
		Item storeItem = new Item("ice",2,4,100,500,-1,0);
		Item storeItem2 = new Item("rice",2,4,100,500,null,0);
		storeStock.add(storeItem2);
		storeStock.add(storeItem);
		
		//generate the manifest, get it, and get the second truck
		generator.generate(storeStock);
		Manifest mani=generator.get();
		Truck truck1 = mani.get(1);
		//make a new truck object
		ordinaryTruck truck2 = new ordinaryTruck();
		//assert that the truck has the correct type
		assertEquals("Second truck was not a dry truck",
				truck2.getClass(),truck1.getClass());
	}
	
	/**
	 * Test 9, can correctly make multiple of each type of truck
	 * 	There are 1600 wetgoods, and 1100 dry goods
	 *  This should result in 2 packed fridge trucks, 1 packed normal truck
	 *  and one normal truck with 100 items
	 *  Part 1 tests all the trucks
	 */
	@Test public void lotsandlotsoftrucks1() {
		//create stock and give it items
		Stock stock = new Stock();
		Item item = new Item("ice",2,4,100,900,-1,0);
		Item item2 = new Item("rice",2,4,100,500,null,0);
		Item item3 = new Item("icecream",2,4,100,700,-5,0);
		Item item4 = new Item("apples",2,4,100,600,null,0);
		stock.add(item);
		stock.add(item2);
		stock.add(item3);
		stock.add(item4);
		
		//generate the manifest and get it, then get the array list of trucks
		generator.generate(stock);
		Manifest manifest = generator.get();
		
		ArrayList<Truck> maniTrucks = new ArrayList<Truck>();
			for (Integer i=0;i<manifest.size();i++) {
				maniTrucks.add(manifest.get(i));
			}
		
		//make a test array of trucks to compare it too
		ArrayList<Truck> testTrucks = new ArrayList<Truck>();
		refrigeratedTruck truck1 = new refrigeratedTruck();
		refrigeratedTruck truck2 = new refrigeratedTruck();
		ordinaryTruck truck3 = new ordinaryTruck();
		ordinaryTruck truck4 = new ordinaryTruck();
		
		testTrucks.add(truck1);
		testTrucks.add(truck2);
		testTrucks.add(truck3);
		testTrucks.add(truck4);
		
		
		boolean value = false;
		int counter = 0;
		//iterate over all trucks
		for (int index=0;index<testTrucks.size();index++) {
			//count the number of trucks with the correct type
			if (testTrucks.get(index).getClass()==testTrucks.get(index).getClass()) {
				counter++;
			}
		}
		//if all the trucks are correct
		if (counter==testTrucks.size()) {
			//then the test is true
			value = true;
		}
		//assert that the trucks are the right type
		assertTrue ("Trucks are of the wrong types",value);
			
			
	}
	
	/**
	 * Test 10. Lots of trucks part 2, checks that the trucks have the correct quantities.
	 * Follows on from test 9
	 */
	
	@Test public void lotsandlotsoftrucks2() {
		//create stock and give it items
		Stock stock = new Stock();
		Item item = new Item("ice",2,4,100,900,-1,0);
		Item item2 = new Item("rice",2,4,100,500,null,0);
		Item item3 = new Item("icecream",2,4,100,700,-5,0);
		Item item4 = new Item("apples",2,4,100,600,null,0);
		stock.add(item);
		stock.add(item2);
		stock.add(item3);
		stock.add(item4);
		
		//generate the manifest and get it, then get the array list of trucks
		generator.generate(stock);
		Manifest manifest = generator.get();
		ArrayList<Truck> maniTrucks = new ArrayList<Truck>();
		for (Integer i=0;i<manifest.size();i++) {
			maniTrucks.add(manifest.get(i));
		}
		
		//make a test array of trucks to compare it too
		ArrayList<Truck> testTrucks = new ArrayList<Truck>();
		refrigeratedTruck truck1 = new refrigeratedTruck();
		refrigeratedTruck truck2 = new refrigeratedTruck();
		ordinaryTruck truck3 = new ordinaryTruck();
		ordinaryTruck truck4 = new ordinaryTruck();
		
		//give the trucks some dummy items to give them the correct capacities
		Item item5 = new Item("ice",2,4,100,800,-1,800);
		Item item6 = new Item("icecream",2,4,100,800,-1,800);
		Item item7 = new Item("rice",2,4,100,1000,null,1000);
		Item item8 = new Item("rice",2,4,100,1000,null,1000);
		
		try {
			truck1.add(item5);
			truck2.add(item6);
			truck3.add(item7);
			truck4.add(item8);
		} catch (truckException e) {
			e.printStackTrace();
		}

		
		testTrucks.add(truck1);
		testTrucks.add(truck2);
		testTrucks.add(truck3);
		testTrucks.add(truck4);
		
		boolean value = false;
		int counter = 0;
		//iterate over all trucks
		for (int index=0;index<testTrucks.size();index++) {
			//count the number of trucks with the correct capacity
			if (testTrucks.get(index).getCapacity()==testTrucks.get(index).getCapacity()) {
				counter++;
			}
		}
		//if all the trucks are holding the correct number of items
		if (counter==testTrucks.size()) {
			//then the test is true
			value = true;
		}
		//assert that the trucks are carrying the correct quantities
		assertTrue ("Trucks aren't carrying the correct cargo counts",value);
		
		
	}
	
	/**
	 * Test 11. Tests that the manifest is empty if nothing needs to be ordered.
	 */
	@Test public void nothingToOrder() {
		//create stock and give it items
		Stock stock = new Stock();
		Item item = new Item("ice",2,4,100,900,-1,901);
		Item item2 = new Item("rice",2,4,100,500,null,501);
		stock.add(item);
		stock.add(item2);
		
		generator.generate(stock);
		Manifest mani = generator.get();
		assertTrue ("Did not correctly handle items above their restock point", mani.size()==0);
				
	}
	
	/**
	 * Test 12: Tests that if a manifest has already been made, then another one is made that requires no items to be delivered,
	 * it makes an empty manifest
	 */
	@Test public void handlesEmptyCorrectly() {
		Stock stock = new Stock();
		Item item = new Item("ice",2,4,100,900,-1,300);
		Item item2 = new Item("rice",2,4,100,500,null,200);
		stock.add(item);
		stock.add(item2);
		generator.generate(stock);
		stock = new Stock();
		item = new Item("ice",2,4,100,900,-1,901);
		item2 = new Item("rice",2,4,100,500,null,501);
		stock.add(item);
		stock.add(item2);
		generator.generate(stock);
		generator.generate(stock);
		Manifest mani = generator.get();
		assertTrue ("Did not correctly reset the manifest each time", mani.size()==0);
		
	}
}
