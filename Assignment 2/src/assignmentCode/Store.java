package assignmentCode;

import java.util.ArrayList;
/**
 * Singleton object that interacts with all other objects
 * @author Locke
 * @version 1
 */
public class Store {


	private static double capital;
	private static String name;
	private static Stock stock= new Stock();
	private static itemParser parser = new itemParser();
	
	private static Store INSTANCE;
	// Protected constructor is sufficient to suppress unauthorized
	// calls to the constructor
	protected Store() {	
	}
	/**
	 * Returns the instance of the store
	 * @return Store Instance
	 */
	public static Store getInstance()
	{
		if( INSTANCE == null )
		{
			INSTANCE = new Store();
			INSTANCE.stock=new Stock();
			INSTANCE.parser=new itemParser();
	    }
	    return INSTANCE;
	    
	}
	/**
	 * Destroys the store instance. Will have to be reinitialised
	 */
	public static void clear() {
		INSTANCE = null;
	}
	  
	/**
	 * Sets the stores capital
	 * @param c The capital to be set
	 */
	public static void setInitialCapital(double c) {
		capital=c;
	}
	/**
	 * Returns the stores capital
	 * @return The stores capital
	 */
	public static double getCapital() {
		return capital;
	}
	/**
	 * Sets the name of the store
	 * @param n The name to be set
	 */
	public static void setName(String n) {
		name = n;
	}
	/**
	 * Returns the name of the store
	 * @return The store name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the stores stock object
	 * @return The stores stock object
	 */
	public Stock getStock() {
		return stock;
	}
/**
 * Given a filename, parse item properties
 * @param filename the location and name of the item properties csv
 * @throws CSVFormatException If there is an issue with the format of the csv
 */
	public static void initialiseItems(String filename)throws CSVFormatException {

		parser.parse(filename);

		
		ArrayList<Item> items= parser.getStock().get();
		for (Item item:items) {
			stock.add(item);
		}		
	}
	/**
	 * Process a delivery manifest
	 * @param mani The manifest to be processed
	 * @throws DeliveryException If there is an issue with accepting the delivery
	 */
	public static void deliver(Manifest mani) throws DeliveryException {

		ArrayList<Item> storeArray = stock.get();

		double runningCost = 0;

		//before we add anything, we should check to make sure that the delivery is valid
		//needs to be done beforehand
		//for each truck
		for (Integer p=0;p<mani.size();p++) {
			Integer confirmations = 0;
			ArrayList<Item> truckItems = mani.get(p).get().get();
			//for each item in the truck
			Integer temp = null;
			for (Integer t =0;t<truckItems.size();t++) {
				//for each item in the store
				for (Integer s=0;s<storeArray.size();s++) {
					if (storeArray.get(s).getName().equals(truckItems.get(t).getName())){
						confirmations++;
						if (t==0) {
							temp = storeArray.get(s).getItemTemp();
						}
						runningCost+=(storeArray.get(s).getCost() *  truckItems.get(t).getItemQuantity()); 
					}
				}
				
				
			}

			//after looping over all items
			if (confirmations<truckItems.size()) {
				throw new DeliveryException("An item in the delivery wasn't in the store!");
			}
			if (confirmations>0) {
				runningCost+=mani.get(p).getCost(temp);
			}

		}
		if (runningCost>getCapital()) {
			throw new DeliveryException("You can't afford to pay for that!");
		}
			
			
		runningCost=0;
		// for each truck
		for (Integer p=0;p<mani.size();p++) {
			ArrayList<Item> truckItems = mani.get(p).get().get();
			//for each item in the truck
			Integer temp = null;
			for (Integer t =0;t<truckItems.size();t++) {
				//for each item in the store
				for (Integer s=0;s<storeArray.size();s++) {
					if (storeArray.get(s).getName().equals(truckItems.get(t).getName())){
						if (t==0) {
							temp = storeArray.get(s).getItemTemp();
						}
						runningCost+=(storeArray.get(s).getCost() *  truckItems.get(t).getItemQuantity()); 
						Integer quantity = truckItems.get(t).getItemQuantity();
						stock.get().get(s).addQuantity(quantity);
					}
				}	
				
			}
			runningCost+=mani.get(p).getCost(temp);
						
		}
		capital-=runningCost;
		
	}
	/**
	 * Process a sales log
	 * @param saleStock A stock object of sold items to be processed
	 * @throws StockException If there is an issue with the stock
	 */
	public static void processSale(Stock saleStock)throws StockException {
		ArrayList<Item>saleItems = saleStock.get();
		ArrayList<Item>storeItems=stock.get();
		double profit=0;
		// input should be checked before it is allowed to impact the store
		for (Integer i=0;i<saleItems.size();i++) {
			for (Integer j = 0;j<storeItems.size();j++) {
				if(saleItems.get(i).getName().equals(storeItems.get(j).getName())) {				
					if(   storeItems.get(j).getItemQuantity()  -  saleItems.get(i).getItemQuantity()  <  0  ) {
						throw new StockException("Can't load sale, would reduce "+saleItems.get(i).getName()+" quantity below 0");
					}
				}
			}
		}
		
		//Add the items to the store
		//for each item
		for (Integer i=0;i<saleItems.size();i++) {
			// for each item in the store
			for (Integer j = 0;j<storeItems.size();j++) {
				// if the item names match, adjust quantity and capital accordingly
				if(saleItems.get(i).getName().equals(storeItems.get(j).getName())) {				
					profit+=saleItems.get(i).getItemQuantity()*storeItems.get(i).getSellPrice();
					storeItems.get(j).addQuantity(-1*(saleItems.get(i).getItemQuantity()));
					
				}
			}
		}
		capital+=profit;
		
	}

}
