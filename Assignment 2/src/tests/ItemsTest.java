package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.*;

import assignmentCode.Item;

/**
 * 
 * @author Shahyan
 * @version 1
 */

/** The following tests are for the Items class
 * that defines the characteristics and functionality for Items class.
 * 
 */


public class ItemsTest {
	
	/** Test 0: Declaring Item objects
	 */
	Item item;

	/** Test 1: Constructing a Item item
	 */
	@Before @Test public void setUpItem() {
		item = new Item("banana", 2, 5, 10, 20, null, 13);
	}
	
	/** Test 2: Check if the object instance of Item Class
	 */
	@Test
	public void ItemInstance() {
		assertTrue("items object created", item instanceof Item);
	}
	/**
	 * Test 3: Get item name
	 */
	@Test

	public void ItemName() {
		assertEquals("Check item name: ","banana", item.getName());
	}

	
	/** Test 4: Get item sell price
	 */
	@Test
	public void getItemSellPrice() {
		assertTrue("Equal values if 0:", 5 - item.getSellPrice() == 0);	
	}
	
	/**	Test 5: Get item reorder point value
	 */
	@Test
	public void getReorderPoint() {
		assertTrue("Check item point, true if 0: ", 10 - item.getReorderPoint() == 0);

	}
	
	/**	Test 6: Get item reorder amount
	 */
	@Test
	public void getReorderAmount() {
		assertTrue("Check item amount, true if 0: ", 20 - item.getReorderAmount() == 0);		
	}
	
	/**	Test 7: Get item temperature expected null
	 */
	@Test
	// Check temp of an item, expected null
	public void getTemperatureNull() {
		assertNull(item.getItemTemp());
	}
	
	/**	Test 8: Check items temp value, expects value not null
	 */	
	@Test
	public void getTemperatureValue() {
		Item  item2 = new Item("apples", 2, 5, 10, 20, 40, 200);
		assertEquals("temp of item", Integer.valueOf(40), item2.getItemTemp());
	}

	/**	Test 9: Get item quantity
	 */
	@Test
	public void checkItemsQuantity() {
		assertEquals("Check item quantity", Integer.valueOf(13), item.getItemQuantity());	
	}
	
	/**
	 * Test 10: add to quantity
	 */
	@Test public void addItemQuantity() {
		item.addQuantity(1);
		assertEquals("Check item quantity", Integer.valueOf(14), item.getItemQuantity());	
	}
	
	/**
	 * Test 11: Check item cost
	 */
	@Test public void checkCost() {
		assertEquals("Check item cost", Integer.valueOf(2), item.getCost());	
	}
}
