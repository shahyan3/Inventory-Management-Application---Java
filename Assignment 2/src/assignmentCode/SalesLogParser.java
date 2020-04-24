package assignmentCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * For parsing sales logs into usable objects
 * @author Locke
 * @version 1
 */
public class SalesLogParser {
	private Stock stock;
	
	
	public SalesLogParser() {
		stock = new Stock();
	}
	/**
	 * Parses the CSV at the path given
	 * @param fileName The path and name of the file
	 * @throws FileNotFoundException If the file doesn't exist
	 * @throws CSVFormatException If the CSV is the wrong format
	 */
	public void parse(String fileName) throws FileNotFoundException, CSVFormatException {
		stock=new Stock();
		File file =new File(fileName);
		Scanner input = new Scanner(file);
		ArrayList<String> lines = new ArrayList<String>();
		
		while(input.hasNextLine()) {
			String data=input.nextLine();
			lines.add(data);
		
		}
		input.close();
		
		String[][] splits=new String[lines.size()][];
		//validate input
		for (int i=0;i<lines.size();i++) {
			String []split = lines.get(i).split(",");
			if(split.length!=2) {
				throw new CSVFormatException("Wrong number of columns in CSV");
			}
			try{
				Integer test = Integer.parseInt(split[1]);
			}
			catch (NumberFormatException e) {
				throw new CSVFormatException("Could not get price from second column");
			}
			splits[i]=split;
 	
		}
		
		//check for dupes
		for (int i=0;i<splits.length;i++) {
			int counter = 0;
			for (int j=0;j<splits.length;j++) {
				if (splits[i][0].equals(splits[j][0])){
					counter++;
				}
			}
			//because it will match with itself, will need to be higher than 1 to be an issue
			if (counter>1) {
				throw new CSVFormatException("Duplicate Items in CSV");
			}
		}
		
		for (int i=0;i<lines.size();i++) {
			String []split = splits[i];
			stock.add(new Item(split[0],
								0,
								0,
								0,
								0,
								0,
								Integer.parseInt(split[1])
								)

					);
		}

	
	}
	/**
	 * Gets the stock object of the most recently parsed CSV
	 * @return The most recent stock object
	 */
	public Stock getStock() {
		return stock;
	}
	
	
}
