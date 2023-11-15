package io.github.millenniarst.ai_builder.config.component;

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

import io.github.millenniarst.ai_builder.config.model.Schematic;

public class RoofStyle extends Component {
	
	private Schematic repeat;
	private Schematic center;
	private Schematic side;
	private Schematic sideBase;
	private Schematic corner;
	private Schematic cornerHigh;
	
	private int base;
	private int varHigh;
	
	public RoofStyle(String id) {
		super(id);
	}

	public void setRepeat(Schematic repeat) {
		this.repeat = repeat;
	}
	public Schematic getRepeat() {
		return repeat;
	}
	public Schematic getCenter() {
		return center;
	}
	public void setCenter(Schematic center) {
		this.center = center;
	}
	public Schematic getSide() {
		return side;
	}
	public void setSide(Schematic side) {
		this.side = side;
	}
	public Schematic getSideBase() {
		return sideBase;
	}
	public void setSideBase(Schematic sideBase) {
		this.sideBase = sideBase;
	}
	public Schematic getCorner() {
		return corner;
	}
	public void setCorner(Schematic corner) {
		this.corner = corner;
	}
	public Schematic getCornerHigh() {
		return cornerHigh;
	}
	public void setCornerHigh(Schematic cornerHigh) {
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
