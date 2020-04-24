package assignmentCode;
/**
 * CSVFormatError. Called when there is an error with CSV formats
 * @author Locke
 * @version 1
 * 
 * 
 */
public class CSVFormatException extends Exception {
	public CSVFormatException() {
		super();
	}

	public CSVFormatException(String message) {
		super(message);
	}
}
