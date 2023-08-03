package Model;

import Building.Building;
import Main.Main;

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
		int ix;
		int iz;
		int iy;
		int varCeilingOri;
		int varCeilingEnd;
		if(ceiling) {
			varCeilingOri = 1;
			varCeilingEnd = 0;
		} else {
			varCeilingOri = 0;
			varCeilingEnd = 1;
		}
		
		if(style.getSide() != null) {
			Main.printDebug("Building floor side model");
			int variationCornerX = style.getCorner().getSizeX();
			int variationCornerZ = style.getCorner().getSizeZ();
			Model sideNorth = style.getSide().clone();
			Model sideEast = style.getSide().clone();
			sideEast.rotate(90);
			Model sideSouth = style.getSide().clone();
			sideSouth.rotate(180);
			Model sideWest = style.getSide().clone();
			sideWest.rotate(270);
			ix = 0;
			for(int x = origin.getPosX() + variationCornerX; x <= end.getPosX() - variationCornerZ; x++) {
				iz = 0;
				for(int z = origin.getPosZ(); z >= origin.getPosZ() - sideNorth.getSizeZ() +1; z--) {
					iy = 0;
					for(int y = origin.getPosY() - ((sideNorth.getSizeY() -1) * varCeilingOri); y <= origin.getPosY() + ((sideNorth.getSizeY() -1) * varCeilingEnd); y++) {
						building.setPos(x, z, y, sideNorth.getDataPos(ix, iz, iy));
						iy++;
					}
					iz--;
				}
				ix++;
				if(ix >= sideNorth.getSizeX())
					ix = 0;
			}
			iz = 0;
			for(int z = origin.getPosZ() - variationCornerX; z >= end.getPosZ() + variationCornerZ; z--) {
				ix = 0;
				for(int x = end.getPosX(); x >= end.getPosX() - sideEast.getSizeX() +1; x--) {
					iy = 0;
					for(int y = origin.getPosY() - ((sideEast.getSizeY() -1) * varCeilingOri); y <= origin.getPosY() + ((sideEast.getSizeY() -1) * varCeilingEnd); y++) {
						building.setPos(x, z, y, sideEast.getDataPos(ix, iz, iy));
						iy++;
					}
					ix--;
				}
				iz--;
				if(iz <= sideEast.getSizeZ() * -1)
					iz = 0;
			}
			ix = 0;
			for(int x = end.getPosX() - variationCornerX; x >= origin.getPosX() + variationCornerZ; x--) {
				iz = 0;
				for(int z = end.getPosZ(); z <= end.getPosZ() + sideSouth.getSizeZ() -1; z++) {
					iy = 0;
					for(int y = origin.getPosY() - ((sideSouth.getSizeY() -1) * varCeilingOri); y <= origin.getPosY() + ((sideSouth.getSizeY() -1) * varCeilingEnd); y++) {
						building.setPos(x, z, y, sideSouth.getDataPos(ix, iz, iy));
						iy++;
					}
					iz++;
				}
				ix--;
				if(ix <= sideSouth.getSizeX() * -1)
					ix = 0;
			}
			iz = 0;
			for(int z = end.getPosZ() + variationCornerX; z <= origin.getPosZ() - variationCornerZ; z++) {
				ix = 0;
				for(int x = origin.getPosX(); x <= origin.getPosX() + sideWest.getSizeX() -1; x++) {
					iy = 0;
					for(int y = origin.getPosY() - ((sideWest.getSizeY() -1) * varCeilingOri); y <= origin.getPosY() + ((sideWest.getSizeY() -1) * varCeilingEnd); y++) {
						building.setPos(x, z, y, sideWest.getDataPos(ix, iz, iy));
						iy++;
					}
					ix++;
				}
				iz++;
				if(iz >= sideWest.getSizeZ())
					iz = 0;
			}
		}
		Main.printDebug("Building floor model");
		int variationSide = 0;
		if(style.getSide() != null)
			variationSide = style.getSide().getSizeZ();
		ix = 0;
		for(int x = origin.getPosX() + variationSide; x <= end.getPosX() - variationSide; x++) {
			iz = 0;
			for(int z = origin.getPosZ() - variationSide; z >= end.getPosZ() + variationSide; z--) {
				iy = 0;
				for(int y = origin.getPosY() - ((style.getRepeat().getSizeY() -1) * varCeilingOri); y <= origin.getPosY() + ((style.getRepeat().getSizeY() -1) * varCeilingEnd); y++) {
					building.setPos(x, z, y, style.getRepeat().getDataPos(ix, iz, iy));
					iy++;
				}
				iz--;
				if(iz <= style.getRepeat().getSizeZ() * -1)
					iz = 0;
			}
			ix++;
			if(ix >= style.getRepeat().getSizeX())
				ix = 0;
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
