package Model;

import Building.Building;
import Exception.AIObjectNotFoundException;
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

public class Wall {

	private WallStyle style;
	
	private Position origin;
	private Position end;
	private boolean inside;
	private Door door;
	private Window window;
	
	private int lenght;
	private int size;
	private int high;
	
	private int id;
	
	public Wall(WallStyle style, Position origin, Position end, boolean inside, int id) {
		this.style = style;
		this.origin = origin;
		this.end = end;
		this.inside = inside;
		this.door = null;
		this.window = null;
		this.id = id;
		
		refreshSize();
	}
	
	public void build(Building building) throws AIObjectNotFoundException {
		Main.printDebug("Building wall " + this);
		building.getStyle().changeIndexs();
		if(style.getModelBotton().getSizeY() + style.getModelUp().getSizeY() > high) {
			style.setModelBotton(new Model());
			style.setModelUp(new Model());
		}
		
		int variationCorner = 0;
		if(style.getCorner() != null) {
			variationCorner = style.getCorner().getSize() - style.getCorner().getRelX();
			if(variationCorner < 0)
				variationCorner = 0;
		}
		int windows = 0;
		int dis = 0;
		int disWindows = 0;
		int disWindowsAdd = 0;
		if(door != null)
			door.getOrigin().setPosY(origin.getPosY() + 1);
		else {
			this.window = style.getWindow().getRandom();
			windows = (lenght - variationCorner) / (window.getModel().getSizeX() + window.getPrefDistance());
			dis = (lenght - variationCorner) % (window.getModel().getSizeX() + window.getPrefDistance());
			disWindows = (dis / windows) + window.getPrefDistance();
			disWindowsAdd = dis % windows;
		}
		
		if(id == 0) {
			if(style.getCorner() != null) {
				Main.printDebug("Building wall's corner");
				for(int x = 0; x < style.getCorner().getSize(); x++) {
					for(int z = 0; z > style.getCorner().getSize() * -1; z--) {
						for(int y = 0; y < high; y++) {
							building.setPos(origin.getPosX() - style.getCorner().getRelX() + x, 
									origin.getPosZ() + size - 1 + style.getCorner().getRelZ() + z, 
									origin.getPosY() + y, style.getCorner().getMaterialsCollection());
						}
					}
				}
			}
			Main.printDebug("Building wall's models");
			style.getModelBotton().buildRepeatNorth(new Position(origin.getPosX() + variationCorner, origin.getPosZ(), origin.getPosY()), 
													new Position(end.getPosX(), origin.getPosZ(), origin.getPosY()), 0);
			style.getModelUp().buildRepeatNorth(new Position(origin.getPosX() + variationCorner, origin.getPosZ(), end.getPosY()), 
												new Position(end.getPosX(), origin.getPosZ(), end.getPosY()), 0);
			style.getModelRepeat().buildRepeat2North(new Position(origin.getPosX() + variationCorner, origin.getPosZ(), end.getPosY() + style.getModelBotton().getMaxY() +1), 
													new Position(end.getPosX(), origin.getPosZ(), end.getPosY() - style.getModelUp().getMinY() -1), 0);
			if(door != null) {
				Main.printDebug("Building wall's door");
				door.getModel().build(building, door.getOrigin());
			} else {
				Main.printDebug("Building wall's windows");
				int x = origin.getPosX() + variationCorner + disWindows;
				for(int i = 1; i <= windows; i++) {
					if(disWindowsAdd > 0)
						x++;
					window.getModel().build(building, x, end.getPosZ(), origin.getPosY() + window.getHigh());
					x = x + window.getModel().getSizeX() + disWindows;
					disWindowsAdd--;
				}
			}
			
		} else if(id == 1) {
			if(style.getCorner() != null) {
				Main.printDebug("Building wall's corner");
				for(int x = 0; x > style.getCorner().getSize() * -1; x--) {
					for(int z = 0; z > style.getCorner().getSize() * -1; z--) {
						for(int y = 0; y < high; y++) {
							building.setPos(origin.getPosX() + size - 1 + style.getCorner().getRelZ() + x, 
									origin.getPosZ() + style.getCorner().getRelX() + z, 
									origin.getPosY() + y, style.getCorner().getMaterialsCollection());
						}
					}
				}
			}
			Main.printDebug("Building wall's models");
			style.getModelBotton().buildRepeatEast(new Position(origin.getPosX(), origin.getPosZ() - variationCorner, origin.getPosY()), 
												new Position(origin.getPosX(), end.getPosZ(), origin.getPosY()), 0);
			style.getModelUp().buildRepeatEast(new Position(origin.getPosX(), origin.getPosZ() - variationCorner, end.getPosY()), 
												new Position(origin.getPosX(), end.getPosZ(), end.getPosY()), 0);
			style.getModelRepeat().buildRepeat2East(new Position(origin.getPosX(), origin.getPosZ() - variationCorner, origin.getPosY() + style.getModelBotton().getMaxY() +1), 
												new Position(origin.getPosX(), end.getPosZ(), origin.getPosY() - style.getModelBotton().getMinY() -1), 0);
			if(door != null) {
				Main.printDebug("Building wall's door");
				door.getModel().rotate(90);
				door.getModel().build(building, door.getOrigin());
			} else {
				Main.printDebug("Building wall's windows");
				int z = origin.getPosZ() - variationCorner - disWindows;
				for(int i = 1; i <= windows; i++) {
					if(disWindowsAdd > 0)
						z--;
					window.getModel().build(building, end.getPosX(), z, origin.getPosY() + window.getHigh());
					z = z - window.getModel().getSizeX() - disWindows;
					disWindowsAdd--;
				}
			}
			
		} else if(id == 2) {
			if(style.getCorner() != null) {
				Main.printDebug("Building wall's corner");
				for(int x = 0; x > style.getCorner().getSize() * -1; x--) {
					for(int z = 0; z < style.getCorner().getSize(); z++) {
						for(int y = 0; y < high; y++) {
							building.setPos(origin.getPosX() + style.getCorner().getRelX() + x, 
									origin.getPosZ() - size + 1 + style.getCorner().getRelZ() + z, 
									origin.getPosY() + y, style.getCorner().getMaterialsCollection());
						}
					}
				}
			}
			Main.printDebug("Building wall's models");
			style.getModelBotton().buildRepeatSouth(new Position(origin.getPosX() - variationCorner, origin.getPosZ(), origin.getPosY()), 
													new Position(end.getPosX(), origin.getPosZ(), origin.getPosY()), 0);
			style.getModelUp().buildRepeatSouth(new Position(origin.getPosX() - variationCorner, origin.getPosZ(), end.getPosY()), 
													new Position(end.getPosX(), origin.getPosZ(), end.getPosY()), 0);
			style.getModelRepeat().buildRepeat2South(new Position(origin.getPosX() - variationCorner, origin.getPosZ(), end.getPosY() + style.getModelBotton().getMaxY() +1), 
													new Position(end.getPosX(), origin.getPosZ(), end.getPosY() - style.getModelUp().getMinY() -1), 0);
			if(door != null) {
				Main.printDebug("Building wall's door");
				door.getModel().rotate(180);
				door.getModel().build(building, door.getOrigin());
			} else {
				Main.printDebug("Building wall's windows");
				int x = origin.getPosX() - variationCorner - disWindows;
				for(int i = 1; i <= windows; i++) {
					if(disWindowsAdd > 0)
						x--;
					window.getModel().build(building, x, end.getPosZ(), origin.getPosY() + window.getHigh());
					x = x - window.getModel().getSizeX() - disWindows;
					disWindowsAdd--;
				}
			}
			
		} else if(id == 3) {
			if(style.getCorner() != null) {
				for(int x = 0; x < style.getCorner().getSize(); x++) {
					for(int z = 0; z < style.getCorner().getSize(); z++) {
						for(int y = 0; y < high; y++) {
							building.setPos(origin.getPosX() - size + 1 + style.getCorner().getRelZ() + x, 
									origin.getPosZ() - style.getCorner().getRelX() + z, 
									origin.getPosY() + y, style.getCorner().getMaterialsCollection());
						}
					}
				}
			}
			Main.printDebug("Building wall's models");
			style.getModelBotton().buildRepeatWest(new Position(origin.getPosX(), origin.getPosZ() + variationCorner, origin.getPosY()), 
					new Position(origin.getPosX(), end.getPosZ(), origin.getPosY()), 0);
			style.getModelUp().buildRepeatWest(new Position(origin.getPosX(), origin.getPosZ() + variationCorner, end.getPosY()), 
					new Position(origin.getPosX(), end.getPosZ(), end.getPosY()), 0);
			style.getModelRepeat().buildRepeat2West(new Position(origin.getPosX(), origin.getPosZ() + variationCorner, origin.getPosY() + style.getModelBotton().getMaxY() +1), 
					new Position(origin.getPosX(), end.getPosZ(), origin.getPosY() - style.getModelBotton().getMinY() -1), 0);
			if(door != null) {
				Main.printDebug("Building wall's door");
				door.getModel().rotate(270);
				door.getModel().build(building, door.getOrigin());
			} else {
				Main.printDebug("Building wall's windows");
				int z = origin.getPosZ() + variationCorner + disWindows;
				for(int i = 1; i <= windows; i++) {
					if(disWindowsAdd > 0)
						z++;
					window.getModel().build(building, end.getPosX(), z, origin.getPosY() + window.getHigh());
					z = z + window.getModel().getSizeX() + disWindows;
					disWindowsAdd--;
				}
			}
		}
	}
	
	public void refreshSize() {
		if(id == 1 || id == 3) {
			this.lenght = Math.abs(this.origin.getPosZ() - this.end.getPosZ()) + 1;
			this.size = Math.abs(this.origin.getPosX() - this.end.getPosX()) + 1;
		} else if(id == 0 || id == 2) {
			this.lenght = Math.abs(this.origin.getPosX() - this.end.getPosX()) + 1;
			this.size = Math.abs(this.origin.getPosZ() - this.end.getPosZ()) + 1;
		}
		this.high = Math.abs(this.origin.getPosY() - this.end.getPosY()) + 1;
	}
	
	public String toString() {
		return "Wall from " + origin + " to " + end + " with lenght " + lenght + " size " + size + " high " + high + " and data = [style=[" + 
				style + "],inside=" + inside + ",door=" + door + ",window=" + window + "]";
	}
	
	public WallStyle getStyle() {
		return style;
	}
	public void setStyle(WallStyle style) {
		this.style = style;
	}
	public Position getOrigin() {
		return origin;
	}
	public void setOrigin(Position origin) {
		this.origin = origin;
		refreshSize();
	}
	public Position getEnd() {
		return end;
	}
	public void setEnd(Position end) {
		this.end = end;
		refreshSize();
	}
	public int getLenght() {
		return lenght;
	}
	public int getHigh() {
		return high;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isInside() {
		return inside;
	}
	public void setInside(boolean inside) {
		this.inside = inside;
	}
	public int getSize() {
		return size;
	}
	public Door isDoor() {
		return door;
	}
	public void setDoor(Door door) {
		this.door = door;
	}
	public Window getWindow() {
		return window;
	}
	public void setWindow(Window window) {
		this.window = window;
	}
}
