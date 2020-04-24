package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import assignmentCode.Manifest;
import assignmentCode.ordinaryTruck;
import assignmentCode.refrigeratedTruck;

/**
 * 
 * @author Locke
 * @version 1.1
 */
public class manifestTests {
	/**
	 * Test 0: Declare a manifest object
	 */
	
	Manifest manifest;
	
	/**
	 * Test 1: Construct a manifest object
	 */
	
	@Before @Test public void makeAManifest() {
		manifest = new Manifest();
	}
	
	/**
	 * Test 2: Add trucks to manifest, ensure they are stored in a useful format
	 */
	
	@Test public void addTrucksToManifest(){
		//create some trucks
		ordinaryTruck truck = new ordinaryTruck();
		refrigeratedTruck truck2=new refrigeratedTruck();
		//add the trucks to the manifest
		manifest.add(truck);
		manifest.add(truck2);
		//assert that the manifest stored the trucks as it should and can return them
		assertEquals("Could not add trucks to manifest",
					truck2,manifest.get(1));
		
	}
	
	/**
	 * Test 3: The manifest can return how many trucks it has stored
	 */
	
	@Test public void manifestSize() {
		//create some trucks
		ordinaryTruck truck = new ordinaryTruck();
		refrigeratedTruck truck2=new refrigeratedTruck();
		refrigeratedTruck truck3=new refrigeratedTruck();
		//add the trucks to the manifest
		manifest.add(truck);
		manifest.add(truck2);
		manifest.add(truck3);
		
		//assert that the manifest knows it has 3 trucks in it.
		assertTrue("Did not correctly get count of trucks in manifest",
				manifest.size()==3);
	}
		
	
}
