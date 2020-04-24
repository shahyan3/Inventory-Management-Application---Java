package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.*;

import assignmentCode.Item;
import assignmentCode.Stock;

/**
 * 
 * @author Shahyan
 * @version 1
 */

/** The following tests are for the Stock class. The tests 
 * define the characteristics and functionality of Stock class.
 */

public class StockTest {
	
	/** Test 0: Declaring Stock object
	 */
	
	Stock stock;
	
	/**	Test 1: Constructing a Stock object item
	 */
	@Before @Test public void setUpStock() {		
		stock = new Stock();
	}
	
	/**	Test 2: Check if stock instance of Stock class
	 */	
	@Test
	public void instance() {
		assertTrue(stock instanceof Stock);
	}
	
	/**	Test 3: Add item to Stock object
	 */	
	@Test
	public void addItemToStock() {
		Item a = new Item("mangos", 2, 3, 45, 200, 0, 100);
		stock.add(a);
		assertEquals("Set an item to stock list", a, stock.get().get(0));
	}
	
	
	/**	Test 5: Return all items in a list in Stock object (Important because the parserClass actually
	 *  returns a list of arrays)
	 */		
	@Test
	public void getListOfItems() {
		// dummy data to test against
		ArrayList<Item> items = new ArrayList<Item>();
		// create items
		Item a = new Item("bananas", 2, 5, 10, 20, null,0);
		Item b = new Item("mangos", 2, 5, 10, 20, 23,0);
		Item c = new Item("rice", 2, 5, 10, 20, -10,0);
		// add items
		items.add(a);
		items.add(b);
		items.add(c);
		
		// Actual
		stock.add(a);
		stock.add(b);
		stock.add(c);
		
		assertEquals("Returns a ArrayList of Type Items ", items, stock.get());
	}
	
	/**
	 * Test 6: Change quantity of an item
	 */
	@Test public void changeItemQuantity() {
		Item a = new Item("bananas", 2, 5, 10, 20, null,100);
		stock.add(a);
		
		stock.changeQuantity(0, 10);
		assertTrue("Did not change the quantity correctly",110==stock.get().get(0).getItemQuantity());
	}
	

 
}
