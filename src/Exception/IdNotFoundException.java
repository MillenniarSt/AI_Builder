package Exception;

import Main.Main;

public class IdNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public IdNotFoundException(int id, String message) {
		super(id + " not found, " + message);
		Main.printDebug("IdNotFoundException: " + id + " not found, " + message);
	}
}
