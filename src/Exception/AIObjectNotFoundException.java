package Exception;

public class AIObjectNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	private String type;
	
	public AIObjectNotFoundException(String message) {
		super("Object not found: " + message);
	}
	public AIObjectNotFoundException(String type, String message) {
		super(type + " not found: " + message);
		this.type = type;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
