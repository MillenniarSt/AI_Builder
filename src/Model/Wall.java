package Model;

import Building.Building;
import Exception.AIObjectNotFoundException;
import Main.Main;

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
		this.style = style.clone();
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
		if(style.getModelBotton().getSizeY() + style.getModelUp().getSizeY() > high) {
			style.setModelBotton(null);
			style.setModelUp(null);
		}
		
		int variationCorner = 0;
		if(style.getCorner() != null) {
			variationCorner = style.getCorner().getSize() - style.getCorner().getRelX();
			if(variationCorner < 0)
				variationCorner = 0;
		}
		door.getOrigin().setPosY(origin.getPosY() + 1);
		this.window = style.getWindow().getRandom();
		int windows = (lenght - variationCorner) / (window.getModel().getSizeX() + window.getPrefDistance());
		int dis = (lenght - variationCorner) % (window.getModel().getSizeX() + window.getPrefDistance());
		int disWindows = (dis / windows) + window.getPrefDistance();
		int disWindowsAdd = dis % windows;
		
		int ix = 0;
		int iz = 0;
		int iy = 0;
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
			for(int x = origin.getPosX() + variationCorner; x <= end.getPosX(); x++) {
				iz = 0;
				for(int z = origin.getPosZ(); z <= origin.getPosZ() + style.getModelBotton().getSizeZ() -1; z++) {
					iy = 0;
					for(int y = origin.getPosY(); y <= origin.getPosY() + style.getModelBotton().getSizeY() -1; y++) {
						building.setPos(x, z, y, style.getModelBotton().getDataPos(ix, iz, iy));
						iy++;
					}
					iz++;
				}
				ix++;
				if(ix >= style.getModelBotton().getSizeX())
					ix = 0;
			}
			ix = 0;
			for(int x = origin.getPosX() + variationCorner; x <= end.getPosX(); x++) {
				iz = 0;
				for(int z = origin.getPosZ(); z <= origin.getPosZ() + style.getModelUp().getSizeZ() -1; z++) {
					iy = 0;
					for(int y = end.getPosY(); y >= end.getPosY() - style.getModelUp().getSizeY() +1; y--) {
						building.setPos(x, z, y, style.getModelUp().getDataPos(ix, iz, iy));
						iy++;
					}
					iz++;
				}
				ix++;
				if(ix >= style.getModelUp().getSizeX())
					ix = 0;
			}
			ix = 0;
			for(int x = origin.getPosX() + variationCorner; x <= end.getPosX(); x++) {
				iz = 0;
				for(int z = origin.getPosZ(); z <= origin.getPosZ() + style.getModelRepeat().getSizeZ() -1; z++) {
					iy = 0;
					for(int y = origin.getPosY() + style.getModelBotton().getSizeY() -1; y <= end.getPosY() - style.getModelUp().getSizeY() +1; y++) {
						building.setPos(x, z, y, style.getModelRepeat().getDataPos(ix, iz, iy));
						iy++;
						if(iy >= style.getModelRepeat().getSizeY())
							iy = 0;
					}
					iz++;
				}
				ix++;
				if(ix >= style.getModelRepeat().getSizeX())
					ix = 0;
			}
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
			style.getModelBotton().rotate(90);
			style.getModelRepeat().rotate(90);
			style.getModelUp().rotate(90);
			for(int z = origin.getPosZ() - variationCorner; z >= end.getPosZ(); z--) {
				iz = 0;
				for(int x = origin.getPosX(); x <= origin.getPosX() + style.getModelBotton().getSizeX() -1; x++) {
					iy = 0;
					for(int y = origin.getPosY(); y <= origin.getPosY() + style.getModelBotton().getSizeY() -1; y++) {
						building.setPos(x, z, y, style.getModelBotton().getDataPos(ix, iz, iy));
						iy++;
					}
					ix++;
				}
				iz--;
				if(iz <= style.getModelBotton().getSizeZ() * -1)
					iz = 0;
			}
			iz = 0;
			for(int z = origin.getPosZ() - variationCorner; z >= end.getPosZ(); z--) {
				ix = 0;
				for(int x = origin.getPosX(); x <= origin.getPosX() + style.getModelUp().getSizeX() -1; x++) {
					iy = 0;
					for(int y = end.getPosY(); y >= end.getPosY() - style.getModelUp().getSizeY() +1; y--) {
						building.setPos(x, z, y, style.getModelUp().getDataPos(ix, iz, iy));
						iy++;
					}
					ix++;
				}
				iz--;
				if(iz <= style.getModelUp().getSizeZ() * -1)
					iz = 0;
			}
			iz = 0;
			for(int z = origin.getPosZ() - variationCorner; z >= end.getPosZ(); z--) {
				ix = 0;
				for(int x = origin.getPosX(); x <= origin.getPosX() + style.getModelRepeat().getSizeX() -1; x++) {
					iy = 0;
					for(int y = origin.getPosY() + style.getModelBotton().getSizeY() -1; y <= end.getPosY() - style.getModelUp().getSizeY() +1; y++) {
						building.setPos(x, z, y, style.getModelRepeat().getDataPos(ix, iz, iy));
						iy++;
						if(iy >= style.getModelRepeat().getSizeY())
							iy = 0;
					}
					ix++;
				}
				iz--;
				if(iz <= style.getModelRepeat().getSizeZ() * -1)
					iz = 0;
			}
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
			style.getModelBotton().rotate(180);
			style.getModelRepeat().rotate(180);
			style.getModelUp().rotate(180);
			for(int x = origin.getPosX() - variationCorner; x >= end.getPosX(); x--) {
				iz = 0;
				for(int z = origin.getPosZ(); z >= origin.getPosZ() - style.getModelBotton().getSizeZ() +1; z--) {
					iy = 0;
					for(int y = origin.getPosY(); y <= origin.getPosY() + style.getModelBotton().getSizeY() -1; y++) {
						building.setPos(x, z, y, style.getModelBotton().getDataPos(ix, iz, iy));
						iy++;
					}
					iz++;
				}
				ix--;
				if(ix <= style.getModelBotton().getSizeX() * -1)
					ix = 0;
			}
			ix = 0;
			for(int x = origin.getPosX() - variationCorner; x >= end.getPosX(); x--) {
				iz = 0;
				for(int z = origin.getPosZ(); z >= origin.getPosZ() - style.getModelUp().getSizeZ() +1; z--) {
					iy = 0;
					for(int y = end.getPosY(); y >= end.getPosY() - style.getModelUp().getSizeY() +1; y--) {
						building.setPos(x, z, y, style.getModelUp().getDataPos(ix, iz, iy));
						iy++;
					}
					iz++;
				}
				ix--;
				if(ix <= style.getModelUp().getSizeX() * -1)
					ix = 0;
			}
			ix = 0;
			for(int x = origin.getPosX() - variationCorner; x >= end.getPosX(); x--) {
				iz = 0;
				for(int z = origin.getPosZ(); z >= origin.getPosZ() - style.getModelRepeat().getSizeZ() +1; z--) {
					iy = 0;
					for(int y = origin.getPosY() + style.getModelBotton().getSizeY() -1; y <= end.getPosY() - style.getModelUp().getSizeY() +1; y++) {
						building.setPos(x, z, y, style.getModelRepeat().getDataPos(ix, iz, iy));
						iy++;
						if(iy >= style.getModelRepeat().getSizeY())
							iy = 0;
					}
					iz++;
				}
				ix--;
				if(ix <= style.getModelRepeat().getSizeX() * -1)
					ix = 0;
			}
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
			style.getModelBotton().rotate(270);
			style.getModelRepeat().rotate(270);
			style.getModelUp().rotate(270);
			for(int z = origin.getPosZ() + variationCorner; z <= end.getPosZ(); z++) {
				iz = 0;
				for(int x = origin.getPosX(); x >= origin.getPosX() - style.getModelBotton().getSizeX() +1; x--) {
					iy = 0;
					for(int y = origin.getPosY(); y <= origin.getPosY() + style.getModelBotton().getSizeY() -1; y++) {
						building.setPos(x, z, y, style.getModelBotton().getDataPos(ix, iz, iy));
						iy++;
					}
					ix++;
				}
				iz++;
				if(iz >= style.getModelBotton().getSizeZ())
					iz = 0;
			}
			iz = 0;
			for(int z = origin.getPosZ() + variationCorner; z <= end.getPosZ(); z++) {
				ix = 0;
				for(int x = origin.getPosX(); x >= origin.getPosX() - style.getModelUp().getSizeX() +1; x--) {
					iy = 0;
					for(int y = end.getPosY(); y >= end.getPosY() - style.getModelUp().getSizeY() +1; y--) {
						building.setPos(x, z, y, style.getModelUp().getDataPos(ix, iz, iy));
						iy++;
					}
					ix++;
				}
				iz++;
				if(iz >= style.getModelUp().getSizeZ())
					iz = 0;
			}
			iz = 0;
			for(int z = origin.getPosZ() + variationCorner; z <= end.getPosZ(); z++) {
				ix = 0;
				for(int x = origin.getPosX(); x >= origin.getPosX() - style.getModelRepeat().getSizeX() +1; x--) {
					iy = 0;
					for(int y = origin.getPosY() + style.getModelBotton().getSizeY() -1; y <= end.getPosY() - style.getModelUp().getSizeY() +1; y++) {
						building.setPos(x, z, y, style.getModelRepeat().getDataPos(ix, iz, iy));
						iy++;
						if(iy >= style.getModelRepeat().getSizeY())
							iy = 0;
					}
					ix++;
				}
				iz++;
				if(iz >= style.getModelRepeat().getSizeZ())
					iz = 0;
			}
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
