package tests;
 import org.junit.Before;
 import org.junit.Test;

import assignmentCode.CSVFormatException;
import assignmentCode.SalesLogParser;
import assignmentCode.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
 
  

/**
 * 
 * @author Shahyan
 * @version 1.1
 * 
 */ 
 
public class SalesLogTest {
	/**Test 0: Declare SalesLog Object
	 */
	
	SalesLogParser salesParser;
	
	/**
	 * Test 1: Make a salesParser Object
	 * 
	 */
	@Before @Test public void create() {
		salesParser = new SalesLogParser();
	}
	
	
	/**
	 * Test 2: return a stock item
	 */
	@Test public void getStock() {
		Stock stock = new Stock();
		Stock parserStock = salesParser.getStock();
		assertTrue("Did not correctly return a stock file",stock.getClass()==salesParser.getStock().getClass());
	
	}
	
	/**
	 * Test 3: Put a single item into the stock object when there is a single item in a csv
	 * @throws FileNotFoundException 
	 * @throws CSVFormatException 
	 */
	@Test public void getSingleItem() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/salesParser/logTest1.csv";
		salesParser.parse(fileName);
		Stock parserStock = salesParser.getStock();
		assertTrue("did not make one and only 1 item for a single item csv",parserStock.get().size()==1);
	}
	
	
	/**
	 * Test 4: Correctly get the right item name
	 * @throws CSVFormatException 
	 */
	@Test public void getSingleName() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/salesParser/logTest1.csv";
		salesParser.parse(fileName);
		Stock parserStock = salesParser.getStock();
		assertTrue("did not get the right item name",parserStock.get().get(0).getName().equals("rice"));
	}
	
	/**
	 * Test 5: Correctly get the item quantity
	 * @throws CSVFormatException 
	 */
	@Test public void getSinglePrice() throws FileNotFoundException, CSVFormatException{
		String fileName = "docs/salesParser/logTest1.csv";
		salesParser.parse(fileName);
		Stock parserStock = salesParser.getStock();
		assertTrue("did not get the right price",parserStock.get().get(0).getItemQuantity()==174);
	}
	
	/**
	 * Test 6: Can get multiple items
	 * @throws CSVFormatException 
	 */
	@Test public void getMultipleItems() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/salesParser/logTest2.csv";
		salesParser.parse(fileName);
		Stock parserStock = salesParser.getStock();
		assertTrue("did not get two items",parserStock.get().size()==2);
	}
	/**
	 * 
	 * Test 7: Gets the right name if the item has a space in it
	 * @throws CSVFormatException 
	 */
	@Test public void getSpacedName() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/salesParser/logTest2.csv";
		salesParser.parse(fileName);
		Stock parserStock = salesParser.getStock();
		assertTrue("did not correctly parse item name with a space",parserStock.get().get(1).getName().equals("frozen vegetables"));
	}
	
	/**
	 * Test 8: Can't add more than 1 of a particular item in sales log
	 * @throws FileNotFoundException 
	 */
	@Test (expected = CSVFormatException.class)
	public void noDuplicates() throws CSVFormatException, FileNotFoundException {
		//The location of the items to be parsed
		//Contains 2 items with the same name. 
		String fileLocation = "docs/salesParser/logTest3.csv";	
		//Parse it
		salesParser.parse(fileLocation);
	}
	
	/**
	 * Test 9: Raises exception if the file has too many columns
	 */
	@Test (expected = CSVFormatException.class)
	public void twoColumnsOnly() throws CSVFormatException, FileNotFoundException {
		//The location of the items to be parsed
		//Contains 2 items with the same name. 
		String fileLocation = "docs/salesParser/logTest4.csv";	
		//Parse it
		salesParser.parse(fileLocation);
	}
	/**
	 * Test 10: Raises exception if a price can not be found for all items
	 */
	@Test (expected = CSVFormatException.class)
	public void validPricePlease() throws CSVFormatException, FileNotFoundException {
		//The location of the items to be parsed
		//Contains 2 items with the same name. 
		String fileLocation = "docs/salesParser/logTest5.csv";	
		//Parse it
		salesParser.parse(fileLocation);
	}
	

}
