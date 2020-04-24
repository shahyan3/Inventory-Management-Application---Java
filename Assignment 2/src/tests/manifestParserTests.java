package tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import assignmentCode.CSVFormatException;
import assignmentCode.Item;
import assignmentCode.Manifest;
import assignmentCode.Truck;
import assignmentCode.manifestParser;
import assignmentCode.ordinaryTruck;
import assignmentCode.refrigeratedTruck;


/**
 * 
 * @author Locke
 * @version 1.0
 * 
 * Please note: When running CSV tests, make sure you are overwriting the CSV each time.
 * Otherwise, when you finish the first parsing test and try to do the second, the first will fail
 * This is on purpose to make sure you are overwriting and not appending.
 */
public class manifestParserTests {
	/**
	 * Test 0: Declare manifestParser object
	 */
	manifestParser parser;
	
	/**
	 * Test 1: Create manifestParser object
	 */
	@Before @Test public void create() {
		parser = new manifestParser();
	}
	
	/**
	 * Test 2: return a manifest object
	 */
	
	@Test public void getManifest() {
		Manifest mani = new Manifest();
		assertTrue("Didn't get a manifest",mani.getClass()==parser.getManifest().getClass());
	}
	
	/**
	 * Test 3: Given a csv with just an ordinary truck, return a manifest with an ordinary truck
	 * @throws FileNotFoundException 
	 * @throws CSVFormatException 
	 */
	@Test public void oneOrdinaryTruck() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/manifestParser/test1.csv";
		parser.parse(fileName);
		Manifest mani = parser.getManifest();
		ordinaryTruck truck = new ordinaryTruck();
		assertTrue("did not make an ordinary truck",mani.get(0).getClass()==truck.getClass());
	}
	
	/**
	 * Test 4: Given a csv with just an ordinary truck, return a manifest with an ordinary truck
	 * @throws FileNotFoundException 
	 * @throws CSVFormatException 
	 */
	@Test public void oneRefrigeratedTruck() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/manifestParser/test2.csv";
		parser.parse(fileName);
		Manifest mani = parser.getManifest();
		refrigeratedTruck truck = new refrigeratedTruck();
		assertTrue("did not make an ordinary truck",mani.get(0).getClass()==truck.getClass());
	}
	
	/**
	 * Test 5: Add Items to a truck
	 * @throws CSVFormatException 
	 * @throws FileNotFoundException 
	 */
	@Test public void addItemsToATruck() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/manifestParser/test3.csv";
		parser.parse(fileName);
		Manifest mani = parser.getManifest();
		//test3csv has a cold truck with 3 items. already testing the ability to make cold trucks in previous gets.
		//just gotta test that we can parse that there are 3 items and put them all in the truck
		Truck truck = mani.get(0);
		ArrayList<Item> items = truck.get().get();
		assertTrue ("Did not get right number of items",items.size()==3);
	}
	
	/**
	 * Test 6: Add multiple trucks
	 * @throws CSVFormatException 
	 * @throws FileNotFoundException 
	 */
	@Test public void manyTrucks() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/manifestParser/test4.csv";
		parser.parse(fileName);
		Manifest mani = parser.getManifest();
		//should return 3 trucks in manifest
		assertTrue("Did not correctly add multiple trucks to manifest",mani.size()==3);
	}
	
	
	/**
	 * Test 7: get correct item names
	 */
	@Test public void correctNames() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/manifestParser/test3.csv";
		parser.parse(fileName);
		Manifest mani = parser.getManifest();
		ArrayList<Item> items = mani.get(0).get().get();
		int counter=0;
		
		// count the wrong results
		if (!items.get(0).getName().equals("ice cream")) {
			counter++;
		}
		if (!items.get(1).getName().equals("cheese")) {
			counter++;
		}
		if (!items.get(2).getName().equals("rice")) {
			counter++;
		}
		
		assertTrue("Did not correctly get "+counter+" item names",counter==0);
	}
	/**
	 * Test 8: get correct item Quantities
	 */
	@Test public void correctQuantities() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/manifestParser/test3.csv";
		parser.parse(fileName);
		Manifest mani = parser.getManifest();
		ArrayList<Item> items = mani.get(0).get().get();
		int counter=0;
		
		// count the wrong results
		if (!items.get(0).getItemQuantity().equals(101)) {
			counter++;
		}
		if (!items.get(1).getItemQuantity().equals(76)) {
			counter++;
		}
		if (!items.get(2).getItemQuantity().equals(90)) {
			counter++;
		}
		
		assertTrue("Did not correctly get "+counter+" item quantities",counter==0);
	}
	
	/**
	 * Test 9: add items to all the trucks
	 * @throws CSVFormatException 
	 * @throws FileNotFoundException 
	 */
	@Test public void addItemsToAll() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/manifestParser/manifest.csv";
		parser.parse(fileName);
		Manifest mani = parser.getManifest();
		//this manifest is the assignment test one. contains 3 trucks
		//inside each truck is a different number of items
		//this checks that each of the 3 trucks has the correct number of items
		int counter=0;
		
		// count the wrong results
		if (!(mani.get(0).get().get().size()==3)) {
			counter++;
		}
		if (!(mani.get(1).get().get().size()==2)) {
			counter++;
		}
		if (!(mani.get(2).get().get().size()==1)) {
			counter++;
		}
		
		assertTrue ("Did not correctly get items for"+counter+" trucks",counter==0);
	}
	
	/**
	 * Test 10: Throw CSVFormatExemption when there are incorrect truck names
	 * @throws CSVFormatException 
	 * @throws FileNotFoundException 
	 */
	@Test (expected = CSVFormatException.class)
	public void wrongNames() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/manifestParser/test5.csv";
		parser.parse(fileName);
	}

	/**
	 * Test 11: Throw CSVFormatExemption when there are more than 2 columns for a row
	 * @throws CSVFormatException 
	 * @throws FileNotFoundException 
	 */
	@Test (expected = CSVFormatException.class)
	public void wrongColumns() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/manifestParser/test6.csv";
		parser.parse(fileName);
	}

	/**
	 * Test 12: Throw CSVFormatExemption when a quantity can't be parsed for an item
	 * @throws CSVFormatException 
	 * @throws FileNotFoundException 
	 */
	@Test (expected = CSVFormatException.class)
	public void wrongQuantity() throws FileNotFoundException, CSVFormatException {
		String fileName = "docs/manifestParser/test7.csv";
		parser.parse(fileName);
	}
	
	/**
	 * Test 13: given a manifest object, path, and name, create a .csv file
	 * can be empty, just needs to make a file
	 * @throws CSVFormatException 
	 * @throws IOException 
	 */
	
	@Test public void makeACSV () throws CSVFormatException, IOException {
		String fileName = "csv1.csv";
		String path = "docs/OUTPUTS/";
		parser.parse("docs/manifestParser/manifest.csv");
		Manifest mani = parser.getManifest();
		parser.write(mani,path,fileName);
		File file =new File(path+fileName);
		
		boolean success = true;
		try {
			Scanner input = new Scanner(file);
			input.close();
		}
		catch(Exception e){
			success = false;
		}


		assertTrue("Did not make a csv at the right path",success==true);	
	}
	/**
	 * Test 14: Puts an ordinary truck object in the csv
	 * @throws CSVFormatException
	 * @throws IOException
	 */
	@Test public void putTruckInCSV() throws CSVFormatException, IOException {
		String fileName = "csv2.csv";
		String path = "docs/OUTPUTS/";
		parser.parse("docs/manifestParser/test1.csv");
		Manifest mani = parser.getManifest();
		
		//we parse in a manifest that we know from earlier tests has a single empty ordinary truck
		parser.write(mani,path,fileName);
		parser.parse(path+fileName);
		Manifest mani2=parser.getManifest();
		ordinaryTruck truck = new ordinaryTruck();
		assertTrue("did not write an ordinary truck",mani2.get(0).getClass()==truck.getClass());
		
	}
	
	/**
	 * Test 15: Puts a refrigerated truck object in the csv
	 * @throws CSVFormatException
	 * @throws IOException
	 */
	@Test public void putColdTruckInCSV() throws CSVFormatException, IOException {
		String fileName = "csv3.csv";
		String path = "docs/OUTPUTS/";
		parser.parse("docs/manifestParser/test2.csv");
		Manifest mani = parser.getManifest();
		
		//we parse in a manifest that we know from earlier tests has a single empty refrigerated truck
		parser.write(mani,path,fileName);
		parser.parse(path+fileName);
		Manifest mani2=parser.getManifest();
		Truck truck = new refrigeratedTruck();
		assertTrue("did not write a refrigerated truck",mani2.get(0).getClass()==truck.getClass());
		
	}
	
	/**
	 * Test 16: writes multiple trucks correctly
	 */
	@Test public void putManyTrucksInCSV() throws CSVFormatException, IOException {
		String fileName = "csv4.csv";
		String path = "docs/OUTPUTS/";
		parser.parse("docs/manifestParser/test4.csv");
		Manifest mani = parser.getManifest();
		
		//we parse in a manifest that we know from earlier tests has 3 trucks
		parser.write(mani,path,fileName);
		parser.parse(path+fileName);
		Manifest mani2=parser.getManifest();
		//should return 3 trucks in manifest
		assertTrue("Did not correctly add multiple trucks to manifest",mani2.size()==3);
	}
	/**
	 * Test 17: Writes item names correctly;
	 * @throws CSVFormatException 
	 * @throws IOException 
	 */
	@Test public void putItemsInCSV() throws CSVFormatException, IOException {
		String fileName = "csv5.csv";
		String path = "docs/OUTPUTS/";
		parser.parse("docs/manifestParser/test3.csv");
		Manifest mani = parser.getManifest();
		
		//we parse in a manifest that we know from earlier tests has 1 truck with 3 items
		parser.write(mani,path,fileName);
		parser.parse(path+fileName);
		Manifest mani2=parser.getManifest();
		
		ArrayList<Item> items = mani2.get(0).get().get();
		
		int counter=0;
		int counter2=0;
		
		// count the wrong results
		if (!items.get(0).getName().equals("ice cream")) {
			counter++;
		}
		if (!items.get(1).getName().equals("cheese")) {
			counter++;
		}
		if (!items.get(2).getName().equals("rice")) {
			counter++;
		}
		// count the wrong results
		if (!items.get(0).getItemQuantity().equals(101)) {
					counter2++;
		}
		if (!items.get(1).getItemQuantity().equals(76)) {
			counter2++;
		}
		if (!items.get(2).getItemQuantity().equals(90)) {
			counter2++;
		}
		
		assertTrue("You got "+counter+" names wrong and "+counter2+" quantities wrong",counter+counter2==0);
	}
	
	/**
	 * Test 18: Lots of trucks with multiple items
	 */

		@Test public void largeManifest() throws CSVFormatException, IOException {
			String fileName = "csv6.csv";
			String path = "docs/OUTPUTS/";
			parser.parse("docs/manifestParser/manifest.csv");
			Manifest mani = parser.getManifest();
			
			parser.write(mani,path,fileName);
			parser.parse(path+fileName);
			Manifest mani2=parser.getManifest();
			//this manifest is the assignment test one. contains 3 trucks
			//inside each truck is a different number of items
			//this checks that each of the 3 trucks has the correct number of items
			int counter=0;
			
			// count the wrong results
			if (!(mani.get(0).get().get().size()==3)) {
				counter++;
			}
			if (!(mani.get(1).get().get().size()==2)) {
				counter++;
			}
			if (!(mani.get(2).get().get().size()==1)) {
				counter++;
			}
			
			assertTrue ("Did not correctly write items for"+counter+" trucks",counter==0);
			
	 }
}
