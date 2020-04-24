package assignmentCode;

import java.util.ArrayList;
/**
 * For generating manifests from a stock object
 * @author Shahyan
 * @version 1.0
 *
 */
public class manifestGenerator {
	
	//internal manifest object. 
	private Manifest manifest;
	
	/**
	 * Takes the provided stock object and generates a manifest to deliver items below their restock point
	 * @param stock The stock to base the order manifest on
	 */
	public void generate(Stock stock) {
		//initialise important classes and variables
		manifest = new Manifest();
		
		//optimise returns an array of stock items.
		//These stock items correspond to a truck
		ArrayList<Stock> optimised=optimise(stock);
		//Only make a manifest if there is stuff to add.
		if (!optimised.isEmpty()) {
				
		//for each stock. 1 stock in optimised = 1 truck object
		//think of these stock objects as a list of stuff that must go in a particular truck
		//so stock 0 in optimised is the list of stuff that needs to get loaded into truck 0 in the manifest
			for (Integer i=0;i<optimised.size();i++) {
				ArrayList<Item> items = optimised.get(i).get();
				Truck truck;
				//decide what type the truck should be based on its contents
				//as the array is sorted by optimise, we can tell by the first object.
				//If the first object is null, then all will be null
				//if the first has a temperature, then it doesn't matter, it has to be kept cool
				if (items.get(0).getItemTemp()==null) {
					truck = new ordinaryTruck();
					}
				else {
					truck = new refrigeratedTruck();
				}
				//for every item in the optimised list, add to the current truck
				for (Integer j =0;j<items.size();j++) {
					try {
						truck.add(new Item(items.get(j).getName(),
								items.get(j).getCost(),
								items.get(j).getSellPrice(),
								items.get(j).getReorderPoint(),
								items.get(j).getReorderAmount(),
								items.get(j).getItemTemp(),
								items.get(j).getItemQuantity()						
								));
					} catch (truckException e) {
						e.printStackTrace();
					}
					
					
				}
				//Once the truck is full. Add it to the manifest.
				manifest.add(truck);
			}
		}
		else {
			manifest=new Manifest();
		}
		
		
	}
	/**
	 * Optimise takes a stock object (ie the one from the store) and turns it into a list of stock objects. 
	 * These stock objects are in an optimsied order to minimise the number of cold trucks required. 
	 * 1 stock = 1 list of items that should go on 1 truck. Checks if items are below their reorder point.
	 * @param unomptimised An unoptimised stock object
	 * @return An optimised arraylist of stock objects
	 */
	private ArrayList<Stock> optimise(Stock unomptimised){
		ArrayList<Item> initial = unomptimised.get();
		ArrayList<Item> sortedByTemp= new ArrayList<Item>();
		ArrayList<Stock> output = new ArrayList<Stock>();
		Integer targetLength;
		
		//get all the cold items sorted coldest to hottest
		for (Integer temp=-20;temp<=10;temp++) {
			for (Integer index=0;index<initial.size();index++) {
				
				//if they are the right temp AND below reorder point, add to list
				if ((initial.get(index).getItemTemp()==temp)) {
					if (initial.get(index).getItemQuantity()<initial.get(index).getReorderPoint()) {
						sortedByTemp.add(initial.get(index));
					}
				}
			}
		}//stick all the hot items on the end
		for (Integer index=0;index<initial.size();index++) {
			//if they are null temp AND below reorder point, add to list
			if ((initial.get(index).getItemTemp()==null)) {
				if (initial.get(index).getItemQuantity()<initial.get(index).getReorderPoint()) {
					sortedByTemp.add(initial.get(index));
				}
			}
		}
		//if no items get added, return an empty list
		
		if(sortedByTemp.size()==0) {
			return output;
		}
		
		
		//decide what length to target based on what truck we will need
		//if the coldest item is a dry good, choose length for ord. truck
		//else choose length for ref. truck
		if (sortedByTemp.get(0).getItemTemp()==null) {
			targetLength=1000;
			}
		else {
			targetLength=800;		
		}
		//add the first stock item to the output array
		output.add(new Stock());
		//loop over all the sorted items
		for (Integer i=0;i<sortedByTemp.size();i++) {
	
			//get the remaining length of the current stock item
			//ie calculate how much more we can fit
			Integer sum=0;		
			Stock current = output.get(output.size()-1);
			for (Integer k=0;k<current.get().size();k++) {
				sum+=output.get(output.size()-1).get().get(k).getItemQuantity();
			}	
			Integer remaining=targetLength-sum;
			
			//if we can just put all of the items in the current stock, then just do it.
			//if we can't, fill the current stock to the brim, and start a new stock object, and put the remainder in there
			//this code works on the assumption that the order amount of a single item will never be high enough to fill the current stock
			//then overfill the next stock. ie that cold items reorder amounts are <801 and dry goods are <1001.
			//Looking at the item properties document for the store, all objects have reorder points<600
			//It might get into situations where too many items are added to a stock object, which will raise
			//errors when 
			if (sortedByTemp.get(i).getReorderAmount()<=remaining) {

				//make a new identical object and put it in the current stock
				output.get(output.size()-1).add(new Item (sortedByTemp.get(i).getName(),
						sortedByTemp.get(i).getCost(),
						sortedByTemp.get(i).getSellPrice(),
						sortedByTemp.get(i).getReorderPoint(),
						sortedByTemp.get(i).getReorderAmount(),
						sortedByTemp.get(i).getItemTemp(),
						sortedByTemp.get(i).getReorderAmount()	
						));
						
			}
			else {

				//make an item of legnth remaining and put it in current stock
				output.get(output.size()-1).add(new Item (sortedByTemp.get(i).getName(),
						sortedByTemp.get(i).getCost(),
						sortedByTemp.get(i).getSellPrice(),
						sortedByTemp.get(i).getReorderPoint(),
						sortedByTemp.get(i).getReorderAmount(),
						sortedByTemp.get(i).getItemTemp(),
						remaining
						));
				//make an item of whatever is left over and put it in a new stock
				Integer leftover = sortedByTemp.get(i).getReorderAmount()-remaining;		
				output.add(new Stock());
				output.get(output.size()-1).add(new Item (sortedByTemp.get(i).getName(),
						sortedByTemp.get(i).getCost(),
						sortedByTemp.get(i).getSellPrice(),
						sortedByTemp.get(i).getReorderPoint(),
						sortedByTemp.get(i).getReorderAmount(),
						sortedByTemp.get(i).getItemTemp(),
						leftover
						));
				//check if current item has a null temp
				//This if loop makes sure the newly made stock will have correct length
				//if its been working with cold items, target will be at 800. Puts the target up to 1000
				//once it starts working with dry goods only, to not waste space
				//inside the for loop so it only happens when a new stock is made.
				if (sortedByTemp.get(i).getItemTemp()==null) {
					targetLength=1000;
				}	
			}
		}
		
		//returns optimised ArrayList of stock items.
		return output;
		
	}
	/**
	 * Returns the most recent manifest object. Empty if generate has never been called
	 * @return most recent manifest object
	 */
	
	public Manifest get() {		
		return manifest;
	}
}
