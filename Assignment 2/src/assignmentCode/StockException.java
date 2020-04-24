package assignmentCode;
/**
 * Thrown when there is an issue with a stock object
 * @author Locke
 * @version 1
 */
public class StockException extends Exception {
	public StockException() {
		super();
	}

	public StockException(String message) {
		super(message);
	}
}
