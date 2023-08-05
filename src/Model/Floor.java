package Model;

import Building.Building;
import Main.Main;
import Style.FloorStyle;
import Style.Model;

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

public class Floor {

	private FloorStyle style;
	
	private Position origin;
	private Position end;
	private boolean inside;
	private boolean ceiling;
	
	private int sizeX;
	private int sizeZ;
	private int high;
	
	public Floor(FloorStyle style, Position origin, Position end, boolean inside, boolean ceiling) {
		this.style = style;
		this.origin = origin;
		this.end = end;
		this.inside = inside;
		this.ceiling = ceiling;
		refreshSize();
	}
	
	public void build(Building building) {
		Main.printDebug("Building floor " + this);
		building.getStyle().changeIndexs();
		
		if(style.getSide() != null) {
			Main.printDebug("Building floor side model");
			int variationCornerX = 0;
			int variationCornerZ = 0;
			if(style.getCorner() != null) {
				variationCornerX = style.getCorner().getMaxX() +1;
				variationCornerZ = style.getCorner().getMaxZ() +1;
			}
			
			style.getSide().buildRepeatNorth(new Position(origin.getPosX() + variationCornerX, origin.getPosZ(), origin.getPosY()), 
					new Position(end.getPosX() - variationCornerZ, origin.getPosZ(), origin.getPosY()), 0);
			style.getSide().buildRepeatEast(new Position(end.getPosX(), origin.getPosZ() - variationCornerX, origin.getPosY()), 
					new Position(end.getPosX(), end.getPosZ() + variationCornerZ, origin.getPosY()), 0);
			style.getSide().buildRepeatSouth(new Position(end.getPosX() - variationCornerX, end.getPosZ(), origin.getPosY()), 
					new Position(origin.getPosX() + variationCornerZ, end.getPosZ(), origin.getPosY()), 0);
			style.getSide().buildRepeatWest(new Position(origin.getPosX(), end.getPosZ() + variationCornerX, origin.getPosY()), 
					new Position(origin.getPosX(), origin.getPosZ() - variationCornerZ, origin.getPosY()), 0);
		}
		if(style.getRepeat() != null) {
			Main.printDebug("Building floor repeat model");
			int variationSide = 0;
			if(style.getSide() != null)
				variationSide = style.getSide().getSizeZ();
			
			style.getRepeat().buildRepeat2Down(new Position(origin.getPosX() + variationSide, origin.getPosZ() - variationSide, origin.getPosY()), 
					new Position(end.getPosX() - variationSide, origin.getPosZ() + variationSide, origin.getPosY()));
		}
		
		if(style.getCorner() != null) {
			Main.printDebug("Building floor corner model");
			Model cornerNorth = style.getCorner().clone();
			Model cornerEast = style.getCorner().clone();
			cornerEast.rotate(90);
			Model cornerSouth = style.getCorner().clone();
			cornerSouth.rotate(180);
			Model cornerWest = style.getCorner().clone();
			cornerWest.rotate(270);
			cornerNorth.build(building, origin);
			cornerEast.build(building, end.getPosX(), origin.getPosZ(), origin.getPosY());
			cornerSouth.build(building, origin.getPosX(), end.getPosZ(), origin.getPosY());
			cornerWest.build(building, end);
		}
		
		if(style.getCenter() != null) {
			Main.printDebug("Building floor center model");
			int cx = sizeX % 2;
			int cz = sizeZ % 2;
			Model center = style.getCenter()[cx + (cz * 2)];
			center.build(building, origin.getPosX() + sizeX / 2, origin.getPosZ() - sizeZ / 2, origin.getPosY());
		}
		Main.printDebug("Finish to build floor");
	}
	
	public void refreshSize() {
		this.sizeX = Math.abs(origin.getPosX() - end.getPosX()) + 1;
		this.sizeZ = Math.abs(origin.getPosZ() - end.getPosZ()) + 1;
		this.high = Math.abs(origin.getPosY() - end.getPosY()) + 1;
	}
	
	public String toString() {
		return "Floor from " + origin + " to " + end + " with size " + sizeX  + " - " + sizeZ + " x " + high + ", data = [style=[" + 
				style + "],inside=" + inside + ",ceiling=" + ceiling;
	}
	
	public FloorStyle getStyle() {
		return style;
	}
	public void setStyle(FloorStyle style) {
		this.style = style;
	}
	public Position getOrigin() {
		return origin;
	}
	public void setOrigin(Position origin) {
		this.origin = origin;
	}
	public Position getEnd() {
		return end;
	}
	public void setEnd(Position end) {
		this.end = end;
	}
	public boolean isInside() {
		return inside;
	}
	public void setInside(boolean inside) {
		this.inside = inside;
	}
	public int getSizeZ() {
		return sizeZ;
	}
	public int getSizeX() {
		return sizeX;
	}
	public int getHigh() {
		return high;
	}
	public boolean isCeiling() {
		return ceiling;
	}
	public void setCeiling(boolean ceiling) {
		this.ceiling = ceiling;
	}
}
