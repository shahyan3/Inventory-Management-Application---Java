package assignmentCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * For parsing item properties CSV
 * @author Shahyan
 * @version 2.0
 */


public class itemParser {

	Stock stock = new Stock();
	ArrayList<String[]>  allRows = new ArrayList<>();

	/**
	 *Parses the csv at the file location
	 * @param fileLocation The location of the file to be parsed
	 * @throws CSVFormatException if there is an error with the csv
	 */
	
	public void parse(String fileLocation) throws CSVFormatException {
		
		// 1. Create a new file of above with contents from .csv
		File file = new File(fileLocation); // TODO: read about File class
		
		// 2. Convert the contents of file into string using Scanner inputStream obj.
		try {
			Scanner inputStream = new Scanner(file);	
						
			// 3. Read the data
			while(inputStream.hasNext()) {
				String data = inputStream.nextLine(); // gets a while line .next gets next word i.e. ice. .nextLine get word with spaces i.e. ice cream
				// Get all the columns from the cell including last empty cell (if temp empty)
				String[] row = data.split(",", -1); // regex - split line
				allRows.add(row);
									
				// get total number of fields in a row
				int numberOfValues = 0;
				Integer itemTemp = null;
				for(String element : row) {
					numberOfValues++;
				}
				if(numberOfValues-1 != 5) {
					itemTemp = null;
				} else {
					itemTemp = Integer.parseInt(row[5]);
				}
				 
				this.stock.add(new Item(row[0], Integer.parseInt(row[1]),Integer.parseInt(row[2]),
						Integer.parseInt(row[3]),Integer.parseInt(row[4]), itemTemp, 0));
				
 			}
			inputStream.close();
			if(allRows.size() > 1) {
 				for(int i = 0; i < allRows.size()-1; i++) {
 					String rowX[] = allRows.get(i);
					String rowY[] = allRows.get(i+1);
						
					if(rowX[0].equals(rowY[0])) {
						throw new CSVFormatException("CSV Format Error: Duplicate Items in File");
					}

 				}
 			}
			
			// 4. close input stream

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
				
	}

}
/**
 * The stock object for the most recently parsed CSV. Empty if no CSV parsed yet
 * @return Stock object
 */
	public Stock getStock() {
		return this.stock;
	}


}
