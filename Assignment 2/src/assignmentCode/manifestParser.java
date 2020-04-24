package assignmentCode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * For parsing manifest CSVs
 * @author Shahyan
 * @version 1.0
 *
 */
public class manifestParser {

	Manifest manifest;
	public manifestParser() {
		manifest = new Manifest();
	}
	/**
	 * Returns the class manifest object, the most recent on to be parsed. will be empty if there have been no parses
	 * @return Manifest object
	 */
	public Manifest getManifest() {
		return manifest;
	}
	
	/**
	 * Parses the file at the location and puts it into the class manifest object
	 * @param fileName the path of the file to be parsed
	 * @throws FileNotFoundException if the file doesn't exist
	 * @throws CSVFormatException if the csv is not a correct manifest csv
	 */
	public void parse(String fileName) throws FileNotFoundException, CSVFormatException {
		manifest = new Manifest();
		File file =new File(fileName);
		Scanner input = new Scanner(file);
		ArrayList<String> lines = new ArrayList<String>();
		
		while(input.hasNextLine()) {
			String data=input.nextLine();
			lines.add(data);
		}
		input.close();
		
		
		//get the splits and validate
		String[][] splits = new String[lines.size()][];
		for (int i=0;i<lines.size();i++) {
			splits[i] = lines.get(i).split(",");
			if (splits[i].length==1) {
				if (!(  (splits[i][0].equals(">Refrigerated"))  ||  (splits[i][0].equals(">Ordinary"))  ) ){
					throw new CSVFormatException("Wrong truck names detected in manifest");
				}
			}
			else if (splits[i].length==2) {
				
			}
			else {
				throw new CSVFormatException("Too many columns detected");
			}
		
		}
		
		Truck truck = new refrigeratedTruck();
		for (int i=0;i<splits.length;i++) {
			String[] split = splits[i];
			if (split.length==1) {
				if (split[0].equals(">Ordinary")) {
					truck = new ordinaryTruck();
					manifest.add(truck);
				}
				else if (split[0].equals(">Refrigerated")) {
					truck = new refrigeratedTruck();
					manifest.add(truck);
				}
			}
			if (split.length==2) {
				try {
					truck.add(new Item(split[0],
							0,
							0,
							0,
							0,
							null,
							Integer.parseInt(split[1])
							)

							);
				} catch (NumberFormatException e) {
					manifest= new Manifest();
					throw new CSVFormatException("Couldn't get a quantity number from an item in the csv");
				} catch (truckException e) {
					manifest= new Manifest();
					throw new CSVFormatException("Adding too much to a truck, manifest incorrect");
					
				}
			}
		
		}

		
	}
	/**
	 * Writes the supplied manifest to a csv at the the supplied location with the supplied name
	 * 
	 * @param mani The manifest to print
	 * @param path The filepath to put the csv at
	 * @param fileName The name of the csv
	 * @throws IOException If there is an error with writing.
	 */
	public void write(Manifest mani, String path, String fileName) throws IOException {
		
		FileWriter fwriter = new FileWriter(path+fileName,false);
		BufferedWriter bwriter = new BufferedWriter(fwriter);
		PrintWriter writer = new PrintWriter(bwriter);
		
		for (int i=0;i<mani.size();i++) {
			if (mani.get(i).getClass()==ordinaryTruck.class) {
				writer.println(">Ordinary");
			}
			else if(mani.get(i).getClass()==refrigeratedTruck.class) {
				writer.println(">Refrigerated");
			}
			Stock stock = mani.get(i).get();
			ArrayList<Item> items = stock.get();
			for(int j=0;j<items.size();j++) {
				writer.println(items.get(j).getName()+","+items.get(j).getItemQuantity());
			}
		}
		writer.flush();
		writer.close();
	}
	

}
