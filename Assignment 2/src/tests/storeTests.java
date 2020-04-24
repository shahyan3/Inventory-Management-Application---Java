package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import assignmentCode.CSVFormatException;
import assignmentCode.DeliveryException;
import assignmentCode.Item;
import assignmentCode.Manifest;
import assignmentCode.Stock;
import assignmentCode.StockException;
import assignmentCode.Store;
import assignmentCode.ordinaryTruck;
import assignmentCode.refrigeratedTruck;
import assignmentCode.truckException;

/**
 * 
 * @author Shahyan
 * @version 1.0
 */

public class storeTests {
	/**
	 * Test 0: Declare a store object (singleton type)
	 */
	 
	Store store;
			
	/**
	 * Test 1: Get a store object
	 */
	@Before public void getObject() {
		store = Store.getInstance();
	}
	
	@After public void clearObject() {
		Store.clear();
	}
	
	/**
	 * Test 2: Set and get the stores current capital
	 */
	
	@Test public void getStoreCapital() {	
		store.setInitialCapital(100000.00);
		assertTrue("Wrong capital amount",100000.00==store.getCapital());
	}
	/**
	 * Test 3: Get the stores name
	 */
	@Test public void getStoreName() {
		store.setName("Test Name");
		assertTrue("Wrong store name","Test Name"==store.getName());
	}
	
	/**
	 * Test 4: Get the stock object from store
	 */
	
	@Test public void getStock() {

		assertTrue("Did not return a stock object",store.getStock() instanceof Stock);
	}

	/**
	 * Test 5: Parse items and put them in the stock file, get the right name
	 * If itemParser has passed it's tests, then we know that all the other details will be correct.
	 * @throws CSVFormatException 
	 * 
	 */
	@Test public void parseItems() throws CSVFormatException{
		

		String fileLocation = "docs/itemParser/item_properties.csv";		
		store.initialiseItems(fileLocation);
		Stock storeStock = store.getStock();
		Item storeItem = storeStock.get().get(0);
		String name = storeItem.getName();


		assertEquals("Did not correctly add the item","rice",name);
		}
	
	/**
	 * Test 6: Given a very simple manifest, add the items to the store 
	 * @throws CSVFormatException 
	 * 
	 */
	@Test public void deliverySimple() throws DeliveryException, CSVFormatException{
		// create a manifest with a single truck with a single item
		String fileLocation = "docs/itemParser/item_properties.csv";		
		store.initialiseItems(fileLocation);
		store.setInitialCapital(100000);

		Manifest mani=new Manifest();
		ordinaryTruck truck = new ordinaryTruck();
		Item item = new Item("rice",2,4,100,500,null,1000);
		try {
			truck.add(item);
		} catch (truckException e) {
			//If this is ever thrown by this test, ordinary truck is implemented incorrectly. 
			e.printStackTrace();
		}
		mani.add(truck);
		store.deliver(mani);
		Stock stock = store.getStock();
		Item item2 = stock.get().get(0);

		assertEquals("Did not correctly add the delivery to the stores inventory",(Integer)1000,item2.getItemQuantity());
		}
	
	/**
	 * Test 7: Given a stock object, reduce the inventory of the store
	 * @throws StockException 
	 * @throws CSVFormatException 
	 */
	
	@Test public void sellSomeItems() throws DeliveryException, StockException, CSVFormatException{
		// create a manifest with a single truck with a single item
		String fileLocation = "docs/itemParser/item_properties.csv";		
		store.initialiseItems(fileLocation);
		store.setInitialCapital(100000);
		
		Manifest mani=new Manifest();
		ordinaryTruck truck = new ordinaryTruck();
		Item item = new Item("rice",2,4,100,500,null,1000);
		try {				
			truck.add(item);
		} catch (truckException e) {
			//If this is ever thrown by this test, ordinary truck is implemented incorrectly. 
			e.printStackTrace();
		}
		mani.add(truck);
		store.deliver(mani);
		
		
		Stock storeStock = store.getStock();
		Item storeItem = storeStock.get().get(0);
		Stock stock = new Stock();
		Item item2 = new Item("rice",2,4,100,500,null,500);	
		stock.add(item2);
		store.processSale(stock);
		
		storeItem = storeStock.get().get(0);
		assertEquals("Did not correctly subtract the sale from the stores inventory",(Integer)500,storeItem.getItemQuantity());
	}
	/**
	 * Test 8: A more complex delivery, with items spread over multiple trucks. 
	 * @throws CSVFormatException 
	 */
	@Test public void deliveryComplex()throws DeliveryException, CSVFormatException {
		String fileLocation = "docs/itemParser/item_properties.csv";		
		store.initialiseItems(fileLocation);
		store.setInitialCapital(100000);
		Manifest mani=new Manifest();
		ordinaryTruck truck = new ordinaryTruck();
		Item item = new Item("rice",2,4,100,500,null,1000);
		
		refrigeratedTruck truck1= new refrigeratedTruck();
		Item item2 = new Item("mushrooms",2,4,200,325,10,500);
		Item item3= new Item("rice",2,4,100,500,null,300);
		
		try {
			truck1.add(item2);
			truck1.add(item3);
			truck.add(item);
		} catch (truckException e) {
			e.printStackTrace();
		}
		
		mani.add(truck1);
		mani.add(truck);
		store.deliver(mani);

		//check the items are correct
		//looking at the items csv,  rice is item 0 and mushrooms are item 8
		int counter =0;
		if (store.getStock().get().get(0).getItemQuantity()!=1300) {
			counter ++;
		}
		if (store.getStock().get().get(8).getItemQuantity()!=500) {
			counter++;
			
		}
		
		assertTrue("Did not correctly add the delivery to the stores inventory, mistakes counter: "+counter,counter<1);
	
	}
	
	/**
	 * Test 9: Multiple items sold 
	 * @throws StockException 
	 * @throws CSVFormatException 
	 */
	@Test public void bigSale() throws DeliveryException, StockException, CSVFormatException{
		
		String fileLocation = "docs/itemParser/item_properties.csv";		
		store.initialiseItems(fileLocation);
		store.setInitialCapital(100000);
		Manifest mani=new Manifest();
		ordinaryTruck truck = new ordinaryTruck();
		Item item = new Item("rice",2,4,100,500,null,1000);
		
		refrigeratedTruck truck1= new refrigeratedTruck();
		Item item2 = new Item("mushrooms",2,4,200,325,10,500);
		Item item3= new Item("rice",2,4,100,500,null,300);
		
		try {
			truck1.add(item2);
			truck1.add(item3);
			truck.add(item);
		} catch (truckException e) {
			e.printStackTrace();
		}
		
		mani.add(truck1);
		mani.add(truck);
		store.deliver(mani);
		
		//we added 1300 rice and 500 mushrooms.
		//Taking away 1000 rice and 400 mushrooms
		//We expect 300 rice and 100 mushrooms
		Stock saleStock = new Stock();
		Item saleItem1 = new Item("mushrooms",2,4,200,325,10,400); 
		Item saleItem2 = new Item("rice",2,4,200,325,10,1000);
		saleStock.add(saleItem1);
		saleStock.add(saleItem2);
		
		store.processSale(saleStock);
		//check the items are correct
		//looking at the items csv,  rice is item 0 and mushrooms are item 8
		int counter =0;
		if (store.getStock().get().get(0).getItemQuantity()!=300) {
			counter ++;
		}
		if (store.getStock().get().get(8).getItemQuantity()!=100) {
			counter++;	
		}		
		
		assertTrue("Did not correctly reduce the inventory when multiple items sold",counter<1);
				
	}

	/**
	 * Test 10: Can't deliver items that do not exist 
	 * @throws CSVFormatException 
	 */
	@Test (expected = DeliveryException.class)
	public void weDontStockThat()throws DeliveryException, CSVFormatException{
		String fileLocation = "docs/itemParser/item_properties.csv";		
		store.initialiseItems(fileLocation);

		Item item = new Item("lololo",2,2,2,2,2,2);

		refrigeratedTruck truck = new refrigeratedTruck();
		try {
			truck.add(item);
		} catch (truckException e) {
			e.printStackTrace();
		}
		Manifest mani = new Manifest();
		mani.add(truck);

		store.deliver(mani);
	}
	
	/**
	 * Test 11: Can't reduce stock below 0
	 * @throws CSVFormatException 
	 * 
	 */
	@Test (expected = StockException.class)
	public void dontOverSell() throws StockException, DeliveryException, CSVFormatException {
		// create a manifest with a single truck with a single item
		String fileLocation = "docs/itemParser/item_properties.csv";		
		store.initialiseItems(fileLocation);
		store.setInitialCapital(100000);
		
		Manifest mani=new Manifest();
		ordinaryTruck truck = new ordinaryTruck();
		Item item = new Item("rice",2,4,100,500,null,1000);
		try {				
			truck.add(item);
		} catch (truckException e) {
			//If this is ever thrown by this test, ordinary truck is implemented incorrectly. 
			e.printStackTrace();
		}
		mani.add(truck);
		store.deliver(mani);
		
		
		Stock stock = new Stock();
		Item item2 = new Item("rice",2,4,100,500,null,1001);	
		stock.add(item2);
		store.processSale(stock);
		
	}
	
	/**
	 * Test 12: If all delivery is valid, subtract costs from capital
	 * @throws DeliveryException 
	 * @throws CSVFormatException 
	 */
	
	@Test public void thingsCostMoneyToDeliver() throws DeliveryException, CSVFormatException {
		String fileLocation = "docs/itemParser/item_properties.csv";		
		store.initialiseItems(fileLocation);
		store.setInitialCapital(100000.0);
		Manifest mani=new Manifest();
		ordinaryTruck truck = new ordinaryTruck();
		refrigeratedTruck truck1 = new refrigeratedTruck();
		Item item = new Item("rice",2,3,100,500,null,500);
		Item item1 = new Item ("ice",2,5,225,325,-10,600);
		Item item2 = new Item("rice",2,3,100,500,null,200);
		try {				
			truck.add(item);
			truck1.add(item1);
			truck1.add(item2);
		} catch (truckException e) {
			//If this is ever thrown by this test, trucks are implemented incorrectly. 
			e.printStackTrace();
		}
		mani.add(truck);
		mani.add(truck1);
		store.deliver(mani);

	
		//The cost of the item is 700*2=1400
		//						  600*2=1200
		//							   =2600 total
		double truckCost = truck.getCost(null)+truck1.getCost(-10);
		double totalCost =2600+ truckCost;
		double capital = 100000.0-totalCost;
		assertTrue("The costs were not subtracted correctly from capital",capital==store.getCapital());
		
	}
	/**
	 * Test 13: Selling products increases capital
	 * @throws DeliveryException 
	 * @throws StockException 
	 * @throws CSVFormatException 
	 */
	@Test public void turningAProfit() throws DeliveryException, StockException, CSVFormatException {
		
		String fileLocation = "docs/ASSDOCS/item_properties.csv";		
		store.initialiseItems(fileLocation);
		store.setInitialCapital(100000);
		Manifest mani=new Manifest();
		ordinaryTruck truck = new ordinaryTruck();
		Item item = new Item("rice",2,3,100,500,null,1000);
		
		refrigeratedTruck truck1= new refrigeratedTruck();
		Item item2 = new Item("mushrooms",2,4,200,325,10,500);
		Item item3= new Item("rice",2,3,100,500,null,300);
		
		try {
			truck1.add(item2);
			truck1.add(item3);
			truck.add(item);
		} catch (truckException e) {
			e.printStackTrace();
		}
		
		mani.add(truck1);
		mani.add(truck);
		store.deliver(mani);
		
		Stock saleStock = new Stock();
		Item saleItem1 = new Item("mushrooms",0,0,0,0,0,400); 
		Item saleItem2 = new Item("rice",0,0,0,0,0,1000);
		saleStock.add(saleItem1);
		saleStock.add(saleItem2);
		
		double correctFunds = store.getCapital()+6000+1200;
		store.processSale(saleStock);

	
		assertTrue("Did not correctly add profit to the store",correctFunds==store.getCapital());
		
		
	}
	
	/**
	 * Test 14: Can't accept deliveries without money to pay for it
	 * @throws DeliveryException 
	 * @throws CSVFormatException 
	 */
	@Test (expected = DeliveryException.class)
	public void cantBeInTheRed() throws DeliveryException, CSVFormatException {
		String fileLocation = "docs/itemParser/item_properties.csv";		
		store.initialiseItems(fileLocation);
		store.setInitialCapital(10);
		Manifest mani=new Manifest();
		ordinaryTruck truck = new ordinaryTruck();
		Item item = new Item("rice",2,4,100,500,null,1000);
		
		refrigeratedTruck truck1= new refrigeratedTruck();
		Item item2 = new Item("mushrooms",2,4,200,325,10,500);
		Item item3= new Item("rice",2,3,100,500,null,300);
		
		try {
			truck1.add(item2);
			truck1.add(item3);
			truck.add(item);
		} catch (truckException e) {
			e.printStackTrace();
		}
		
		mani.add(truck1);
		mani.add(truck);
		store.deliver(mani);
	}
	/**
	 * Test 15: Test that singleton structure works
	 */
	@Test public void singletoDetection() {
		//try and make a second store instance
		//change a parameter of the first instance and see if it effects the second
		//if it does, then it's singleton
		Store store2 = store.getInstance();
		store.setName("test name do not steal");
		assertTrue(store.getName().equals(store2.getName()));
	}

}
	