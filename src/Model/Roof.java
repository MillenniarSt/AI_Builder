package Model;

/*
*           |\       /|                          __                 __    ___  __
*           | \     / |   ______    /\    |     |  \  |   | | |    |  \  |    |  \
*           |  \   /  |  /         /  \   |     |__/  |   | | |    |   | |___ |__/
*           |   \_/   | |         /----\  |     |   \ |   | | |    |   | |    |  \
*           |         |  \____   /      \ |     |___/  \_/  | |___ |__/  |___ |   \
*           |         |       \
*           |         |        |      AI Builder  ---   By Millenniar Studios
*           |         | ______/
*/

public class Roof {

	private final String type;
	private RoofStyle style;
	
	private Position origin;
	private Position end;
	
	public Roof(String type, RoofStyle style) {
		this.type = type;
		this.style = style;
	}

	public void build() {
		
	}
	
	public String getType() {
		return type;
	}
	public void setStyle(RoofStyle style) {
		this.style = style;
	}
	public RoofStyle getStyle() {
		return style;
	}
	public void setOrigin(Position origin) {
		this.origin = origin;
	}
	public Position getOrigin() {
		return origin;
	}
	public void setEnd(Position end) {
		this.end = end;
	}
	public Position getEnd() {
		return end;
	}
}
