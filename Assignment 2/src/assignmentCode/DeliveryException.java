package assignmentCode;
/**
 * Delivery Excpetion is called when problems arise with the store processing a manifest.
 * @author Locke
 * @version 1
 */
public class DeliveryException extends Exception {
	public DeliveryException() {
		super();
	}

	public DeliveryException(String message) {
		super(message);
	}
	
}
