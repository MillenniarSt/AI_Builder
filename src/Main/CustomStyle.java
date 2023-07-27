package Main;

import java.util.ArrayList;

public class CustomStyle implements Loader {

	private String id;
	private ArrayList<Style> enableStyles;

	public CustomStyle(String id) {
		this.id = id.replaceAll("\\", ".");
		this.enableStyles = new ArrayList<>();
	}
	public CustomStyle(String id, ArrayList<Style> enableStyles) {
		this.id = id.replaceAll("\\", ".");
		this.enableStyles = enableStyles;
	}

	@Override
	public boolean load(String path) {
		id = path.replaceAll("\\", ".");
		return true;
	}
	
	public String toString() {
		return id;
	}
	
	protected void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public ArrayList<Style> getEnableStyles() {
		return enableStyles;
	}
	public void setEnableStyles(ArrayList<Style> enableStyles) {
		this.enableStyles = enableStyles;
	}
}
