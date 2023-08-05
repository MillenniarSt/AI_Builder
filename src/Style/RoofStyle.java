package Style;

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

public class RoofStyle extends CustomStyle {
	
	private Model repeat;
	private Model center;
	private Model side;
	private Model sideBase;
	private Model corner;
	private Model cornerHigh;
	
	private int base;
	private int varHigh;
	
	public RoofStyle(String id) {
		super(id);
	}

	public void setRepeat(Model repeat) {
		this.repeat = repeat;
	}
	public Model getRepeat() {
		return repeat;
	}
	public Model getCenter() {
		return center;
	}
	public void setCenter(Model center) {
		this.center = center;
	}
	public Model getSide() {
		return side;
	}
	public void setSide(Model side) {
		this.side = side;
	}
	public Model getSideBase() {
		return sideBase;
	}
	public void setSideBase(Model sideBase) {
		this.sideBase = sideBase;
	}
	public Model getCorner() {
		return corner;
	}
	public void setCorner(Model corner) {
		this.corner = corner;
	}
	public Model getCornerHigh() {
		return cornerHigh;
	}
	public void setCornerHigh(Model cornerHigh) {
		this.cornerHigh = cornerHigh;
	}
	public void setBase(int base) {
		this.base = base;
	}
	public int getBase() {
		return base;
	}
	public int getVarHigh() {
		return varHigh;
	}
	public void setVarHigh(int varHigh) {
		this.varHigh = varHigh;
	}
}
