package Exceptions;


public class AlreadyExistException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7199678130285702283L;

	public AlreadyExistException(String message) {
        super(message);
    }
}