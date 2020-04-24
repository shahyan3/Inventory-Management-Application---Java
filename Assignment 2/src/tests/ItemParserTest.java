package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.*;

import assignmentCode.CSVFormatException;
import assignmentCode.Item;
import assignmentCode.Stock;
import assignmentCode.itemParser;

/**
 * 
 * @author Locke
 * @version 1.0
 */
public class ItemParserTest {

	/**
	 * Test 0: Declare an Item Parser
	 */
	
	itemParser parser;
	
	/**
	 * Test 1: Create an itemParser object
	 */
	@Before @Test public void createObject() {
		parser = new itemParser();
		
	}
	
	/**
	 * Test 2: Can successfully parse an item that is cold
	 * @throws CSVFormatException When there is an issue with the csv format
	 */
	@Test public void singleColdItem() throws  CSVFormatException {
		// The location of an items CSV to be parsed
		//For convenience, the contents of the csv:
		//ice cream,8,14,175,250,-20
		String fileLocation = "docs/itemParser/Test1.csv";
		//Parse it
		parser.parse(fileLocation);
		//Get the stock object from the parser
		Stock stock = parser.getStock();
		//System.out.println(stock.get().get(0).getName());
		
		//Get the first (in this case, only) item from the stock object
		Item item = stock.get().get(0);
 	 		
		assertEquals("Did not correctly parse the file", "ice cream", item.getName() );
		
		assertEquals("Did not get cost price 8", new Integer(8), item.getCost() );
		
		assertEquals("Did not get 14 sell price", new Integer(14), item.getSellPrice() );
		
		assertEquals("Did not get 8 reorder point", new Integer(175) , item.getReorderPoint());
		
		assertEquals("Did not get 8 cost price", new Integer(250) , item.getReorderAmount());
		
		assertEquals("Did not get temp -20", new Integer(-20) , item.getItemTemp());
		
		assertEquals("Did not get quantity 0", new Integer(0) , item.getItemQuantity());

	}
	
	/**
	 * Test 3: Can successfully parse a non temperature controlled item
	 * @throws CSVFormatException When there is an issue with the csv format
	 */
	@Test public void singleDryItem() throws  CSVFormatException {
		// The location of an items CSV to be parsed
		//For convenience, the contents of the csv:
		//rice,2,3,225,300
		//This is of the same format as cold items, with 1 less property
		//Both types will appear in the same file in future
		String fileLocation = "docs/itemParser/Test2.csv";	
		//Parse it
		parser.parse(fileLocation);
		//Get the stock object from the parser
		Stock stock = parser.getStock();
		
		//Get the first (in this case, only) item from the stock object
		Item item = stock.get().get(0);

		//The previous test already confirmed that the code can successfully parse item details
		//therefore this test will only need to confirm the code handles null temperatures correctly
		assertTrue("Did not correctly parse a dry good",(item.getItemTemp()==null));
	}
	
	/**
	 * Test 4, correctly parse multiple items
	 * @throws CSVFormatException When there is an issue with the csv format
	 */
	@Test public void multipleItems() throws CSVFormatException {
		//The location of the items to be parsed
		String fileLocation = "docs/itemParser/Test3.csv";	
		//Parse it
		parser.parse(fileLocation);
		//Get the stock object from the parser
		Stock stock = parser.getStock();
		
		//We have already tested the parser's ability to parse files. We just care about the length
		//There are 4 items in the csv
		assertTrue ("Wrong number of items", stock.get().size()==4);
		
	}
	
	/**
	 * Test 5: Can't add more than 1 of a particular item
	 * @throws CSVFormatException When there is an issue with the csv format
	 */
	@Test (expected = CSVFormatException.class)
	public void noDuplicates() throws CSVFormatException{
		//The location of the items to be parsed
		//Contains 2 items with the same name. 
		String fileLocation = "docs/itemParser/Test5.csv";	
		//Parse it
		parser.parse(fileLocation);
		
	}
	
	/**
	 * Test 6, correctly parse the assignment file
	 * @throws CSVFormatException When there is an issue with the csv format
	 */
	@Test public void theRealDeal() throws CSVFormatException {
		//The location of the items to be parsed
		String fileLocation = "docs/itemParser/item_properties.csv";	
		//Parse it
		parser.parse(fileLocation);
		//Get the stock object from the parser
		Stock stock = parser.getStock();
		
		//We have already tested the parser's ability to parse files. We just care about the length
		//There are 24 items in the csv
		
		assertTrue ("Wrong number of items", stock.get().size()==24);
		
	}
}
